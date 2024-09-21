package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement statistics =  $("#stat");
  private final SelenideElement spendings =  $("#spendings");
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
    spendings.shouldBe(visible);
  }

  public ProfilePage openProfilePage() {
    menuButton.click();
    menuButtons.first().click();
    return new ProfilePage();
  }

  public PeoplePage openFriends() {
    menuButton.click();
    menuButtons.get(1).click();
    return new PeoplePage();
  }

  public PeoplePage openAllPeople() {
    menuButton.click();
    menuButtons.get(2).click();
    return new PeoplePage();
  }
}
