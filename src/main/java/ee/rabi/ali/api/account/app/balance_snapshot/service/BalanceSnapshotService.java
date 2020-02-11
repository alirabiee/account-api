package ee.rabi.ali.api.account.app.balance_snapshot.service;

import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import io.micronaut.validation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
public interface BalanceSnapshotService {

    BigDecimal getBalanceForAccountId(@NotBlank String accountId);

    BalanceSnapshotDto create(@NotBlank String accountId);

    void updateBalance(@NotBlank String accountId, @NotNull BigDecimal delta) throws InsufficientBalanceException;
}
