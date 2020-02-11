package ee.rabi.ali.api.account.app.transfer.service.impl;

import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.transfer.repository.TransferRepository;
import ee.rabi.ali.api.account.app.transfer.service.TransferService;
import ee.rabi.ali.api.account.app.transfer.service.exception.TransferCurrenciesMismatchException;
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
    private final AccountService accountService;

    @Override
    @Transactional
    public TransferDto create(CreateTransferDto createTransferDto) throws InsufficientBalanceException {
        validateCurrenciesMatch(createTransferDto);
        final TransferDto transferDto = TransferDto.from(createTransferDto);
        transferRepository.insert(transferDto.toRecord());
        ledgerService.create(transferDto.toLedgerDtos());
        return transferDto;
    }

    private void validateCurrenciesMatch(final CreateTransferDto createTransferDto) {
        final AccountDto fromAccountDto = accountService.find(createTransferDto.getFromAccountId());
        final AccountDto toAccountDto = accountService.find(createTransferDto.getToAccountId());

        if (!fromAccountDto.getCurrency().equals(toAccountDto.getCurrency())) {
            throw new TransferCurrenciesMismatchException();
        }
    }

    @Override
    @Transactional
    public List<TransferDto> list() {
        return transferRepository.findAll().stream().map(TransferDto::from).collect(Collectors.toList());
    }

}
