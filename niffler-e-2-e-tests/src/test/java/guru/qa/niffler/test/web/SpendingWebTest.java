package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.condition.Bubble;
import guru.qa.niffler.condition.Color;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.ScreenShotTest;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.rest.CurrencyValues;
import guru.qa.niffler.model.rest.SpendJson;
import guru.qa.niffler.model.rest.UserJson;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

@WebTest
public class SpendingWebTest {

    @User(
            spendings = @Spending(
                    category = "Обучение",
                    description = "Обучение Advanced 2.0",
                    amount = 79990
            )
    )
    @ApiLogin
    @Test
    void categoryDescriptionShouldBeChangedFromTable() {
        final String newDescription = "Обучение Niffler Next Generation";

        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .editSpending("Обучение Advanced 2.0")
                .setNewSpendingDescription(newDescription)
                .saveSpending();

        new MainPage().getSpendingTable()
                .checkTableContains(newDescription);
    }

    @User
    @ApiLogin
    @Test
    void shouldAddNewSpending(UserJson user) {
        String category = "Friends";
        int amount = 100;
        Date currentDate = new Date();
        String description = RandomDataUtils.randomSentence(3);

        Selenide.open(MainPage.URL, MainPage.class)
                .getHeader()
                .addSpendingPage()
                .setNewSpendingCategory(category)
                .setNewSpendingAmount(amount)
                .setNewSpendingDate(currentDate)
                .setNewSpendingDescription(description)
                .setNewSpendingCurrency(CurrencyValues.EUR)
                .saveSpending()
                .checkAlertMessage("New spending is successfully created");

        new MainPage().getSpendingTable()
                .checkTableContains(description);
    }

    @User
    @ApiLogin
    @Test
    void shouldNotAddSpendingWithEmptyCategory(UserJson user) {
        Selenide.open(MainPage.URL, MainPage.class)
                .getHeader()
                .addSpendingPage()
                .setNewSpendingAmount(100)
                .setNewSpendingDate(new Date())
                .saveSpending()
                .checkFormErrorMessage("Please choose category");
    }

    @User
    @ApiLogin
    @Test
    void shouldNotAddSpendingWithEmptyAmount(UserJson user) {
        Selenide.open(MainPage.URL, MainPage.class)
                .getHeader()
                .addSpendingPage()
                .setNewSpendingCategory("Friends")
                .setNewSpendingDate(new Date())
                .saveSpending()
                .checkFormErrorMessage("Amount has to be not less then 0.01");
    }

    @User(
            spendings = @Spending(
                    category = "Обучение",
                    description = "Обучение Advanced 2.0",
                    amount = 79990
            )
    )
    @ApiLogin
    @ScreenShotTest(value = "img/clear-stat.png",
            rewriteExpected = true)
    void deleteSpendingTest(@Nonnull UserJson user, BufferedImage clearStat) throws IOException {
        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .deleteSpending("Обучение Advanced 2.0")
                .checkTableSize(0);

        new MainPage().getStatComponent().checkStatImg(clearStat)
                .checkStatCell("");
    }

    @User(
            spendings = {
                    @Spending(
                            category = "Обучение",
                            description = "Обучение Advanced 2.0",
                            amount = 79990
                    ),
                    @Spending(
                            category = "Ужин",
                            description = "Ужин в кафе",
                            amount = 5000
                    ),
                    @Spending(
                            category = "Спорт",
                            description = "Абонемент в бассейн",
                            amount = 50000
                    )
            }
    )
    @ApiLogin
    @ScreenShotTest(value = "img/expected-stat.png",
            rewriteExpected = true)
    void checkStatComponentTest(@Nonnull UserJson user, BufferedImage expectedStat) throws IOException {
        Selenide.open(MainPage.URL, MainPage.class)
                .getStatComponent()
                .checkStatImg(expectedStat)
                .checkBubbles(new Bubble(Color.green, "Спорт 50000 ₽"), new Bubble(Color.yellow, "Обучение 79990 ₽"));
    }

    @User(
            spendings = {
                    @Spending(
                            category = "Обучение",
                            description = "Обучение Advanced 2.0",
                            amount = 79990
                    ),
                    @Spending(
                            category = "Ужин",
                            description = "Ужин в кафе",
                            amount = 5000
                    ),
                    @Spending(
                            category = "Спорт",
                            description = "Абонемент в бассейн",
                            amount = 50000
                    )
            }
    )
    @ApiLogin
    @ScreenShotTest(value = "img/expected-stat.png",
            rewriteExpected = true)
    void checkSpendingTableTest(@Nonnull UserJson user, BufferedImage expectedStat) throws IOException {
        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .checkSpends(user.testData().spends().toArray(SpendJson[]::new));
    }
}

