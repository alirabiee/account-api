package ee.rabi.ali.api.account;

import io.micronaut.runtime.Micronaut;

public final class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    private Application() {
    }
}