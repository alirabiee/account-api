package ee.rabi.ali.api.account.app.common.data;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.TransactionManager;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
public abstract class Repository {
    protected final TransactionManager transactionManager;

    public void insert(@Valid ServiceDto serviceDto) {
        transactionManager.run((context -> {
            context.executeInsert(serviceDto.toRecord());
        }));
    }
}
