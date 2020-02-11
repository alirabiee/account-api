package ee.rabi.ali.api.account.app.common.repository;

import ee.rabi.ali.api.account.orm.TransactionManager;
import lombok.RequiredArgsConstructor;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;

@RequiredArgsConstructor
public abstract class Repository {
    protected final TransactionManager txMgr;

    public <T extends TableRecord<T>> void insert(T record) {
        txMgr.getContext().executeInsert(record);
    }

    public <T extends UpdatableRecord<T>> int update(final T record) {
        return txMgr.getContext().executeUpdate(record);
    }
}
