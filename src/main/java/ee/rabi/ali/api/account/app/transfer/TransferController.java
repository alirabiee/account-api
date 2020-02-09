package ee.rabi.ali.api.account.app.transfer;

import ee.rabi.ali.api.account.orm.DatabaseConnectionProvider;
import ee.rabi.ali.api.account.orm.model.tables.Author;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

@Controller("/transfer")
public class TransferController {

    private final DatabaseConnectionProvider databaseConnectionProvider;

    public TransferController(final DatabaseConnectionProvider databaseConnectionProvider) {
        this.databaseConnectionProvider = databaseConnectionProvider;
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() throws SQLException {
        final StringBuilder stringBuilder = new StringBuilder();
        try (Connection conn = databaseConnectionProvider.get()) {
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(Author.AUTHOR).fetch();
            for (Record record : result) {
                stringBuilder.append(record);
            }
        }
        return stringBuilder.toString();
    }
}
