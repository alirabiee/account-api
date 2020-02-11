package ee.rabi.ali.api.account.app.balance_snapshot.service.impl;

import ee.rabi.ali.api.account.app.account.service.exception.AccountNotFoundException;
import ee.rabi.ali.api.account.app.balance_snapshot.repository.BalanceSnapshotRepository;
import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.balance_snapshot.service.model.BalanceSnapshotDto;
import ee.rabi.ali.api.account.orm.aspect.Transactional;
import ee.rabi.ali.api.account.orm.model.tables.records.BalanceSnapshotRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class BalanceSnapshotServiceImpl implements BalanceSnapshotService {

    private final BalanceSnapshotRepository balanceSnapshotRepository;

    @Override
    public BigDecimal getBalanceForAccountId(String accountId) {
        return findByAccountId(accountId).getBalance();
    }

    @Override
    @Transactional
    public BalanceSnapshotDto create(String accountId) {
        final BalanceSnapshotDto dto = BalanceSnapshotDto.buildNew(accountId);
        balanceSnapshotRepository.insert(dto.toRecord());
        return dto;
    }

    @Override
    @Transactional
    public void updateBalance(String accountId, BigDecimal delta) throws InsufficientBalanceException {
        final BalanceSnapshotRecord record = findByAccountId(accountId);
        final BigDecimal currentBalance = record.getBalance();
        record.setBalance(currentBalance.add(delta));
        if (record.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            log.error("balance-snapshot-service:insufficient-balance account-id={} balance={} delta={}", accountId, currentBalance, delta);
            throw new InsufficientBalanceException(accountId, delta);
        }
        record.store();
    }

    private BalanceSnapshotRecord findByAccountId(final String accountId) {
        return balanceSnapshotRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> {
                    log.error("balance-snapshot-service:not-found account-id={}", accountId);
                    return new AccountNotFoundException(accountId);
                });
    }
}
