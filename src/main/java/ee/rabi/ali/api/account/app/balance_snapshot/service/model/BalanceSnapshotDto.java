package ee.rabi.ali.api.account.app.balance_snapshot.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.model.tables.records.BalanceSnapshotRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class BalanceSnapshotDto implements ServiceDto<BalanceSnapshotRecord> {
    private String accountId;
    private BigDecimal balance;
    private Timestamp updatedAt;
    private int version;

    public static BalanceSnapshotDto buildNew(String accountId) {
        return BalanceSnapshotDto
                .builder()
                .accountId(accountId)
                .balance(BigDecimal.ZERO)
                .updatedAt(Timestamp.from(Instant.now()))
                .version(0)
                .build();
    }

    @Override
    public BalanceSnapshotRecord toRecord() {
        return new BalanceSnapshotRecord(accountId, balance, updatedAt, version);
    }
}
