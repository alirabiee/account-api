package ee.rabi.ali.api.account.app.account.controller;

import ee.rabi.ali.api.account.app.account.controller.model.AccountResponse;
import ee.rabi.ali.api.account.app.account.controller.model.CreateAccountRequest;
import ee.rabi.ali.api.account.app.account.controller.model.GetAccountBalanceResponse;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(tags = "Accounts", summary = "List accounts")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class))))
    public List<AccountResponse> list() {
        return accountService
                .list()
                .stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
    }

    @Put
    @Operation(tags = "Accounts", summary = "Create an account")
    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    public HttpResponse<AccountResponse> create(@Valid @Body CreateAccountRequest createAccountRequest) {
        return HttpResponse.created(
                AccountResponse.from(accountService
                        .create(createAccountRequest.toCreateAccountDto())));
    }

    @Get("/{accountId}/balance")
    @Operation(tags = "Accounts", summary = "Get an account's balance")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GetAccountBalanceResponse.class)))
    @ApiResponse(responseCode = "404", description = "Account not found")
    public GetAccountBalanceResponse getBalance(@NotBlank String accountId) {
        return GetAccountBalanceResponse
                .builder()
                .balance(balanceSnapshotService.getBalanceForAccountId(accountId))
                .build();
    }
}
