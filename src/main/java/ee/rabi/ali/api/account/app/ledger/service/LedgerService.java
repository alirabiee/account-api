package ee.rabi.ali.api.account.app.ledger.service;

import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
public interface LedgerService {

    BigDecimal getBalance(@NotBlank String accountId);

    void create(@NotNull @NotEmpty LedgerDto... ledgerDtos) throws InsufficientBalanceException;
}
