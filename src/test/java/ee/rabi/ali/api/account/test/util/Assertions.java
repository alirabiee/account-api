package ee.rabi.ali.api.account.test.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class Assertions {

    private static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public static void assertUuid(String value) {
        assertNotNull(value);
        assertTrue(value.matches(UUID_REGEX));
    }

    private Assertions() {
    }
}
