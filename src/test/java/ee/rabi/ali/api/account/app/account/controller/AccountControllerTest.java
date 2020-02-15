package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.AccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountRequest;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
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
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static ee.rabi.ali.api.account.test.util.Assertions.assertUuid;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AccountControllerTest extends IntegrationTest {

    @Test
    void list_shouldReturnAllAccounts_givenData() {
        dataUtil.accounts.insertAccount1WithEurCurrency();
        final List<AccountResponse> response = client
                .toBlocking()
                .retrieve(HttpRequest.GET("/account"), Argument.listOf(AccountResponse.class));
        final List<AccountResponse> expected = Collections.singletonList(AccountResponse
                .builder()
                .id("1")
                .currency(Currency.getInstance("EUR"))
                .build());
        assertEquals(expected, response);
    }

    @Test
    void balance_shouldReturnCorrectBalance_givenData() {
        dataUtil.accounts.insertAccount1WithEurCurrency();
        dataUtil.ledgers.creditAccount1By1();
        final HttpResponse<GetAccountBalanceResponse> response = client
                .toBlocking()
                .exchange(HttpRequest.GET("/account/1/balance"), GetAccountBalanceResponse.class);
        assertEquals(HttpStatus.OK.getCode(), response.code());
        final GetAccountBalanceResponse expected = GetAccountBalanceResponse
                .builder()
                .balance(BigDecimal.ONE)
                .build();
        assertEquals(expected, response.body());
    }

    @Test
    void balance_shouldReturn404_givenAccountDoesNotExist() {
        final HttpClientResponseException exception = assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(HttpRequest.GET("/account/1/balance")));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Could not find account 1", exception.getMessage());
    }

    @Test
    void create_shouldCreateNewAccount_givenData() {
        final Currency currency = Currency.getInstance("EUR");
        final CreateAccountRequest request = CreateAccountRequest
                .builder()
                .currency(currency)
                .build();
        final HttpResponse<AccountResponse> response = client
                .toBlocking()
                .exchange(HttpRequest
                                .PUT("/account", request)
                                .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate()),
                        AccountResponse.class);
        assertEquals(HttpStatus.CREATED.getCode(), response.code());
        final AccountResponse accountResponse = response.body();
        assertNotNull(accountResponse);
        assertUuid(accountResponse.getId());
        assertEquals(currency, accountResponse.getCurrency());
    }

    @Test
    void create_shouldCreateNewAccount_givenInitialBalance() {
        final Currency currency = Currency.getInstance("EUR");
        final BigDecimal initialBalance = BigDecimal.TEN;
        final CreateAccountRequest request = CreateAccountRequest
                .builder()
                .currency(currency)
                .initialBalance(initialBalance)
                .build();
        final HttpResponse<AccountResponse> response = client
                .toBlocking()
                .exchange(HttpRequest
                                .PUT("/account", request)
                                .header(Headers.IDEMPOTENCY_KEY_HEADER, IdGenerator.generate()),
                        AccountResponse.class);
        assertEquals(HttpStatus.CREATED.getCode(), response.code());
        final AccountResponse accountResponse = response.body();
        assertNotNull(accountResponse);
        assertUuid(accountResponse.getId());
        assertEquals(currency, accountResponse.getCurrency());
        final GetAccountBalanceResponse balanceResponse = client
                .toBlocking()
                .retrieve(HttpRequest.GET("/account/" + accountResponse.getId() + "/balance"), GetAccountBalanceResponse.class);
        assertEquals(initialBalance, balanceResponse.getBalance());
    }
}
