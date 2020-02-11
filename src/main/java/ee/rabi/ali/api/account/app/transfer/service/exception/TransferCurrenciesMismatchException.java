package ee.rabi.ali.api.account.app.transfer.service.exception;

import ee.rabi.ali.api.account.app.common.service.exception.ApplicationRuntimeException;

public class TransferCurrenciesMismatchException extends ApplicationRuntimeException {

    public TransferCurrenciesMismatchException() {
        super("Currencies for the source and target accounts do not match");
    }
}
