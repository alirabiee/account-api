package ee.rabi.ali.api.account.app.ledger.service.impl;

import ee.rabi.ali.api.account.app.balance_snapshot.service.BalanceSnapshotService;
import ee.rabi.ali.api.account.app.balance_snapshot.service.exception.InsufficientBalanceException;
import ee.rabi.ali.api.account.app.ledger.repository.LedgerRepository;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.aspect.Transactional;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;
    private final BalanceSnapshotService balanceSnapshotService;

    @Override
    @Transactional
    public BigDecimal getBalance(String accountId) {
        return ledgerRepository.getBalance(accountId);
    }

    @Override
    @Transactional
    public void create(LedgerDto... ledgerDtos) throws InsufficientBalanceException {
        for (LedgerDto ledgerDto : ledgerDtos) {
            ledgerRepository.insert(ledgerDto.toRecord());
            // the following line indicates how strict we'd like to be in regards to overdraft
            // currently, it's synchronous with hard limit of min 0
            balanceSnapshotService.updateBalance(ledgerDto.getAccountId(), ledgerDto.getAmount());
        }
    }
}
