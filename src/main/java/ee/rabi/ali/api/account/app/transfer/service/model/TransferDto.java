package ee.rabi.ali.api.account.app.transfer.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Data
@Builder
public class TransferDto implements ServiceDto<TransferRecord> {
    @NotBlank
    private String id;
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotBlank
    private String idempotencyKey;
    @NotNull
    @Min(1)
    private BigDecimal amount;
    @NotNull
    private Timestamp createdAt;

    public static TransferDto from(TransferRecord record) {
        return TransferDto
                .builder()
                .id(record.getId())
                .fromAccountId(record.getFromAccountId())
                .toAccountId(record.getToAccountId())
                .amount(record.getAmount())
                .createdAt(record.getCreatedAt())
                .idempotencyKey(record.getIdempotencyKey())
                .build();
    }

    public static TransferDto from(CreateTransferDto dto) {
        return prepare()
                .amount(dto.getAmount())
                .fromAccountId(dto.getFromAccountId())
                .toAccountId(dto.getToAccountId())
                .idempotencyKey(dto.getIdempotencyKey())
                .build();
    }

    @Override
    public TransferRecord toRecord() {
        return new TransferRecord(id, fromAccountId, toAccountId, idempotencyKey, amount, createdAt);
    }

    public LedgerDto[] toLedgerDtos() {
        return new LedgerDto[]{buildDebit(), buildCredit()};
    }

    private static TransferDtoBuilder prepare() {
        return TransferDto
                .builder()
                .id(generate())
                .createdAt(Timestamp.from(Instant.now()));
    }

    private LedgerDto buildDebit() {
        return LedgerDto.prepare().accountId(fromAccountId).amount(amount.negate()).transactionId(id).build();
    }

    private LedgerDto buildCredit() {
        return LedgerDto.prepare().accountId(toAccountId).amount(amount).transactionId(id).build();
    }
}
