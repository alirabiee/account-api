package ee.rabi.ali.api.account.app.ledger.service.impl;

import ee.rabi.ali.api.account.app.ledger.data.LedgerRepository;
import ee.rabi.ali.api.account.app.ledger.service.LedgerService;
import ee.rabi.ali.api.account.app.ledger.service.model.LedgerDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository ledgerRepository;

    @Override
    public BigDecimal getBalance(String accountId) {
        return ledgerRepository.getBalance(accountId);
    }

    @Override
    public void create(LedgerDto... ledgerDtos) {
        for (LedgerDto ledgerDto : ledgerDtos) {
            ledgerRepository.insert(ledgerDto);
        }
    }
}
