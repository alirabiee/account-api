package ee.rabi.ali.api.account.app.common.controller;

import ee.rabi.ali.api.account.app.common.controller.model.ErrorResponse;
import ee.rabi.ali.api.account.orm.exception.NonTransactionalContextException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import lombok.extern.slf4j.Slf4j;
import org.jooq.exception.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@Slf4j
public class GlobalErrorHandler {

    public static final String GENERAL_ERROR_MESSAGE = "Sorry, we're unable to process your request at this moment";

    @Error(global = true)
    public HttpResponse<ErrorResponse> dataAccessException(HttpRequest request, DataAccessException ex) {
        log.error("Exception", ex);

        String message = GENERAL_ERROR_MESSAGE;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
            message = "Sorry, we were unable to find the data you are referring to";
            status = HttpStatus.BAD_REQUEST;
        }

        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(message)
                .build();

        return HttpResponse.<ErrorResponse>status(status).body(errorResponse);
    }

    @Error(global = true)
    public HttpResponse<ErrorResponse> nonTransactionalContextException(HttpRequest request, NonTransactionalContextException ex) {
        log.error("Exception", ex);

        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(GENERAL_ERROR_MESSAGE)
                .build();

        return HttpResponse.<ErrorResponse>status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
