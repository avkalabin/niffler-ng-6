package guru.qa.niffler.test.web;

import guru.qa.niffler.model.rest.UserJson;
import guru.qa.niffler.service.UsersClient;
import guru.qa.niffler.service.impl.UsersApiClient;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.utils.RandomDataUtils.randomUsername;

public class JdbcTest {

    UsersClient usersClient = new UsersApiClient();

    @Test
    void testUsersApiClient() {
        UserJson user = usersClient.createUser(randomUsername(), "12345");
        System.out.println("Созданный user " + user.username());

        usersClient.addIncomeInvitation(user, 1);
        usersClient.addOutcomeInvitation(user, 2);
        usersClient.addFriend(user, 3);
    }

}
