package ee.rabi.ali.api.account.orm.config;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Property;
import org.h2.jdbcx.JdbcDataSource;

@Context
public class DataSource {

    private final JdbcDataSource dataSource;

    public DataSource(@Property(name = "jdbc.url") final String url,
                      @Property(name = "jdbc.username") final String userName,
                      @Property(name = "jdbc.password") final String password) {
        dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
    }

    public JdbcDataSource get() {
        return dataSource;
    }
}
