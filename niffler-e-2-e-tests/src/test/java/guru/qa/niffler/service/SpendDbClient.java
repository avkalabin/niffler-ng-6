package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.CategoryDao;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.tpl.JdbcTransactionTemplate;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.Optional;


public class SpendDbClient {

    private final CategoryDao categoryDao = new CategoryDaoJdbc();
    private final SpendDao spendDao = new SpendDaoJdbc();

    private static final Config CFG = Config.getInstance();

    private final JdbcTransactionTemplate jdbcTxTemplate = new JdbcTransactionTemplate(
            CFG.spendJdbcUrl()
    );

    public SpendJson createSpend(SpendJson spend) {
        return jdbcTxTemplate.execute(() -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    CategoryEntity categoryEntity;
                    Optional<CategoryEntity> ce = categoryDao.findByUsernameAndCategoryName(spendEntity.getCategory().getUsername(), spendEntity.getCategory().getName());
                    categoryEntity = ce.orElseGet(() -> categoryDao.create(spendEntity.getCategory()));
                    spendEntity.setCategory(categoryEntity);
                    return SpendJson.fromEntity(
                            spendDao.create(spendEntity)
                    );
                }
        );
    }

    public CategoryJson addCategory(CategoryJson category) {
        return jdbcTxTemplate.execute(() -> {
                    return CategoryJson.fromEntity(
                            categoryDao.create(CategoryEntity.fromJson(category))
                    );
                }
        );
    }

    public void deleteCategory(CategoryJson category) {
        jdbcTxTemplate.execute(() -> {
                    categoryDao.deleteCategory(CategoryEntity.fromJson(category));
                    return null;
                }
        );
    }
}