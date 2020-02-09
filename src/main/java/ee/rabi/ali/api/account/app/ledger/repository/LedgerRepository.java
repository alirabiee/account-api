package ee.rabi.ali.api.account.app.ledger.repository;

import ee.rabi.ali.api.account.app.common.repository.Repository;
import ee.rabi.ali.api.account.orm.TransactionManager;
import ee.rabi.ali.api.account.orm.model.tables.Ledger;
import io.micronaut.validation.Validated;
import org.jooq.impl.DSL;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Optional;

@Singleton
@Validated
public class LedgerRepository extends Repository {

    public LedgerRepository(final TransactionManager transactionManager) {
        super(transactionManager);
    }

    public BigDecimal getBalance(String accountId) {
        return Optional
                .ofNullable(txMgr
                        .getContext()
                        .select(DSL.sum(Ledger.LEDGER.AMOUNT))
                        .from(Ledger.LEDGER)
                        .where(Ledger.LEDGER.ACCOUNT_ID.eq(accountId))
                        .fetch()
                        .get(0)
                        .component1())
                .orElse(BigDecimal.ZERO);
    }
}
