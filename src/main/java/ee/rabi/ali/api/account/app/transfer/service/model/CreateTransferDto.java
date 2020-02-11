package ee.rabi.ali.api.account.app.transfer.service.model;


import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@Introspected
public class CreateTransferDto {
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    @Min(1)
    private BigDecimal amount;
}
