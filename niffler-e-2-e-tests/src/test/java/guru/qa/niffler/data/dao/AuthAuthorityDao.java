package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.auth.AuthorityEntity;

import java.util.List;
import java.util.UUID;

public interface AuthAuthorityDao {

    void create(AuthorityEntity... authority);

    List<AuthorityEntity> findAllByUserId(UUID id);
    List<AuthorityEntity> findAll();
    void delete(AuthorityEntity authority);

}
