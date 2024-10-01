package guru.qa.niffler.service;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.dao.impl.CategoryDaoJdbc;
import guru.qa.niffler.data.dao.impl.SpendDaoJdbc;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;

import java.util.Optional;

import static guru.qa.niffler.data.Databases.transaction;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class SpendDbClient {
    private static final Config CFG = Config.getInstance();


    public SpendJson createSpend(SpendJson spend) {
        return transaction(connection -> {
                    SpendEntity spendEntity = SpendEntity.fromJson(spend);
                    CategoryEntity categoryEntity;
                    Optional<CategoryEntity> ce = new CategoryDaoJdbc(connection).findByUsernameAndCategoryName(spendEntity.getCategory().getUsername(), spendEntity.getCategory().getName());
                    categoryEntity = ce.orElseGet(() -> new CategoryDaoJdbc(connection).create(spendEntity.getCategory()));
                    spendEntity.setCategory(categoryEntity);
                    return SpendJson.fromEntity(
                            new SpendDaoJdbc(connection).create(spendEntity)
                    );
                },
                CFG.spendJdbcUrl(),
                TRANSACTION_READ_COMMITTED

        );
    }

    public CategoryJson addCategory(CategoryJson category) {
        return transaction(connection -> {
                    return CategoryJson.fromEntity(
                            new CategoryDaoJdbc(connection).create(CategoryEntity.fromJson(category))
                    );
                },
                CFG.spendJdbcUrl(),
                TRANSACTION_READ_COMMITTED
        );
    }

    public void deleteCategory(CategoryJson category) {
        transaction(connection -> {
                    new CategoryDaoJdbc(connection).deleteCategory(CategoryEntity.fromJson(category));
                },
                CFG.spendJdbcUrl(),
                TRANSACTION_READ_COMMITTED
        );
    }
}