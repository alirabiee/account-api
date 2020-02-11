package ee.rabi.ali.api.account.app.common.service.exception;

public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(final String message) {
        super(message);
    }
}
