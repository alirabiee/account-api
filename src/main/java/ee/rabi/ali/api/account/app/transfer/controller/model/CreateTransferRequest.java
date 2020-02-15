package ee.rabi.ali.api.account.app.transfer.controller.model;

import ee.rabi.ali.api.account.app.transfer.service.model.CreateTransferDto;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class CreateTransferRequest {
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    @Min(1)
    private BigDecimal amount;

    public CreateTransferDto toCreateTransferDto(@NotBlank final String idempotencyKey) {
        return CreateTransferDto
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount)
                .idempotencyKey(idempotencyKey)
                .build();
    }
}
