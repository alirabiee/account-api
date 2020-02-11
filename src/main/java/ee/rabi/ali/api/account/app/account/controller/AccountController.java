package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.AccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountRequest;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BalanceSnapshotService balanceSnapshotService;

    @Get
    public List<AccountResponse> list() {
        return accountService
                .list()
                .stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
    }

    @Put
    public AccountResponse create(@Valid @Body CreateAccountRequest createAccountRequest) {
        return AccountResponse
                .from(accountService.create(createAccountRequest.toCreateAccountDto()));
    }

    @Get("/{accountId}/balance")
    public GetAccountBalanceResponse getBalance(@NotBlank String accountId) {
        return GetAccountBalanceResponse
                .builder()
                .balance(balanceSnapshotService.getBalanceForAccountId(accountId))
                .build();
    }
}
