package ee.rabi.ali.api.account.app.common.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String message;
}
