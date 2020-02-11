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
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @Put
    public CreateTransferResponse create(@Valid @Body CreateTransferRequest createTransferRequest) throws InsufficientBalanceException {
        return CreateTransferResponse
                .from(transferService.create(createTransferRequest.toCreateTransferDto()));
    }

    @Get
    public List<TransferResponse> list() {
        return transferService
                .list()
                .stream()
                .map(TransferResponse::from)
                .collect(Collectors.toList());
    }
}
