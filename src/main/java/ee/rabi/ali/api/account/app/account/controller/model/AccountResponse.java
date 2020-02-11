package ee.rabi.ali.api.account.app.account.controller.model;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import lombok.Builder;
import lombok.Value;

import java.util.Currency;

@Value
@Builder
public class AccountResponse {
    private String id;
    private Currency currency;

    public static AccountResponse from(AccountDto dto) {
        return AccountResponse.builder().id(dto.getId()).currency(dto.getCurrency()).build();
    }
}
