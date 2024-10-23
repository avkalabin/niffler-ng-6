package guru.qa.niffler.data.extractor;

import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CurrencyValues;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendEntityRowExtractor implements ResultSetExtractor<SpendEntity> {
    @Override
    public SpendEntity extractData(@Nonnull ResultSet rs) throws SQLException, DataAccessException {
        SpendEntity result = new SpendEntity();
        result.setId(rs.getObject("id", UUID.class));
        result.setUsername(rs.getString("username"));
        result.setSpendDate(rs.getDate("spend_date"));
        result.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
        result.setAmount(rs.getDouble("amount"));
        result.setDescription(rs.getString("description"));

        CategoryEntity category = new CategoryEntity(rs.getObject("category_id", UUID.class));
        category.setUsername(rs.getString("username"));
        category.setName(rs.getString("category_name"));
        category.setArchived(rs.getBoolean("category_archived"));

        result.setCategory(category);
        return result;
    }
}