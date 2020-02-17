package ee.rabi.ali.api.account.app.transfer.controller;

import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferRequest;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferResponse;
import ee.rabi.ali.api.account.app.transfer.controller.model.TransferResponse;
import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import ee.rabi.ali.api.account.constant.Headers;
import ee.rabi.ali.api.account.orm.IdGenerator;
import ee.rabi.ali.api.account.test.IntegrationTest;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ee.rabi.ali.api.account.test.util.Assertions.assertUuid;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class TransferControllerTest extends IntegrationTest {

    @Test
    public void list_shouldReturnAllTransfers_givenData() {
        dataUtil.accounts.insertEurAccountWithInitialBalance("1", BigDecimal.ONE);
        dataUtil.accounts.insertEurAccountWithInitialBalance("2", BigDecimal.ONE);
        dataUtil.transfers.transfer1EurFromAccount1ToAccount2();
        final List<TransferResponse> response = client
                .toBlocking()
                .retrieve(HttpRequest.GET("/transfer"), Argument.listOf(TransferResponse.class));
        assertEquals(1, response.size());
        final TransferResponse transferResponse = response.get(0);
        assertUuid(transferResponse.getId());
        assertEquals("1", transferResponse.getFromAccountId());
        assertEquals("2", transferResponse.getToAccountId());
        assertEquals(BigDecimal.ONE, transferResponse.getAmount());
        assertNotNull(transferResponse.getCreatedAt());
    }

    @Test
    public void create_shouldReturn404_givenToAccountDoesNotExist() {
        dataUtil.accounts.insertAccount1WithEurCurrency();
        final CreateTransferRequest request = buildCreateTransferRequest("1", "2");
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest
                        .PUT("/transfer", request)
                        .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate())));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Could not find account 2", exception.getMessage());
    }

    @Test
    public void create_shouldReturn404_givenFromAccountDoesNotExist() {
        dataUtil.accounts.insertAccount2WithEurCurrency();
        final CreateTransferRequest request = buildCreateTransferRequest("1", "2");
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest
                        .PUT("/transfer", request)
                        .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate())));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Could not find account 1", exception.getMessage());
    }

    @Test
    public void create_shouldReturn400_givenFromAccountDoesNotHaveEnoughBalance() {
        dataUtil.accounts.insertAccount1WithEurCurrency();
        dataUtil.accounts.insertEurAccountWithInitialBalance("2", BigDecimal.ONE);
        final CreateTransferRequest request = buildCreateTransferRequest("1", "2");
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest
                        .PUT("/transfer", request)
                        .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate())));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Account 1 does not have sufficient balance of 1", exception.getMessage());
    }

    @Test
    public void create_shouldReturn400_givenCurrenciesDoNotMatch() {
        dataUtil.accounts.insertEurAccountWithInitialBalance("1", BigDecimal.ONE);
        dataUtil.accounts.insertAccount2WithGbpCurrency();
        final CreateTransferRequest request = buildCreateTransferRequest("1", "2");
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest
                        .PUT("/transfer", request)
                        .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate())));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Currencies for the source and target accounts do not match", exception.getMessage());
    }

    @Test
    public void create_shouldReturn400_givenAnotherTransferWasCreatedWithSameIdempotencyKey() {
        final BigDecimal initialBalance = BigDecimal.valueOf(2);
        dataUtil.accounts.insertEurAccountWithInitialBalance("1", initialBalance);
        dataUtil.accounts.insertAccount2WithEurCurrency();
        final String idempotencyKey = createTransferAndAssert(initialBalance);
        final CreateTransferRequest request = buildCreateTransferRequest("1", "2");
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest
                        .PUT("/transfer", request)
                        .header(Headers.IDEMPOTENCY_KEY_HEADER, idempotencyKey)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Sorry, we were unable to find the data you are referring to", exception.getMessage());
    }

    @Test
    public void create_shouldCreateNewTransfer_givenData() {
        final BigDecimal initialBalance = BigDecimal.ONE;
        dataUtil.accounts.insertEurAccountWithInitialBalance("1", initialBalance);
        dataUtil.accounts.insertAccount2WithEurCurrency();
        createTransferAndAssert(initialBalance);
    }

    @Test
    public void create_shouldCreateNewTransfersCorrectly_givenHighLoad() throws InterruptedException {
        final int nThreads = 32;
        final BigDecimal initialBalance = new BigDecimal(nThreads);
        dataUtil.accounts.insertEurAccountWithInitialBalance("1", initialBalance);
        dataUtil.accounts.insertEurAccountWithInitialBalance("2", initialBalance);
        final ExecutorService executorService1 = getExecutorService(nThreads, "1", "2");
        final ExecutorService executorService2 = getExecutorService(nThreads, "2", "1");
        executorService1.shutdown();
        executorService2.shutdown();
        executorService1.awaitTermination(10, TimeUnit.MINUTES);
        executorService2.awaitTermination(10, TimeUnit.MINUTES);
        assertSystemStateIsValid(nThreads, initialBalance);
    }

    private void assertAccountsBalanceAfterTransfer(final BigDecimal initialBalance) {
        final BalanceSnapshotDto account1BalanceSnapshot = dataUtil.balanceSnapshots.getAccount1BalanceSnapshot();
        assertEquals(initialBalance.subtract(BigDecimal.ONE), account1BalanceSnapshot.getBalance());
        assertEquals(1, account1BalanceSnapshot.getVersion());

        final BalanceSnapshotDto account2BalanceSnapshot = dataUtil.balanceSnapshots.getAccount2BalanceSnapshot();
        assertEquals(BigDecimal.ONE, account2BalanceSnapshot.getBalance());
        assertEquals(1, account2BalanceSnapshot.getVersion());
    }

    private void assertLedgerStateAfterTransfer(final String fromAccountId, final String toAccountId, final CreateTransferResponse transferResponse) {
        final List<LedgerDto> ledgerDtoList = dataUtil.ledgers.findAll();
        assertEquals(2, ledgerDtoList.size());
        final LedgerDto debit = ledgerDtoList.get(0);
        final LedgerDto credit = ledgerDtoList.get(1);
        assertEquals(BigDecimal.ONE.negate(), debit.getAmount());
        assertEquals(BigDecimal.ONE, credit.getAmount());
        assertEquals(transferResponse.getId(), debit.getTransactionId());
        assertEquals(transferResponse.getId(), credit.getTransactionId());
        assertEquals(fromAccountId, debit.getAccountId());
        assertEquals(toAccountId, credit.getAccountId());
        assertNotNull(debit.getCreatedAt());
        assertNotNull(credit.getCreatedAt());
        assertUuid(debit.getId());
        assertUuid(credit.getId());
    }

    private void assertSystemStateIsValid(final int nThreads, final BigDecimal initialBalance) {
        final BalanceSnapshotDto account1BalanceSnapshot = dataUtil.balanceSnapshots.getAccount1BalanceSnapshot();
        final BalanceSnapshotDto account2BalanceSnapshot = dataUtil.balanceSnapshots.getAccount2BalanceSnapshot();
        final int expectedVersion = nThreads * 2;
        assertEquals(expectedVersion, account1BalanceSnapshot.getVersion());
        assertEquals(expectedVersion, account2BalanceSnapshot.getVersion());
        assertEquals(initialBalance, account1BalanceSnapshot.getBalance());
        assertEquals(initialBalance, account2BalanceSnapshot.getBalance());
        final List<LedgerDto> ledgerDtoList = dataUtil.ledgers.findAll();
        final int expectedNumberOfLedgerEntries = nThreads * 4;
        assertEquals(expectedNumberOfLedgerEntries, ledgerDtoList.size());
        assertEquals(BigDecimal.ZERO, ledgerDtoList.stream().filter(e -> e.getAccountId().equals("1")).map(LedgerDto::getAmount).reduce(BigDecimal::add).orElseThrow());
        assertEquals(BigDecimal.ZERO, ledgerDtoList.stream().filter(e -> e.getAccountId().equals("2")).map(LedgerDto::getAmount).reduce(BigDecimal::add).orElseThrow());
        final List<TransferDto> transferDtoList = dataUtil.transfers.findAll();
        final int expectedNumberOfTransfers = nThreads * 2;
        assertEquals(expectedNumberOfTransfers, transferDtoList.size());
        assertTrue(transferDtoList.stream().allMatch(t -> BigDecimal.ONE.equals(t.getAmount())));
        assertEquals(nThreads, transferDtoList.stream().filter(t -> t.getFromAccountId().equals("1")).count());
        assertEquals(nThreads, transferDtoList.stream().filter(t -> t.getFromAccountId().equals("2")).count());
    }

    private ExecutorService getExecutorService(final int nThreads, final String fromAccountId, final String toAccountId) {
        final CreateTransferRequest request = buildCreateTransferRequest(fromAccountId, toAccountId);
        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            executorService.submit(() -> {
                while (true) {
                    try {
                        final HttpResponse<CreateTransferResponse> response = client
                                .toBlocking()
                                .exchange(HttpRequest.PUT("/transfer", request).header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate()),
                                        CreateTransferResponse.class);
                        break;
                    } catch (Exception ignored) {
                    }
                }
            });
        }
        return executorService;
    }

    private String createTransferAndAssert(final BigDecimal initialBalance) {
        final String fromAccountId = "1";
        final String toAccountId = "2";
        final CreateTransferRequest request = buildCreateTransferRequest(fromAccountId, toAccountId);
        final String idempotencyKey = IdGenerator.generate();
        final HttpResponse<CreateTransferResponse> response = client
                .toBlocking()
                .exchange(HttpRequest
                                .PUT("/transfer", request)
                                .header(Headers.IDEMPOTENCY_KEY_HEADER, idempotencyKey),
                        CreateTransferResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        final CreateTransferResponse transferResponse = response.body();
        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getId());

        assertLedgerStateAfterTransfer(fromAccountId, toAccountId, transferResponse);
        assertAccountsBalanceAfterTransfer(initialBalance);
        return idempotencyKey;
    }

    private CreateTransferRequest buildCreateTransferRequest(final String fromAccountId, final String toAccountId) {
        return CreateTransferRequest
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(BigDecimal.ONE)
                .build();
    }
}
