package ee.rabi.ali.api.account.app.transfer.controller.model;

import ee.rabi.ali.api.account.app.transfer.service.model.CreateTransferDto;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Introspected
public class CreateTransferRequest {
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    @Min(1)
    private Long amount;

    public CreateTransferDto toCreateTransferDto() {
        return CreateTransferDto
                .builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount)
                .build();
    }
}
