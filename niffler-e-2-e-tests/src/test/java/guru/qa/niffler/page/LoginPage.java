package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

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

  @Step("Ввести логин {username} и пароль {password}")
  public MainPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new MainPage();
  }

  @Step("Ввести некорректные логин {username} и пароль {password}")
  public LoginPage loginWithBadCredentials(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
   return this;
  }

  @Step("Создать новый аккаунт")
  public RegisterPage createNewAccount() {
    createNewAccountButton.click();
    return new RegisterPage();
  }

  @Step("Проверить, что видима ошибка с некорректными учетными данными")
  public LoginPage verifyBadCredentialsErrorIsVisible() {
    badCredentialsError.shouldBe(visible);
    return this;
  }

  @Step("Проверить, что текущая страница - логин")
  public void verifyCurrentPageIsLogin() {
    createNewAccountButton.shouldBe(visible);
    assertTrue(WebDriverRunner.url().contains("/login?error"));
  }

}
