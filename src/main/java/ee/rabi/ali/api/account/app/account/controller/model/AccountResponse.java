package ee.rabi.ali.api.account.app.account.controller.model;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountResponse {
    private String id;

    public static AccountResponse from(AccountDto dto) {
        return AccountResponse.builder().id(dto.getId()).build();
    }
}
