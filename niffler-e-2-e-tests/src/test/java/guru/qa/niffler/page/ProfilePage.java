package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    private final SelenideElement showArchivedToggle = $(".MuiSwitch-switchBase");

    public ProfilePage categoryShouldBeVisible(String category) {
        $(byTagAndText("span", category)).shouldBe(visible);
        return this;
    }

    public ProfilePage categoryShouldNotBeVisible(String category) {
        $(byTagAndText("span", category)).shouldNotBe(visible);
        return this;
    }


    public ProfilePage showArchivedCategories() {
        showArchivedToggle.click();
        return this;
    }
}
