package ee.rabi.ali.api.account.app.transfer.controller.model;

import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferResponse {
    private String id;

    public static CreateTransferResponse from(TransferDto dto) {
        return CreateTransferResponse
                .builder()
                .id(dto.getId())
                .build();
    }
}
