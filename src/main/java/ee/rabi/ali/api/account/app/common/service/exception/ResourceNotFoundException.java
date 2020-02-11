package ee.rabi.ali.api.account.app.common.service.exception;

public class ResourceNotFoundException extends ApplicationRuntimeException {
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
