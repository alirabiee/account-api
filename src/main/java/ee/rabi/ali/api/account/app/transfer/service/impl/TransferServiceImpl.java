package ee.rabi.ali.api.account.app.transfer.service.impl;

import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.transfer.repository.TransferRepository;
import ee.rabi.ali.api.account.app.transfer.service.TransferService;
import ee.rabi.ali.api.account.app.transfer.service.model.CreateTransferDto;
import ee.rabi.ali.api.account.app.transfer.service.model.TransferDto;
import ee.rabi.ali.api.account.orm.aspect.Transactional;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Validated
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final LedgerService ledgerService;

    @Override
    @Transactional
    public TransferDto create(CreateTransferDto createTransferDto) {
        final TransferDto transferDto = TransferDto.from(createTransferDto);
        transferRepository.insert(transferDto);
        ledgerService.create(transferDto.toLedgerDtos());
        return transferDto;
    }

    @Override
    @Transactional
    public List<TransferDto> list() {
        return transferRepository.findAll().stream().map(TransferDto::from).collect(Collectors.toList());
    }

}
