package guru.qa.niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.niffler.grpc.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static guru.qa.niffler.grpc.CurrencyValues.*;

public class CurrencyGrpcTest extends BaseGrpcTest {

  @Test
  void allCurrenciesShouldReturned() {
    final CurrencyResponse response = blockingStub.getAllCurrencies(Empty.getDefaultInstance());
    final List<Currency> allCurrenciesList = response.getAllCurrenciesList();
    Assertions.assertEquals(4, allCurrenciesList.size());
  }

  private static Stream<Arguments> calculateRateShouldReturnedCorrectValue() {
    return Stream.of(
            Arguments.of(100.0, EUR, USD, 108.0),
            Arguments.of(100.0, USD, RUB, 6666.67),
            Arguments.of(100.0, RUB, KZT, 714.29),
            Arguments.of(100.0, KZT, EUR, 0.19),
            Arguments.of(100.00, RUB, RUB, 100.00)
    );
  }

  @ParameterizedTest
  @MethodSource
  void calculateRateShouldReturnedCorrectValue(double amount,
                                               CurrencyValues spendCurrency,
                                               CurrencyValues desiredCurrency,
                                               double expectedAmount) {

    CalculateRequest request = CalculateRequest.newBuilder()
            .setAmount(amount)
            .setSpendCurrency(valueOf(spendCurrency.name()))
            .setDesiredCurrency(valueOf(desiredCurrency.name()))
            .build();

    CalculateResponse response = blockingStub.calculateRate(request);
    Assertions.assertEquals(expectedAmount, response.getCalculatedAmount());
  }
}
