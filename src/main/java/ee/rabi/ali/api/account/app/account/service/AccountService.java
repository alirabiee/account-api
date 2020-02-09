package ee.rabi.ali.api.account.app.account.service;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import io.micronaut.validation.Validated;

import java.util.List;

@Validated
public interface AccountService {
    List<AccountDto> list();

    AccountDto create();
}
