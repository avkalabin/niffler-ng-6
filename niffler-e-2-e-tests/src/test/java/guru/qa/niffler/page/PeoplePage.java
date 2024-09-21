package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage {
    private final SelenideElement friendsTable = $("#friends");
    private final SelenideElement tabPanelFriends = $("#simple-tabpanel-friends");
    private final SelenideElement friendTable = $("#requests");
    private final SelenideElement allPeopleTable = $("#all");

    public void verifyFriendsTableContainsUser(String name) {
        friendsTable.$$("tr").filter(text(name)).shouldHave(sizeGreaterThan(0));
    }

    public void verifyFriendsTableShouldBeEmpty() {
        tabPanelFriends.shouldHave(text("There are no users yet"));
    }

    public void verifyFriendTableContainsIncome(String name) {
        friendTable.$$("tr").filter(text(name)).shouldHave(sizeGreaterThan(0));
    }

    public void verifyAllPeopleTableContainsOutcome(String name) {
        allPeopleTable.$$("tr").findBy(text(name)).should(text("Waiting..."));
    }

}
