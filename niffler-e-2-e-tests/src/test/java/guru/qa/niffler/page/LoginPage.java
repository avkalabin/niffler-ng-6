package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement createNewAccountButton = $(byTagAndText("a", "Create new account"));
  private final SelenideElement badCredentialsError  = $(byTagAndText("p", "Неверные учетные данные пользователя"));

  public MainPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new MainPage();
  }

  public LoginPage loginWithBadCredentials(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
   return this;
  }

  public RegisterPage createNewAccount() {
    createNewAccountButton.click();
    return new RegisterPage();
  }

  public LoginPage verifyBadCredentialsErrorIsVisible() {
    badCredentialsError.shouldBe(visible);
    return this;
  }

  public void verifyCurrentPageIsLogin() {
    createNewAccountButton.shouldBe(visible);
    assertTrue(WebDriverRunner.url().contains("/login?error"));
  }

}
