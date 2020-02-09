package ee.rabi.ali.api.account.app.account.service;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import io.micronaut.validation.Validated;

@Validated
public interface AccountService {
    AccountDto create();
}
