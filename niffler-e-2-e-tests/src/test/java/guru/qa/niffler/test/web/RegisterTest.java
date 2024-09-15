package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.Test;

public class RegisterTest {
    private static final Config CFG = Config.getInstance();

    @Test
    void shouldRegisterNewUser() {
       final String username = "user" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .createNewAccount()
                .setUsername(username)
                .setPassword("111")
                .setPasswordSubmit("111")
                .submitRegistration()
                .verifySuccessRegistrationMessageIsVisible();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .setUsername("duck")
                .setPassword("111")
                .setPasswordSubmit("111")
                .submitRegistration()
                .verifyUsernameAlreadyExistsErrorIsVisible();
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(CFG.registerUrl(), RegisterPage.class)
                .setUsername("user")
                .setPassword("111")
                .setPasswordSubmit("123")
                .submitRegistration()
                .verifyPasswordsShouldBeEqualErrorIsVisible();
    }

}
