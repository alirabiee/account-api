package ee.rabi.ali.api.account.orm.config;

import io.micronaut.context.annotation.Context;
import org.h2.jdbcx.JdbcDataSource;

@Context
public class DataSource {

    private final JdbcDataSource dataSource;

    public DataSource() {
        //@todo extract props
        String userName = "sa";
        String password = "";
        String url = "jdbc:h2:~/default";

        dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
    }

    public JdbcDataSource get() {
        return dataSource;
    }
}
