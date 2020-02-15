package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.AccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.test.IntegrationTest;
import ee.rabi.ali.api.account.test.data.account.AccountTestData;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class AccountControllerTest extends IntegrationTest {
    @Inject
    private AccountTestData accountTestData;
    @Inject
    private LedgerTestData ledgerTestData;

    @Test
    void list_shouldReturnAllAccounts_givenData() {
        accountTestData.insertAccount1WithEUR();
        List<AccountResponse> response = client
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
        accountTestData.insertAccount1WithEUR();
        ledgerTestData.creditAccount1By1();
        GetAccountBalanceResponse response = client
                .toBlocking()
                .retrieve(HttpRequest.GET("/account/1/balance"), GetAccountBalanceResponse.class);
        final GetAccountBalanceResponse expected = GetAccountBalanceResponse
                .builder()
                .balance(BigDecimal.ONE)
                .build();
        assertEquals(expected, response);
    }
}
