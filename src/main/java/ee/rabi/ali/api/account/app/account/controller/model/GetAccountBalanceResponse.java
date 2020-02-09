package ee.rabi.ali.api.account.app.account.controller.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class GetAccountBalanceResponse {
    private BigDecimal balance;
}
