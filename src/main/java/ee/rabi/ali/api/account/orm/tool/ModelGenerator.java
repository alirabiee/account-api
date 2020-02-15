package ee.rabi.ali.api.account.orm.tool;

import ee.rabi.ali.api.account.orm.config.DataSource;
import ee.rabi.ali.api.account.orm.config.Schema;

public final class ModelGenerator {

    public static void main(String[] args) throws Exception {
        new Schema(new DataSource("jdbc:h2:~/default", "sa", "")).generateModels();
    }

    private ModelGenerator() {
    }
}
