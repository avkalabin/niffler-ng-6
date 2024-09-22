package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

@WebTest
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
        String username = "duck";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .createNewAccount()
                .setUsername(username)
                .setUsername(username)
                .setPassword("111")
                .setPasswordSubmit("111")
                .submitRegistration()
                .verifyErrorMessage("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .createNewAccount()
                .setUsername("user")
                .setPassword("111")
                .setPasswordSubmit("123")
                .submitRegistration()
                .verifyErrorMessage("Passwords should be equal");
    }

}
