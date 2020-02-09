package ee.rabi.ali.api.account.app.transfer.service.model;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.model.tables.records.TransferRecord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

import static ee.rabi.ali.api.account.orm.IdGenerator.generate;

@Data
@Builder
public class TransferDto implements ServiceDto {
    @NotBlank
    private String id;
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    @Min(1)
    private Long amount;
    @NotNull
    private Timestamp createdAt;

    public static TransferDtoBuilder prepare() {
        return TransferDto.builder().id(generate()).createdAt(Timestamp.from(Instant.now()));
    }

    public static TransferDto from(CreateTransferDto dto) {
        return prepare()
                .amount(dto.getAmount())
                .fromAccountId(dto.getFromAccountId())
                .toAccountId(dto.getToAccountId())
                .build();
    }

    @Override
    public TransferRecord toRecord() {
        return new TransferRecord(id, fromAccountId, toAccountId, amount, createdAt);
    }

    public LedgerDto[] toLedgerDtos() {
        return new LedgerDto[]{buildCredit(), buildDebit()};
    }

    private LedgerDto buildDebit() {
        return LedgerDto.prepare().accountId(fromAccountId).amount(-amount).transactionId(id).build();
    }

    private LedgerDto buildCredit() {
        return LedgerDto.prepare().accountId(toAccountId).amount(amount).transactionId(id).build();
    }
}
