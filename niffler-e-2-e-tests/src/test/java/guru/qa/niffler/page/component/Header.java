package guru.qa.niffler.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Header {
    private final SelenideElement self = $("#root header");
    private final SelenideElement logOutButton = $(byTagAndText("button", "Log out"));
    private final ElementsCollection menu = $$("li[role='menuitem']");

    @Step("Перейти на страницу друзей")
    public PeoplePage toFriendsPage() {
        self.$("button").click();
        menu.findBy(text("Friends")).click();
        return new PeoplePage();

    }

    @Step("Перейти на страницу всех людей")
    public PeoplePage toAllPeoplesPage() {
        self.$("button").click();
        menu.findBy(text("All People")).click();
        return new PeoplePage();
    }

    @Step("Перейти на страницу профиля")
    public ProfilePage toProfilePage() {
        self.$("button").click();
        menu.findBy(text("Profile")).click();
        return new ProfilePage();
    }

    @Step("Выйти из аккаунта")
    public LoginPage signOut() {
        self.$("button").click();
        menu.findBy(text("Sign out")).click();
        logOutButton.click();
        return new LoginPage();
    }

    @Step("Добавить новую трату")
    public EditSpendingPage addSpendingPage() {
        self.$(byText("New spending")).click();
        return new EditSpendingPage();
    }

    @Step("Перейти на главную страницу")
    public MainPage toMainPage() {
        self.$("h1").click();
        return new MainPage();
    }
}

