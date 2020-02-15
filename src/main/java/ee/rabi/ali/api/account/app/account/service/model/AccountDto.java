package ee.rabi.ali.api.account.app.account.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Currency;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Data
@Builder
@AllArgsConstructor
public class AccountDto implements ServiceDto<AccountRecord> {
    private String id;
    private Currency currency;
    private String idempotencyKey;

    public static AccountDtoBuilder prepare() {
        return AccountDto.builder().id(generate());
    }

    public static AccountDto from(AccountRecord record) {
        return AccountDto
                .builder()
                .id(record.getId())
                .currency(Currency.getInstance(record.getCurrency()))
                .idempotencyKey(record.getIdempotencyKey())
                .build();
    }

    @Override
    public AccountRecord toRecord() {
        return new AccountRecord(id, currency.getCurrencyCode(), idempotencyKey);
    }
}
