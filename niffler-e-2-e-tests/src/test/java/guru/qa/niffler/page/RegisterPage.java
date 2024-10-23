package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement signUpButton = $("button[type='submit']");
    private final SelenideElement successRegistrationMessage =  $(byTagAndText("p", "Congratulations! You've registered!"));
    private final SelenideElement errorMessage =  $(".form__error");

    @Step("Ввести имя пользователя: {username}")
    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Ввести пароль: {password}")
    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Ввести подтверждение пароля: {password}")
    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку регистрации")
    public RegisterPage submitRegistration() {
        signUpButton.click();
        return this;
    }

    @Step("Проверить успешное сообщение об успешной регистрации")
    public void verifySuccessRegistrationMessageIsVisible() {
        successRegistrationMessage.shouldBe(visible);
    }

    @Step("Проверить сообщение об ошибке: {message}")
    public void verifyErrorMessage(String message) {
        errorMessage.shouldHave(text(message));
    }

}
