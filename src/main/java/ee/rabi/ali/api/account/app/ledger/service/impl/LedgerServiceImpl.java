package ee.rabi.ali.api.account.app.ledger.service.impl;

import ee.rabi.ali.api.account.app.ledger.repository.LedgerRepository;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import ee.rabi.ali.api.account.orm.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    @Override
    @Transactional
    public BigDecimal getBalance(String accountId) {
        return ledgerRepository.getBalance(accountId);
    }

    @Override
    @Transactional
    public void create(LedgerDto... ledgerDtos) {
        for (LedgerDto ledgerDto : ledgerDtos) {
            ledgerRepository.insert(ledgerDto);
        }
    }
}
