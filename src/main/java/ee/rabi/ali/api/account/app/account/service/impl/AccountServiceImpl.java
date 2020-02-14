package ee.rabi.ali.api.account.app.account.service.impl;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.exception.AccountNotFoundException;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.app.account.service.model.CreateAccountDto;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.aspect.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final String INITIAL_BALANCE_TXID = "INITIAL_BALANCE";

    private final AccountRepository accountRepository;
    private final BalanceSnapshotService balanceSnapshotService;
    private final LedgerService ledgerService;

    @Override
    public List<AccountDto> list() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto find(String accountId) {
        return accountRepository
                .findById(accountId)
                .map(AccountDto::from)
                .orElseThrow(() -> {
                    log.error("account-service:not-found account-id={}", accountId);
                    return new AccountNotFoundException(accountId);
                });
    }

    @Override
    @Transactional
    public AccountDto create(CreateAccountDto createAccountDto) {
        final AccountDto accountDto = AccountDto
                .prepare()
                .currency(createAccountDto.getCurrency())
                .build();
        accountRepository.insert(accountDto.toRecord());
        balanceSnapshotService.create(accountDto.getId());
        addInitialBalance(createAccountDto.getInitialBalance(), accountDto);
        return accountDto;
    }

    private void addInitialBalance(final BigDecimal initialBalance, final AccountDto accountDto) {
        try {
            ledgerService.create(LedgerDto
                    .prepare()
                    .transactionId(INITIAL_BALANCE_TXID)
                    .accountId(accountDto.getId())
                    .amount(initialBalance)
                    .build());
        } catch (InsufficientBalanceException e) {
            log.error("account-service:create", e);
        }
    }
}
