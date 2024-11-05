package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.Header;
import guru.qa.niffler.page.component.SpendingTable;
import guru.qa.niffler.page.component.StatComponent;
import guru.qa.niffler.utils.ScreenDiffResult;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MainPage extends BasePage<MainPage> {

    public static final String URL = CFG.frontUrl() + "main";

    private final SelenideElement statImg = $("canvas[role='img']");
    private final SelenideElement statCell = $("#legend-container");

    protected final Header header = new Header();
    protected final SpendingTable spendingTable = new SpendingTable();
    protected final StatComponent statComponent = new StatComponent();

    @Nonnull
    public Header getHeader() {
        return header;
    }

    @Nonnull
    public SpendingTable getSpendingTable() {
        spendingTable.getSelf().scrollIntoView(true);
        return spendingTable;
    }

    @Step("Check that page is loaded")
    @Override
    @Nonnull
    public MainPage checkThatPageLoaded() {
        header.getSelf().should(visible).shouldHave(text("Niffler"));
        statComponent.getSelf().should(visible).shouldHave(text("Statistics"));
        spendingTable.getSelf().should(visible).shouldHave(text("History of Spendings"));
        return this;
    }

    @Step("Check that statistic image matches the expected image")
    public MainPage checkStatImg(BufferedImage expected) throws IOException {
        sleep(2000);
        BufferedImage actual = ImageIO.read(Objects.requireNonNull(statImg.screenshot()));
        assertFalse(new ScreenDiffResult(
                actual,
                expected
        ));
        return this;
    }

    @Step("Check that statistic cell have text {text}")
    public MainPage checkStatCell(String... text) {
        for (String s : text) {
            statCell.shouldHave(exactText(s));
        }
        return this;
    }
}