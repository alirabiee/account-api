package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Put
    public CreateAccountResponse create() {
        return CreateAccountResponse.from(accountService.create());
    }
}
