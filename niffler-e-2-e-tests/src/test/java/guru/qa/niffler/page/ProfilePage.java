package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    public static String url = Config.getInstance().frontUrl() + "profile";

    private final SelenideElement showArchivedToggle = $(".MuiSwitch-switchBase");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement saveButton = $("button[type='submit']");
    private final SelenideElement successSaveChangesMessage = $(".MuiAlert-message");

    @Step("Проверить отображение категории: {category}")
    public void categoryShouldBeVisible(String category) {
        $(byTagAndText("span", category)).shouldBe(visible);
    }

    @Step("Проверить отсутствие категории: {category}")
    public ProfilePage categoryShouldNotBeVisible(String category) {
        $(byTagAndText("span", category)).shouldNotBe(visible);
        return this;
    }

    @Step("Показать архивные категории")
    public ProfilePage showArchivedCategories() {
        showArchivedToggle.click();
        return this;
    }

    @Step("Ввести имя: {name}")
    public ProfilePage setName(String name) {
        nameInput.setValue(name);
        return this;
    }

    @Step("Нажать кнопку сохранить изменения")
    public ProfilePage clickSaveButton() {
        saveButton.click();
        return this;
    }

    @Step("Проверить успешное сообщение об обновлении профиля")
    public void shouldBeVisibleSaveChangesSuccessMessage() {
        successSaveChangesMessage.shouldHave(text("Profile successfully updated"));
    }
}
