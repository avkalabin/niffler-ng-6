package guru.qa.niffler.service;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.List;


public class SpendRestApiClient implements SpendClient {

    SpendApiClient spendApiClient = new SpendApiClient();

    public SpendJson createSpend(SpendJson spend) {
        return spendApiClient.createSpend(spend);
    }

    @Override
    public CategoryJson createCategory(CategoryJson category) {
        return spendApiClient.addCategory(category);
    }

    @Override
    public void removeCategory(CategoryJson category) {
        throw new UnsupportedOperationException("Can`t delete category by API");

    }

    @Override
    public void removeSpend(SpendJson spend) {
        spendApiClient.deleteSpends(spend.username(), List.of(spend.id().toString()));
    }
}
