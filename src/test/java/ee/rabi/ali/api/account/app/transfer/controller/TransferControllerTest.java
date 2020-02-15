package ee.rabi.ali.api.account.app.transfer.controller;

import ee.rabi.ali.api.account.app.transfer.controller.model.TransferResponse;
import ee.rabi.ali.api.account.test.IntegrationTest;
import ee.rabi.ali.api.account.test.data.account.AccountTestData;
import ee.rabi.ali.api.account.test.data.ledger.LedgerTestData;
import ee.rabi.ali.api.account.test.data.transfer.TransferTestData;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static ee.rabi.ali.api.account.test.util.Assertions.assertUuid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class TransferControllerTest extends IntegrationTest {
    @Inject
    private TransferTestData transferTestData;
    @Inject
    private AccountTestData accountTestData;
    @Inject
    private LedgerTestData ledgerTestData;

    @Test
    public void list_shouldReturnAllTransfers_givenData() {
        accountTestData.insertAccount1WithEurCurrencyWithBalanceOf1();
        accountTestData.insertAccount2WithEurCurrencyWithBalanceOf1();
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
}
