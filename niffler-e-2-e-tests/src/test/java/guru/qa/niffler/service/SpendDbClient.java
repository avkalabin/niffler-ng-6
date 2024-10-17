package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.impl.SpendRepositoryHibernate;
import guru.qa.niffler.data.tpl.XaTransactionTemplate;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.Optional;

public class SpendDbClient implements SpendClient {

    private final SpendRepository spendRepository = new SpendRepositoryHibernate();

    private static final Config CFG = Config.getInstance();

    private final XaTransactionTemplate xaTransactionTemplate = new XaTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    @Override
    public SpendJson createSpend(SpendJson spend) {
        return xaTransactionTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    CategoryEntity categoryEntity;
                    Optional<CategoryEntity> ce = spendRepository.findCategoryByUsernameAndCategoryName(spendEntity.getCategory().getUsername(), spendEntity.getCategory().getName());
                    categoryEntity = ce.orElseGet(() -> spendRepository.createCategory(spendEntity.getCategory()));
                    spendEntity.setCategory(categoryEntity);
                    return SpendJson.fromEntity(
                            spendRepository.create(spendEntity)
                    );
                }
        );
    }

    @Override
    public CategoryJson createCategory(CategoryJson category) {
        return xaTransactionTemplate.execute(() -> {
                    return CategoryJson.fromEntity(
                            spendRepository.createCategory(CategoryEntity.fromJson(category))
                    );
                }
        );
    }

    @Override
    public void removeCategory(CategoryJson category) {
        xaTransactionTemplate.execute(() -> {
                    spendRepository.removeCategory((CategoryEntity.fromJson(category)));
                    return null;
                }
        );
    }

    @Override
    public void removeSpend(SpendJson spend) {
        xaTransactionTemplate.execute(() -> {
                    spendRepository.remove(SpendEntity.fromJson(spend));
                    return null;
                }
        );
    }
}