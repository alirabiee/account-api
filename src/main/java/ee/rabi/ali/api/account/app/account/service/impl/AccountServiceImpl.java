package ee.rabi.ali.api.account.app.account.service.impl;

import ee.rabi.ali.api.account.app.account.repository.AccountRepository;
import ee.rabi.ali.api.account.app.account.service.AccountService;
import ee.rabi.ali.api.account.app.account.service.model.AccountDto;
import ee.rabi.ali.api.account.orm.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public List<AccountDto> list() {
        return accountRepository.findAll().stream().map(AccountDto::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto create() {
        final AccountDto accountDto = AccountDto.prepare().build();
        accountRepository.insert(accountDto);
        return accountDto;
    }
}
