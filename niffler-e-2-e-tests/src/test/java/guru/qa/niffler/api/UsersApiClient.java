package guru.qa.niffler.api;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ParametersAreNonnullByDefault
public class UsersApiClient {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.getInstance().userdataUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final UsersApi usersApi = retrofit.create(UsersApi.class);

    @Step("Send GET(\"internal/users/current\") to niffler-userdata")
    public @Nullable UserJson getCurrentUser(String username) {
        final Response<UserJson> response;
        try {
            response = usersApi.currentUser(username)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Step("Send GET(\"internal/users/all\") to niffler-userdata")
    public List<UserJson> getAllUsers(String username, String searchQuery) {
        final Response<List<UserJson>> response;
        try {
            response = usersApi.allUsers(username, searchQuery)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body() != null
                ? response.body()
                : List.of();
    }

    @Step("Send POST(\"internal/users/update\") to niffler-userdata")
    public @Nullable UserJson updateUser(UserJson user) {
        final Response<UserJson> response;
        try {
            response = usersApi.updateUserInfo(user)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Step("Send POST(\"internal/invitations/send\") to niffler-userdata")
    public @Nullable UserJson sendInvitation(String username, String targetUsername) {
        final Response<UserJson> response;
        try {
            response = usersApi.sendInvitation(username, targetUsername)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Step("Send POST(\"internal/invitations/accept\") to niffler-userdata")
    public @Nullable UserJson acceptInvitation(String username, String targetUsername) {
        final Response<UserJson> response;
        try {
            response = usersApi.acceptInvitation(username, targetUsername)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Step("Send POST(\"internal/invitations/decline\") to niffler-userdata")
    public @Nullable UserJson declineInvitation(String username, String targetUsername) {
        final Response<UserJson> response;
        try {
            response = usersApi.declineInvitation(username, targetUsername)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Step("Send GET(\"internal/friends/all\") to niffler-userdata")
    public List<UserJson> getFriends(String username, String searchQuery) {
        final Response<List<UserJson>> response;
        try {
            response = usersApi.friends(username, searchQuery)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body() != null
                ? response.body()
                : List.of();
    }

    @Step("Send DELETE(\"internal/friends/remove\") to niffler-userdata")
    public void removeFriend(String username, String targetUsername) {
        final Response<Void> response;
        try {
            response = usersApi.removeFriend(username, targetUsername)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
    }
}
