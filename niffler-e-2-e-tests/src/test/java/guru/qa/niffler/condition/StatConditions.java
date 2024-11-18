package guru.qa.niffler.condition;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import com.codeborne.selenide.WebElementsCondition;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.CheckResult.accepted;
import static com.codeborne.selenide.CheckResult.rejected;

@ParametersAreNonnullByDefault
public class StatConditions {
    @Nonnull
    public static WebElementCondition color(Color expectedColor) {
        return new WebElementCondition("color " + expectedColor.rgb) {
            @NotNull
            @Override
            public CheckResult check(Driver driver, WebElement element) {
                final String rgba = element.getCssValue("background-color");
                return new CheckResult(
                        expectedColor.rgb.equals(rgba),
                        rgba
                );
            }
        };
    }

    @Nonnull
    public static WebElementsCondition color(@Nonnull Color... expectedColors) {
        return new WebElementsCondition() {
            private final String expectedRgba = Arrays.stream(expectedColors).map(c -> c.rgb).toList().toString();

            @NotNull
            @Override
            public CheckResult check(Driver driver, List<WebElement> elements) {
                if (ArrayUtils.isEmpty(expectedColors)) {
                    throw new IllegalArgumentException("No expected colors given");
                }
                if (expectedColors.length != elements.size()) {
                    final String message = String.format("List size mismatch (expected: %s, actual: %s)", expectedColors.length, elements.size());
                    return rejected(message, elements);
                }
                boolean passed = true;
                final List<String> actualRgbaList = new ArrayList<>();
                for (int i = 0; i < elements.size(); i++) {
                    final WebElement elementToCheck = elements.get(i);
                    final Color colorToCheck = expectedColors[i];
                    final String rgba = elementToCheck.getCssValue("background-color");
                    actualRgbaList.add(rgba);
                    if (passed) {
                        passed = colorToCheck.rgb.equals(rgba);
                    }
                }
                if (!passed) {
                    final String actualRgba = actualRgbaList.toString();
                    final String message = String.format(
                            "List colors mismatch (expected: %s, actual: %s)", expectedRgba, actualRgba
                    );
                    return rejected(message, actualRgba);
                }
                return accepted();
            }

            @Override
            public String toString() {
                return expectedRgba;
            }
        };
    }

    @Nonnull
    public static WebElementsCondition statBubbles(Bubble... expectedBubbles) {
        return new WebElementsCondition() {
            private final String expectedValues = Arrays.stream(expectedBubbles).map(b -> b.color().rgb + " " + b.text()).toList().toString();

            @NotNull
            @Override
            public CheckResult check(Driver driver, List<WebElement> elements) {
                if (ArrayUtils.isEmpty(expectedBubbles)) {
                    throw new IllegalArgumentException("No expected bubbles given");
                }

                if (expectedBubbles.length != elements.size()) {
                    final String message = String.format("List size mismatch (expected: %s, actual: %s)", expectedBubbles.length, elements.size());
                    return rejected(message, elements);
                }

                boolean passed = true;
                final List<String> actualValuesList = new ArrayList<>();
                for (int i = 0; i < elements.size(); i++) {
                    final WebElement elementToCheck = elements.get(i);
                    final Color colorToCheck = expectedBubbles[i].color();
                    final String textToCheck = expectedBubbles[i].text();
                    final String actualRgba = elementToCheck.getCssValue("background-color");
                    final String actualText = elementToCheck.getText();
                    actualValuesList.add(actualRgba + " " + actualText);
                    if (passed) {
                        passed = colorToCheck.rgb.equals(actualRgba) && textToCheck.equals(actualText);
                    }
                }
                if (!passed) {
                    final String actualValues = actualValuesList.toString();
                    final String message = String.format(
                            "List bubbles mismatch (expected: %s, actual: %s)", expectedValues, actualValues
                    );
                    return rejected(message, actualValues);
                }
                return accepted();
            }

            @NotNull
            @Override
            public String toString() {
                return expectedValues;
            }
        };
    }

    @Nonnull
    public static WebElementsCondition statBubblesInAnyOrder(Bubble... expectedBubbles) {
        return new WebElementsCondition() {
            private final List<String> expectedValues = Arrays.stream(expectedBubbles).map(b -> b.color().rgb + " " + b.text()).toList();

            @NotNull
            @Override
            public CheckResult check(Driver driver, List<WebElement> elements) {
                if (ArrayUtils.isEmpty(expectedBubbles)) {
                    throw new IllegalArgumentException("No expected bubbles given");
                }

                if (expectedBubbles.length != elements.size()) {
                    final String message = String.format("List size mismatch (expected: %s, actual: %s)", expectedBubbles.length, elements.size());
                    return rejected(message, elements);
                }

                final List<String> actualValuesList = new ArrayList<>();
                for (WebElement e : elements) {
                    final String actualRgba = e.getCssValue("background-color");
                    final String actualText = e.getText();
                    actualValuesList.add(actualRgba + " " + actualText);
                }

                if (actualValuesList.containsAll(expectedValues) && expectedValues.containsAll(actualValuesList)) {
                    return accepted();
                } else {
                    final String actualValues = actualValuesList.toString();
                    final String message = String.format(
                            "List bubbles mismatch (expected: %s, actual: %s)", expectedValues, actualValues
                    );
                    return rejected(message, actualValues);
                }
            }

            @NotNull
            @Override
            public String toString() {
                return expectedValues.toString();
            }
        };
    }

    @Nonnull
    public static WebElementsCondition statBubblesContains(Bubble... expectedBubbles) {
        return new WebElementsCondition() {
            private final List<String> expectedValues = Arrays.stream(expectedBubbles).map(b -> b.color().rgb + " " + b.text()).toList();

            @NotNull
            @Override
            public CheckResult check(Driver driver, List<WebElement> elements) {
                if (ArrayUtils.isEmpty(expectedBubbles)) {
                    throw new IllegalArgumentException("No expected bubbles given");
                }

                final List<String> actualValuesList = new ArrayList<>();
                for (WebElement e : elements) {
                    final String actualRgba = e.getCssValue("background-color");
                    final String actualText = e.getText();
                    actualValuesList.add(actualRgba + " " + actualText);
                }

                if (actualValuesList.containsAll(expectedValues)) {
                    return accepted();
                } else {
                    final String actualValues = actualValuesList.toString();
                    final String message = String.format(
                            "List bubbles mismatch (expected: %s, actual: %s)", expectedValues, actualValues
                    );
                    return rejected(message, actualValues);
                }
            }

            @NotNull
            @Override
            public String toString() {
                return expectedValues.toString();
            }
        };
    }
}