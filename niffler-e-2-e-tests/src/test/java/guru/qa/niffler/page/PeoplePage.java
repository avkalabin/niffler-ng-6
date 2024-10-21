package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage {
    private final SelenideElement friendsTable = $("#friends");
    private final SelenideElement tabPanelFriends = $("#simple-tabpanel-friends");
    private final SelenideElement friendTable = $("#requests");
    private final SelenideElement allPeopleTable = $("#all");
    private final SelenideElement searchInput = $("input[placeholder='Search']");

    public void verifyFriendsTableContainsUser(List<String> names) {
        for (String name : names) {
            searchUser(name);
            friendsTable.$$("tr").filter(text(name)).shouldHave(sizeGreaterThan(0));
        }
    }

    public void verifyFriendsTableShouldBeEmpty() {
        tabPanelFriends.shouldHave(text("There are no users yet"));
    }

    public void verifyFriendTableContainsIncome(List<String> names) {
        for (String name : names) {
            searchUser(name);
            friendTable.$$("tr").filter(text(name)).shouldHave(sizeGreaterThan(0));
        }
    }

    public void verifyAllPeopleTableContainsOutcome(List<String> names) {
        for (String name : names) {
            searchUser(name);
            allPeopleTable.$$("tr").findBy(text(name)).should(text("Waiting..."));
        }
    }

    public void searchUser(String name) {
        searchInput.setValue(name).pressEnter();
    }

}
