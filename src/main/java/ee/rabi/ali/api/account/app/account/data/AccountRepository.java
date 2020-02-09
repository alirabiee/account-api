package ee.rabi.ali.api.account.app.account.data;

import ee.rabi.ali.api.account.app.common.data.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import org.jooq.impl.DSL;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class AccountRepository extends Repository {

    public AccountRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public BigDecimal getBalance(String accountId) {
        final AtomicReference<BigDecimal> balance = new AtomicReference<>();
        transactionManager.run(context -> {
            final Optional<BigDecimal> result = Optional.ofNullable(context
                    .select(DSL.sum(Ledger.LEDGER.AMOUNT))
                    .from(Ledger.LEDGER)
                    .where(Ledger.LEDGER.ACCOUNT_ID.eq(accountId))
                    .fetch()
                    .get(0)
                    .component1());
            balance.set(result.orElse(BigDecimal.ZERO));
        });
        return balance.get();
    }
}
