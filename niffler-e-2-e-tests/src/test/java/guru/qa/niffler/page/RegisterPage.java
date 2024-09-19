package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

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

    public RegisterPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.setValue(password);
        return this;
    }

    public RegisterPage submitRegistration() {
        signUpButton.click();
        return this;
    }

    public void verifySuccessRegistrationMessageIsVisible() {
        successRegistrationMessage.shouldBe(visible);
    }

    public void verifyErrorMessage(String message) {
        errorMessage.shouldHave(text(message));
    }

}
