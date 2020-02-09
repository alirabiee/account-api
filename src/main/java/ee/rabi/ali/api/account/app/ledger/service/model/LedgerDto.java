package ee.rabi.ali.api.account.app.ledger.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.model.tables.records.LedgerRecord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Data
@Builder
public class LedgerDto implements ServiceDto {
    @NotBlank
    private String id;
    @NotBlank
    private String accountId;
    @NotBlank
    private String transactionId;
    @NotNull
    private Long amount;
    @NotNull
    private Timestamp createdAt;

    public static LedgerDtoBuilder prepare() {
        return LedgerDto.builder().id(generate()).createdAt(Timestamp.from(Instant.now()));
    }

    @Override
    public LedgerRecord toRecord() {
        return new LedgerRecord(id, accountId, transactionId, amount, createdAt);
    }

}
