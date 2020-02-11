package ee.rabi.ali.api.account.app.account.controller.model;

import ee.rabi.ali.api.account.app.account.service.model.CreateAccountDto;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Data
@Introspected
@NoArgsConstructor
public class CreateAccountRequest {
    @Min(0)
    private BigDecimal initialBalance;
    @NotNull
    private Currency currency;

    public CreateAccountDto toCreateAccountDto() {
        return CreateAccountDto
                .builder()
                .initialBalance(Optional.ofNullable(initialBalance).orElse(BigDecimal.ZERO))
                .currency(currency)
                .build();
    }
}
