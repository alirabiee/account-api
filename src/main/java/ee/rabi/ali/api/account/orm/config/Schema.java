package ee.rabi.ali.api.account.orm.config;

import ee.rabi.ali.api.account.Application;
import io.micronaut.context.annotation.Context;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.codegen.GenerationTool;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.flywaydb.core.Flyway.configure;

@Context
public class Schema {

    private static final String SCHEMA = "PUBLIC";
    private static final String MIGRATIONS_PATH = "classpath:db/migration";
    private static final String JOOQ_CONFIG_PATH = "/db/jooq.xml";

    public Schema(final DataSource dataSource) {
        applyMigrations(dataSource);
    }

    public void generateModels() throws Exception {
        GenerationTool.generate(new String(Files.readAllBytes(Paths.get(Application.class.getResource(JOOQ_CONFIG_PATH).toURI()))));
    }

    private void applyMigrations(final DataSource dataSource) {
        final JdbcDataSource jdbcDataSource = dataSource.get();
        configure()
                .dataSource(jdbcDataSource.getURL(), jdbcDataSource.getUser(), jdbcDataSource.getPassword())
                .schemas(SCHEMA)
                .locations(MIGRATIONS_PATH)
                .load()
                .migrate();
    }
}
