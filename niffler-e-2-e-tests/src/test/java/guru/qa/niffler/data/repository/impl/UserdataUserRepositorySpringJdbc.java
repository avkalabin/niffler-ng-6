package guru.qa.niffler.data.repository.impl;

import guru.qa.niffler.data.dao.UserdataUserDao;
import guru.qa.niffler.data.dao.impl.UserdataUserDaoJdbc;
import guru.qa.niffler.data.entity.userdata.FriendshipStatus;
import guru.qa.niffler.data.entity.userdata.UserEntity;
import guru.qa.niffler.data.repository.UserdataUserRepository;

import java.util.Optional;
import java.util.UUID;

public class UserdataUserRepositorySpringJdbc implements UserdataUserRepository {

    UserdataUserDao userdataUserDao = new UserdataUserDaoJdbc();

    @Override
    public UserEntity create(UserEntity user) {
        return userdataUserDao.create(user);
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return userdataUserDao.findById(id);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userdataUserDao.findByUsername(username);
    }

    @Override
    public UserEntity update(UserEntity user) {
        return userdataUserDao.update(user);
    }

    @Override
    public void sendInvitation(UserEntity requester, UserEntity addressee) {
        requester.addFriends(FriendshipStatus.PENDING, addressee);
        userdataUserDao.update(requester);
    }

    @Override
    public void addFriend(UserEntity requester, UserEntity addressee) {
        requester.addFriends(FriendshipStatus.ACCEPTED, addressee);
        addressee.addFriends(FriendshipStatus.ACCEPTED, requester);
        userdataUserDao.update(requester);
        userdataUserDao.update(addressee);
    }

    @Override
    public void remove(UserEntity user) {
        userdataUserDao.deleteUser(user);
    }
}