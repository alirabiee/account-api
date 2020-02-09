package ee.rabi.ali.api.account.app.account.data;

import ee.rabi.ali.api.account.app.common.data.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Account;
import ee.rabi.ali.api.account.orm.model.tables.records.AccountRecord;
import org.jooq.Result;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class AccountRepository extends Repository {

    public AccountRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public List<AccountRecord> findAll() {
        final AtomicReference<Result<AccountRecord>> records = new AtomicReference<>();

        transactionManager.run(context -> {
            records.set(context.selectFrom(Account.ACCOUNT).fetch());
        });

        return records.get();
    }
}
