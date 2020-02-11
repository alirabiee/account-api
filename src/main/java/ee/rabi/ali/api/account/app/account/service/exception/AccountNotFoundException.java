package ee.rabi.ali.api.account.app.account.service.exception;

import ee.rabi.ali.api.account.app.common.service.exception.ApplicationRuntimeException;

public class AccountNotFoundException extends ApplicationRuntimeException {

    private static final String MESSAGE = "Could not find account %s";

    public AccountNotFoundException(final String accountId) {
        super(String.format(MESSAGE, accountId));
    }
}
