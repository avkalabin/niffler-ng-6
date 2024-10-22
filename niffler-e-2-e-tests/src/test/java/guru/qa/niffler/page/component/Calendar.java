package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class Calendar {

    private final SelenideElement self = $("input[name='date']");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Step("Выбрать дату в календаре: {date}")
    public void selectDateInCalendar(@Nonnull LocalDate date) {
        String formattedDate = date.format(formatter);
        self.clear();
        self.setValue(formattedDate);
    }
}