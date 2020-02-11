package ee.rabi.ali.api.account.app.account.service;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.account.service.model.CreateAccountDto;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface AccountService {

    List<AccountDto> list();

    AccountDto find(@NotBlank String accountId);

    AccountDto create(@NotNull @Valid CreateAccountDto createAccountDto);
}
