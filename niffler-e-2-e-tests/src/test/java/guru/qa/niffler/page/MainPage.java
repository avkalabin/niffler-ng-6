package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement statistics =  $(byTagAndText("h2", "Statistics"));
  private final SelenideElement historyOfSpendings =  $(byTagAndText("h2", "History of Spendings"));
  private final SelenideElement menuButton =  $("button[aria-label='Menu']");
  private final ElementsCollection menuButtons =  $$("li[role='menuitem']");



  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).$$("td").get(5).click();
    return new EditSpendingPage();
  }

  public void checkThatTableContainsSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).should(visible);
  }

  public void verifyMainComponentsIsVisible() {
    statistics.shouldBe(visible);
    historyOfSpendings.shouldBe(visible);
  }

  public ProfilePage openProfilePage() {
    menuButton.click();
    menuButtons.first().click();
    return new ProfilePage();
  }
}
