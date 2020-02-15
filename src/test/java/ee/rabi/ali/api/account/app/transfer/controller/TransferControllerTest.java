package ee.rabi.ali.api.account.app.transfer.controller;

import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferRequest;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferResponse;
import ee.rabi.ali.api.account.app.transfer.controller.model.TransferResponse;
import ee.rabi.ali.api.account.test.IntegrationTest;
import ee.rabi.ali.api.account.test.data.account.AccountTestData;
import ee.rabi.ali.api.account.test.data.balance_snapshot.BalanceSnapshotTestData;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;
import ee.rabi.ali.api.account.test.data.transfer.TransferTestData;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static ee.rabi.ali.api.account.test.util.Assertions.assertUuid;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class TransferControllerTest extends IntegrationTest {
    @Inject
    private TransferTestData transferTestData;
    @Inject
    private AccountTestData accountTestData;
    @Inject
    private BalanceSnapshotTestData balanceSnapshotTestData;
    @Inject
    private LedgerTestData ledgerTestData;

    @Test
    public void list_shouldReturnAllTransfers_givenData() {
        accountTestData.insertAccount1WithEurCurrencyWithInitialBalanceOf1();
        accountTestData.insertAccount2WithEurCurrencyWithInitialBalanceOf1();
        transferTestData.transfer1EurFromAccount1ToAccount2();
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
        accountTestData.insertAccount1WithEurCurrency();
        final CreateTransferRequest request = CreateTransferRequest
                .builder()
                .fromAccountId("1")
                .toAccountId("2")
                .amount(BigDecimal.ONE)
                .build();
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.PUT("/transfer", request)));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Could not find account 2", exception.getMessage());
    }

    @Test
    public void create_shouldReturn404_givenFromAccountDoesNotExist() {
        accountTestData.insertAccount2WithEurCurrency();
        final CreateTransferRequest request = CreateTransferRequest
                .builder()
                .fromAccountId("1")
                .toAccountId("2")
                .amount(BigDecimal.ONE)
                .build();
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.PUT("/transfer", request)));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Could not find account 1", exception.getMessage());
    }

    @Test
    public void create_shouldReturn400_givenFromAccountDoesNotHaveEnoughBalance() {
        accountTestData.insertAccount1WithEurCurrency();
        accountTestData.insertAccount2WithEurCurrencyWithInitialBalanceOf1();
        final CreateTransferRequest request = CreateTransferRequest
                .builder()
                .fromAccountId("1")
                .toAccountId("2")
                .amount(BigDecimal.ONE)
                .build();
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.PUT("/transfer", request)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Account 1 does not have sufficient balance of 1", exception.getMessage());
    }

    @Test
    public void create_shouldReturn400_givenCurrenciesDoNotMatch() {
        accountTestData.insertAccount1WithEurCurrencyWithInitialBalanceOf1();
        accountTestData.insertAccount2WithGbpCurrency();
        final CreateTransferRequest request = CreateTransferRequest
                .builder()
                .fromAccountId("1")
                .toAccountId("2")
                .amount(BigDecimal.ONE)
                .build();
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().exchange(HttpRequest.PUT("/transfer", request)));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Currencies for the source and target accounts do not match", exception.getMessage());
    }

    @Test
    public void create_shouldCreateNewTransfer_givenData() {
        accountTestData.insertAccount1WithEurCurrencyWithInitialBalanceOf1();
        accountTestData.insertAccount2WithEurCurrency();
        final String fromAccountId = "1";
        final String toAccountId = "2";
        final CreateTransferRequest request = CreateTransferRequest
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(BigDecimal.ONE)
                .build();
        final HttpResponse<CreateTransferResponse> response = client.toBlocking().exchange(HttpRequest.PUT("/transfer", request), CreateTransferResponse.class);
        assertEquals(HttpStatus.CREATED, response.getStatus());
        final CreateTransferResponse transferResponse = response.body();
        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getId());

        final List<LedgerDto> ledgerDtoList = ledgerTestData.findAll();
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

        final BalanceSnapshotDto account1BalanceSnapshot = balanceSnapshotTestData.getAccount1BalanceSnapshot();
        assertEquals(BigDecimal.ZERO, account1BalanceSnapshot.getBalance());
        assertEquals(1, account1BalanceSnapshot.getVersion());

        final BalanceSnapshotDto account2BalanceSnapshot = balanceSnapshotTestData.getAccount2BalanceSnapshot();
        assertEquals(BigDecimal.ONE, account2BalanceSnapshot.getBalance());
        assertEquals(1, account2BalanceSnapshot.getVersion());
    }
}
