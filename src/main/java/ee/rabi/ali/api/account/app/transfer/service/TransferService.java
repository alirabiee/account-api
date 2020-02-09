package ee.rabi.ali.api.account.app.transfer.service;

import ee.rabi.ali.api.account.app.transfer.service.model.CreateTransferDto;
import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface TransferService {
    TransferDto create(@Valid @NotNull CreateTransferDto createTransferDto);
}
