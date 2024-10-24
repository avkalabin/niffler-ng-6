package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static guru.qa.niffler.utils.RandomDataUtils.randomName;

@WebTest
public class ProfileTest {
    private static final Config CFG = Config.getInstance();

    @User(
            username = "duck",
            categories = @Category(
                    archived = true
            )
    )
    @Test
    void archivedCategoryShouldPresentInCategoriesList(CategoryJson category) throws InterruptedException {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345")
                .openProfilePage()
                .categoryShouldNotBeVisible(category.name())
                .showArchivedCategories()
                .categoryShouldBeVisible(category.name());
    }

    @User(
            username = "duck",
            categories = @Category(
                    archived = false
            )
    )
    @Test
    void activeCategoryShouldPresentInCategoriesList(@Nonnull CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345")
                .openProfilePage()
                .categoryShouldBeVisible(category.name());
    }

    @User
    @Test
    void shouldUpdateProfileName(@Nonnull UserJson user) {
        String name = randomName();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login(user.username(), user.testData().password())
                .getHeader()
                .toProfilePage()
                .setName(name)
                .clickSaveButton()
                .shouldBeVisibleSaveChangesSuccessMessage();
    }
}
