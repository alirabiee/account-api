package ee.rabi.ali.api.account.app.account.service.impl;

import ee.rabi.ali.api.account.app.account.data.AccountRepository;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountDto create() {
        final AccountDto accountDto = AccountDto.createNew();
        accountRepository.insert(accountDto);
        return accountDto;
    }

    @Override
    public BigDecimal getBalance(String accountId) {
        return accountRepository.getBalance(accountId);
    }
}
