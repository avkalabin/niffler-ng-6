package guru.qa.niffler.test.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.qa.niffler.model.*;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UserDbClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

public class JdbcTest {

    @Disabled
    @Test
    void txTest() {
        SpendDbClient spendDbClient = new SpendDbClient();
        SpendJson spend = spendDbClient.createSpend(
                new SpendJson(
                        null,
                        new Date(),
                        new CategoryJson(
                                null,
                                "cat-name-tx-2",
                                "duck",
                                false
                        ),
                        CurrencyValues.RUB,
                        1000.0,
                        "spend-name-tx",
                        null
                )
        );
        System.out.println(spend);
    }

    @Test
    void xaTxTest() {
        UserDbClient userDbClient = new UserDbClient();
        userDbClient.createUser(
                new UserJson(
                        null,
                        "wtf",
                        null,
                        "12345",
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void xaDeleteTest() {
        UserDbClient userDbClient = new UserDbClient();
        userDbClient.deleteUser(
                new UserJson(
                        null,
                        "wtf",
                        null,
                        "12345",
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
    }
}
