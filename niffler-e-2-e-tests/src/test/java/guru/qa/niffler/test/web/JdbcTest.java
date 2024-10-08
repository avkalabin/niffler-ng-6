package guru.qa.niffler.test.web;

import guru.qa.niffler.model.*;
import guru.qa.niffler.service.UserDbClient;
import org.junit.jupiter.api.Test;

public class JdbcTest {

    @Test
    void xaDeleteTest() {
        UserDbClient userDbClient = new UserDbClient();
        userDbClient.deleteUser(
                new UserJson(
                        null,
                        "valentin-5",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createUserSpringJdbcWithTxTest() { // не создает пользователя в обеих базах
        UserDbClient usersDbClient = new UserDbClient();
        UserJson user = usersDbClient.createUserSpringJdbcWithTx(
                new UserJson(
                        null,
                        "valentin-1",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }

    @Test
    void createUserSpringJdbcWithoutTxTest() { // создает пользователя в auth
        UserDbClient usersDbClient = new UserDbClient();
        UserJson user = usersDbClient.createUserSpringJdbcWithoutTx(
                new UserJson(
                        null,
                        "valentin-2",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }

    @Test
    void createUserJdbcWithTxTest() { // не создает пользователя в обеих базах
        UserDbClient usersDbClient = new UserDbClient();
        UserJson user = usersDbClient.createUserJdbcWithTx(
                new UserJson(
                        null,
                        "valentin-3",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }

    @Test
    void createUserJdbcWithoutTxTest() {// создает пользователя в auth
        UserDbClient usersDbClient = new UserDbClient();
        UserJson user = usersDbClient.createUserJdbcWithoutTx(
                new UserJson(
                        null,
                        "valentin-4",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );
        System.out.println(user);
    }
}
