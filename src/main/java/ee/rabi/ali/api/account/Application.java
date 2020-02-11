package ee.rabi.ali.api.account;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Accounts API",
                version = "0.1",
                description = "This API supports accounts with fiat currencies, hard over-draft limits, and real-time transfers",
                contact = @Contact(url = "https://github.com/alirabiee", name = "Ali Rabiee")
        )
)
public final class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    private Application() {
    }
}