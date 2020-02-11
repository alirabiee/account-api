package ee.rabi.ali.api.account.app.balance_snapshot.service.exception;

import ee.rabi.ali.api.account.app.common.service.exception.ApplicationException;

import java.math.BigDecimal;

public class InsufficientBalanceException extends ApplicationException {

    private static final String MESSAGE = "Account %s does not have sufficient balance of %s";

    public InsufficientBalanceException(final String accountId, final BigDecimal delta) {
        super(String.format(MESSAGE, accountId, delta.abs()));
    }
}
