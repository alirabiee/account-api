package ee.rabi.ali.api.account.app.common.repository;

import ee.rabi.ali.api.account.app.common.service.model.ServiceDto;
import ee.rabi.ali.api.account.orm.TransactionManager;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
public abstract class Repository {
    protected final TransactionManager txMgr;

    public void insert(@Valid ServiceDto serviceDto) {
        txMgr.getContext().executeInsert(serviceDto.toRecord());
    }
}
