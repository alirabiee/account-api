package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final LedgerService ledgerService;

    @Get
    public List<AccountDto> list() {
        return accountService.list();
    }

    @Put
    public CreateAccountResponse create() {
        return CreateAccountResponse
                .from(accountService.create());
    }

    @Get("/{accountId}/balance")
    public GetAccountBalanceResponse getBalance(@NotBlank String accountId) {
        return GetAccountBalanceResponse
                .builder()
                .balance(ledgerService.getBalance(accountId))
                .build();
    }
}
