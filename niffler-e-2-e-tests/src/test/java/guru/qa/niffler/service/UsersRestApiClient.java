package guru.qa.niffler.service;

import guru.qa.niffler.api.UsersApiClient;
import guru.qa.niffler.model.UserJson;

import java.util.ArrayList;
import java.util.List;

import static guru.qa.niffler.utils.RandomDataUtils.randomUsername;

public class UsersRestApiClient implements UsersClient {

    UsersApiClient usersApiClient = new UsersApiClient();

    @Override
    public UserJson createUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<UserJson> addIncomeInvitation(UserJson targetUser, int count) {
        List<UserJson> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final String username = randomUsername();
            users.add(
                    usersApiClient.sendInvitation(
                    createUser(username, "12345").username(),
                    targetUser.username())
            );
        }

        return users;
    }

    @Override
    public List<UserJson> addOutcomeInvitation(UserJson targetUser, int count) {
        List<UserJson> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final String username = randomUsername();
            users.add(
                    usersApiClient.sendInvitation(
                            targetUser.username(),
                            createUser(username, "12345").username())
            );

        }
        return users;
    }

    @Override
    public List<UserJson> addFriend(UserJson targetUser, int count) {
        List<UserJson> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final String username = randomUsername();
            UserJson user = createUser(username, "12345");
            usersApiClient.sendInvitation(
                    user.username(),
                    targetUser.username());
            usersApiClient.acceptInvitation(
                    targetUser.username(), user.username());
            users.add(user);

        }
        return users;
    }
}
