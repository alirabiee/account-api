package ee.rabi.ali.api.account.app.account.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ee.rabi.ali.api.account.app.account.service.model.CreateAccountDto;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Data
@Builder
@Introspected
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAccountRequest {
    @Min(0)
    private BigDecimal initialBalance;
    @NotNull
    private Currency currency;

    public CreateAccountDto toCreateAccountDto(@NotBlank final String idempotencyKey) {
        return CreateAccountDto
                .builder()
                .initialBalance(Optional.ofNullable(initialBalance).orElse(BigDecimal.ZERO))
                .currency(currency)
                .idempotencyKey(idempotencyKey)
                .build();
    }
}
