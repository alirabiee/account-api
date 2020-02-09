package ee.rabi.ali.api.account.app.account.controller.model;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateAccountResponse {
    private String id;

    public static CreateAccountResponse from(AccountDto dto) {
        return CreateAccountResponse.builder().id(dto.getId()).build();
    }
}
