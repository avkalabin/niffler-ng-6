package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.Category;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

public class ProfileTest {
    private static final Config CFG = Config.getInstance();


    @Category(
            title = "test category",
            username = "duck",
            archived = true
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


    @Category(
            username = "duck",
            archived = false
    )
    @Test
    void activeCategoryShouldPresentInCategoriesList(CategoryJson category) {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345")
                .openProfilePage()
                .categoryShouldBeVisible(category.name());
    }
}
