package guru.qa.niffler.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.condition.Color;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.utils.ScreenDiffResult;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static guru.qa.niffler.condition.StatConditions.color;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StatComponent extends BaseComponent<StatComponent> {
    public StatComponent() {
        super($("#stat"));
    }

    private final SelenideElement legend = self.$("#legend-container");
    private final SelenideElement chart = $("canvas[role='img']");

    private final ElementsCollection bubbles =legend.$$("li");

    @Step("Check that stat bubbles contains colors {expectedColors}")
    @Nonnull
    public StatComponent checkBubbles(Color... expectedColors) {
        bubbles.should(color(expectedColors));
        return this;
    }

    @Step("Check that statistic image matches the expected image")
    public StatComponent checkStatImg(BufferedImage expected) throws IOException {
        sleep(3000);
        BufferedImage actual = ImageIO.read(requireNonNull(chart.screenshot()));
        assertFalse(new ScreenDiffResult(
                        actual,
                        expected
                ),
                "Screen comparison failure");
        return this;
    }

    @Step("Check that statistic cell have text {text}")
    public StatComponent checkStatCell(String... text) {
        for (String s : text) {
            legend.shouldHave(exactText(s));
        }
        return this;
    }
}