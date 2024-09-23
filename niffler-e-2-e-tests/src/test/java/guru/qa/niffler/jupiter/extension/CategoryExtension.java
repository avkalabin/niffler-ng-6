package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import static guru.qa.niffler.utils.RandomDataUtils.randomCategoryName;


public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(userAnno -> {
                    if (userAnno.categories().length > 0) {
                        Category categoryAnno = userAnno.categories()[0];
                        String categoryName = categoryAnno.title().isEmpty() ? randomCategoryName() :  categoryAnno.title();
                        CategoryJson category = new CategoryJson(
                                null,
                                categoryName,
                                userAnno.username(),
                                false
                        );
                        CategoryJson createdCategory = spendApiClient.addCategory(category);
                        if (categoryAnno.archived()) {
                            CategoryJson archivedCategory = new CategoryJson(
                                    createdCategory.id(),
                                    createdCategory.name(),
                                    createdCategory.username(),
                                    true
                            );
                            createdCategory = spendApiClient.updateCategory(archivedCategory);
                        }
                        context.getStore(NAMESPACE).put(
                                context.getUniqueId(),
                                createdCategory
                        );
                    }
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (category != null && !category.archived()) {
            category = new CategoryJson(
                    category.id(),
                    category.name(),
                    category.username(),
                    true
            );
            spendApiClient.updateCategory(category);
        }
    }
}
