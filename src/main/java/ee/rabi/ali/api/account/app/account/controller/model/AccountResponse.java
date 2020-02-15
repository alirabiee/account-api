package ee.rabi.ali.api.account.app.account.controller.model;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String id;
    private Currency currency;

    public static AccountResponse from(AccountDto dto) {
        return AccountResponse
                .builder()
                .id(dto.getId())
                .currency(dto.getCurrency())
                .build();
    }
}
