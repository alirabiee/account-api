package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.AccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final LedgerService ledgerService;

    @Get
    public List<AccountResponse> list() {
        return accountService
                .list()
                .stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
    }

    @Put
    public AccountResponse create() {
        return AccountResponse
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
