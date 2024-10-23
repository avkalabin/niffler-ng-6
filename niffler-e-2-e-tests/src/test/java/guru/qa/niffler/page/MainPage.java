package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.Header;
import guru.qa.niffler.page.component.SearchField;
import guru.qa.niffler.page.component.SpendingTable;
import io.qameta.allure.Step;
import lombok.Getter;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement spendings = $("#spendings");
    private final SelenideElement statistics = $("#stat");
    @Getter
    private final Header header = new Header();
    @Getter
    private final SpendingTable spendingTable = new SpendingTable();
    private final SearchField searchField = new SearchField();


    @Step("Редактировать трату: {spendingDescription}")
    public EditSpendingPage editSpending(String spendingDescription) {
        searchField.search(spendingDescription);
        spendingTable.editSpending(spendingDescription);
        return new EditSpendingPage();
    }

    @Step("Проверить, что таблица трат содержит: {spendingDescription}")
    public void checkThatTableContainsSpending(@Nonnull String... spendingDescription) {
        for (String spend : spendingDescription) {
            searchField.search(spend);
            spendingTable.checkTableContains(spend);
            searchField.clearIfNotEmpty();
        }
    }

    @Step("Проверить, что компоненты главной страницы видны")
    public void verifyMainComponentsIsVisible() {
        statistics.shouldBe(visible);
        spendingTable.titleIsVisible();
    }

    @Step("Открыть страницу профиля")
    public ProfilePage openProfilePage() {
        header.toProfilePage();
        return new ProfilePage();
    }

    @Step("Открыть страницу друзей")
    public PeoplePage openFriends() {
        header.toFriendsPage();
        return new PeoplePage();
    }

    @Step("Открыть страницу всех людей")
    public PeoplePage openAllPeople() {
        header.toAllPeoplesPage();
        return new PeoplePage();
    }
}
