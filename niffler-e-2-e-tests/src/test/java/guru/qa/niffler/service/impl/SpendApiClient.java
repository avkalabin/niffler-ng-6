package guru.qa.niffler.service.impl;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.api.core.RestClient;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.rest.CategoryJson;
import guru.qa.niffler.model.rest.SpendJson;
import guru.qa.niffler.service.SpendClient;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendApiClient implements SpendClient {

    private static final Config CFG = Config.getInstance();
    private final SpendApi spendApi = new RestClient.EmptyClient(CFG.spendUrl()).create(SpendApi.class);

    @Override
    @Nonnull
    @Step("Send POST(\"internal/spends/add\") to niffler-spend")
    public SpendJson createSpend(@Nonnull SpendJson spend) {
        final Response<SpendJson> response;
        try {
            response = spendApi.addSpend(spend)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(201, response.code());
        return requireNonNull(response.body());
    }

    @NotNull
    @Override
    @Step("Send GET(\"internal/spends/all\") to niffler-spend")
    public List<SpendJson> getSpends(@Nonnull String username) {
        final Response<List<SpendJson>> response;
        try {
            response = spendApi.getSpends(username, null, null, null)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body() != null ? response.body() : List.of();
    }

    @Override
    @Nonnull
    @Step("Send POST(\"internal/categories/add\") to niffler-spend")
    public CategoryJson createCategory(@Nonnull CategoryJson category) {
        final Response<CategoryJson> response;
        try {
            response = spendApi.addCategory(category)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        CategoryJson result = requireNonNull(response.body());

        return category.archived()
                ? updateCategory(
                new CategoryJson(
                        result.id(),
                        result.name(),
                        result.username(),
                        true
                )
        ) : result;
    }

    @NotNull
    @Override
    @Step("Send GET(\"internal/categories/all\") to niffler-spend")
    public List<CategoryJson> getCategories(@Nonnull String username) {
        final Response<List<CategoryJson>> response;
        try {
            response = spendApi.getCategories(username, false)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body() != null ? response.body() : List.of();
    }

    @Override
    @Nonnull
    @Step("Send PATCH(\"internal/categories/update\") to niffler-spend")
    public CategoryJson updateCategory(@Nonnull CategoryJson category) {
        final Response<CategoryJson> response;
        try {
            response = spendApi.updateCategory(category)
                    .execute();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        assertEquals(200, response.code());
        return response.body();
    }

    @Override
    public void removeCategory(@Nonnull CategoryJson category) {
        throw new UnsupportedOperationException("Can`t remove category using API");
    }
}
