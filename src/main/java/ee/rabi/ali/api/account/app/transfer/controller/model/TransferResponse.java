package ee.rabi.ali.api.account.app.transfer.controller.model;

import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
