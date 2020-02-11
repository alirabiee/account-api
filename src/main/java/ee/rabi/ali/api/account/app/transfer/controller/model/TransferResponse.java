package ee.rabi.ali.api.account.app.transfer.controller.model;

import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TransferResponse {
    private String id;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private Timestamp createdAt;

    public static TransferResponse from(TransferDto dto) {
        return TransferResponse
                .builder()
                .id(dto.getId())
                .fromAccountId(dto.getFromAccountId())
                .toAccountId(dto.getToAccountId())
                .amount(dto.getAmount())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
