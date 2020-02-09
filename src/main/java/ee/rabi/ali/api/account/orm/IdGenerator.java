package ee.rabi.ali.api.account.orm;

import java.util.UUID;

public final class IdGenerator {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

    private IdGenerator() {
    }
}
