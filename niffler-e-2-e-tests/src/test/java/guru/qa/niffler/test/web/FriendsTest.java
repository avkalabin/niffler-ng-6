package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

@WebTest
public class FriendsTest {
    private static final Config CFG = Config.getInstance();


    @User(
            friends = 1
    )
    @Test
    void friendShouldBePresentInFriendsTable(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriends()
                .verifyFriendsTableContainsUser(user.testData().friends());
    }

    @User
    @Test
    void friendsTableShouldBeEmptyForNewUser(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriends()
                .verifyFriendsTableShouldBeEmpty();
    }

    @User(
            income = 1
    )
    @Test
    void incomeInvitationBePresentInFriendsTable(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriends()
                .verifyFriendTableContainsIncome(user.testData().income());
    }

    @User(
            outcome = 1
    )
    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openAllPeople()
                .verifyAllPeopleTableContainsOutcome(user.testData().outcome());
    }

    @User(
            income = 1
    )
    @Test
    void shouldAcceptFriendRequest(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriends()
                .acceptFriend(user.username())
                .verifyFriendAdded(user.testData().income().getFirst());
    }

    @User(
            income = 1
    )
    @Test
    void shouldDeclineFriendRequest(@Nonnull UserJson user) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .openFriends()
                .declineFriend(user.username())
                .verifyFriendsTableShouldBeEmpty();
    }
}
