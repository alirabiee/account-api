package ee.rabi.ali.api.account.app.account.service;

import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Validated
public interface AccountService {
    AccountDto create();

    BigDecimal getBalance(@NotBlank String accountId);
}
