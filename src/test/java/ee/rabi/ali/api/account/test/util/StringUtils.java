package ee.rabi.ali.api.account.test.util;

import java.util.UUID;

public final class StringUtils {

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    private StringUtils() {
    }
}
