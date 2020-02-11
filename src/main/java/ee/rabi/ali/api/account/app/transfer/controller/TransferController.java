package ee.rabi.ali.api.account.app.transfer.controller;

import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferRequest;
import ee.rabi.ali.api.account.app.transfer.controller.model.CreateTransferResponse;
import ee.rabi.ali.api.account.app.transfer.controller.model.TransferResponse;
import ee.rabi.ali.api.account.app.transfer.service.TransferService;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @Put
    @Operation(tags = "Transfers", summary = "Submit a transfer")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CreateTransferResponse.class)))
    @ApiResponse(responseCode = "400", description = "Insufficient balance")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public CreateTransferResponse create(@Valid @Body CreateTransferRequest createTransferRequest) throws InsufficientBalanceException {
        return CreateTransferResponse
                .from(transferService.create(createTransferRequest.toCreateTransferDto()));
    }

    @Get
    @Operation(tags = "Transfers", summary = "List transfers")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransferResponse.class))))
    public List<TransferResponse> list() {
        return transferService
                .list()
                .stream()
                .map(TransferResponse::from)
                .collect(Collectors.toList());
    }
}
