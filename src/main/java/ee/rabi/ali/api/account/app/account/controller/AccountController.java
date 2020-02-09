package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Put
    public CreateAccountResponse create() {
        return CreateAccountResponse
                .from(accountService.create());
    }

    @Get("/{accountId}/balance")
    public GetAccountBalanceResponse getBalance(@NotBlank String accountId) {
        return GetAccountBalanceResponse
                .builder()
                .balance(accountService.getBalance(accountId))
                .build();
    }
}
