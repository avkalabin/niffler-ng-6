package guru.qa.niffler.test.web;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.service.SpendClient;
import guru.qa.niffler.service.SpendDbClient;
import guru.qa.niffler.service.UsersClient;
import guru.qa.niffler.service.UsersDbClient;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static guru.qa.niffler.utils.RandomDataUtils.randomCategoryName;
import static guru.qa.niffler.utils.RandomDataUtils.randomUsername;

public class JdbcTest {

    SpendClient spendClient = new SpendDbClient();
    UsersClient usersClient = new UsersDbClient();

    @Test
    void testSpendDbClient() {
        CategoryJson category = spendClient.createCategory(
                new CategoryJson(
                        null,
                        randomCategoryName(),
                        "duck",
                        false
                )
        );
        System.out.println("Созданная категория: " + category);

        SpendJson spend = spendClient.createSpend(
                new SpendJson(
                        null,
                        new Date(),
                        category,
                        CurrencyValues.RUB,
                        1500.0,
                        "spendNameDescription",
                        "duck"
                )
        );
        System.out.println("Созданный спенд: " + spend);

        spendClient.removeSpend(spend);
        spendClient.removeCategory(category);
    }

    @Test
    void testUsersDbClient() {
        UserJson user = usersClient.createUser(randomUsername(), "12345");
        System.out.println("Созданный user " + user.username());

        usersClient.addIncomeInvitation(user, 1);
        usersClient.addOutcomeInvitation(user, 2);
        usersClient.addFriend(user, 3);
    }

}
