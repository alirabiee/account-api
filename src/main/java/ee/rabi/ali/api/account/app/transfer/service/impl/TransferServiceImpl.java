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

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
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

    @Override
    public List<TransferDto> list() {
        return transferRepository.findAll().stream().map(TransferDto::from).collect(Collectors.toList());
    }

    private void validateCurrenciesMatch(final CreateTransferDto createTransferDto) {
        final String fromAccountId = createTransferDto.getFromAccountId();
        final String toAccountId = createTransferDto.getToAccountId();
        final AccountDto fromAccountDto = accountService.find(fromAccountId);
        final AccountDto toAccountDto = accountService.find(toAccountId);
        final Currency fromAccountCurrency = fromAccountDto.getCurrency();
        final Currency toAccountCurrency = toAccountDto.getCurrency();

        if (!fromAccountCurrency.equals(toAccountCurrency)) {
            log.error("transfer-service:currency-mismatch from-account-id={} ({}) to-account-id={} ({})", fromAccountId, fromAccountCurrency, toAccountId, toAccountCurrency);
            throw new TransferCurrenciesMismatchException();
        }
    }
}
