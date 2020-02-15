package ee.rabi.ali.api.account.orm.transaction.impl;

import ee.rabi.ali.api.account.orm.config.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestTransactionManagerImpl extends TransactionManagerImpl {

    public TestTransactionManagerImpl(final DataSource dataSource) {
        super(dataSource);
    }

    public void truncateAll() {
        final List<String> truncatedTableNames = new ArrayList<>();
        getContext().transaction(configuration -> {
            final DSLContext context = DSL.using(configuration);
            context.execute("SET REFERENTIAL_INTEGRITY FALSE");
            final Result<Record> showTables = context.resultQuery("SHOW TABLES").fetch();
            for (Record table : showTables) {
                final String tableName = table.get(0, String.class);
                if (!tableName.toLowerCase().contains("flyway")) {
                    getContext().execute("DELETE FROM " + tableName);
                    truncatedTableNames.add(tableName);
                }
            }
            context.execute("SET REFERENTIAL_INTEGRITY TRUE");
        });
        log.info("Truncated tables: {}", truncatedTableNames);
    }
}
