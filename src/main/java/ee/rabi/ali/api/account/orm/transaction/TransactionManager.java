package ee.rabi.ali.api.account.orm.transaction;

import org.jooq.DSLContext;

public interface TransactionManager {
    DSLContext getContext();
}
