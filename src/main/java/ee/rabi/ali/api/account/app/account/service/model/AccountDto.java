package ee.rabi.ali.api.account.app.account.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Data
@Builder
@AllArgsConstructor
public class AccountDto implements ServiceDto {
    private String id;

    public static AccountDtoBuilder prepare() {
        return AccountDto.builder().id(generate());
    }

    @Override
    public AccountRecord toRecord() {
        return new AccountRecord(id);
    }
}
