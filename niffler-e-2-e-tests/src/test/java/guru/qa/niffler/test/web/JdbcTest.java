package guru.qa.niffler.test.web;

import guru.qa.niffler.model.*;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UserDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JdbcTest {

    @Test
    void txTest() {
        SpendDbClient spendDbClient = new SpendDbClient();

        SpendJson spend = spendDbClient.createSpend(
                new SpendJson(
                        null,
                        new Date(),
                        new CategoryJson(
                                null,
                                "cat-name-tx-3",
                                "duck",
                                false
                        ),
                        CurrencyValues.RUB,
                        1000.0,
                        "spend-name-tx-3",
                        "duck"
                )
        );

        System.out.println(spend);
    }

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

    @Test
    void friendshipTest() {
        UserDbClient usersDbClient = new UserDbClient();

        UserJson user = usersDbClient.createUser(
                new UserJson(
                        null,
                        "user",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );

        UserJson friend = usersDbClient.createUser(
                new UserJson(
                        null,
                        "friend",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );

        UserJson income = usersDbClient.createUser(
                new UserJson(
                        null,
                        "income",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );

        UserJson outcome = usersDbClient.createUser(
                new UserJson(
                        null,
                        "outcome",
                        null,
                        null,
                        null,
                        CurrencyValues.RUB,
                        null,
                        null,
                        null
                )
        );

        usersDbClient.addInvitation(income, user);
        usersDbClient.addInvitation(user, outcome);
        usersDbClient.addFriends(user, friend);
    }
}
