package guru.qa.niffler.service;

import guru.qa.niffler.model.rest.CategoryJson;
import guru.qa.niffler.model.rest.SpendJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface SpendClient {

    @Nonnull
    SpendJson createSpend(SpendJson spend);

    @Nonnull
    List<SpendJson> getSpends (@Nonnull String username);

    @Nonnull
    CategoryJson createCategory(CategoryJson category);

    @Nonnull
    List<CategoryJson> getCategories (@Nonnull String username);

    @Nonnull
    CategoryJson updateCategory(CategoryJson category);

    void removeCategory(CategoryJson category);


}