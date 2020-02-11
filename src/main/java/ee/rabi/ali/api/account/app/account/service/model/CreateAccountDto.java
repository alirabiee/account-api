package ee.rabi.ali.api.account.app.account.service.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@Builder
@Introspected
public class CreateAccountDto {
    @NotNull
    @Min(0)
    private BigDecimal initialBalance;
    @NotNull
    private Currency currency;
}
