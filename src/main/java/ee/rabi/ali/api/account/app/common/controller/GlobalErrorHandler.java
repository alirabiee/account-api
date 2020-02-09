package ee.rabi.ali.api.account.app.common.controller;

import ee.rabi.ali.api.account.app.common.controller.model.ErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import org.jooq.exception.DataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
public class GlobalErrorHandler {

    public static final String GENERAL_ERROR_MESSAGE = "Sorry, we're unable to process your request at this moment";

    @Error(global = true)
    public HttpResponse<ErrorResponse> jsonError(HttpRequest request, DataAccessException ex) {
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
}
