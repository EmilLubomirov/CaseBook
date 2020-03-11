package app.service;

import app.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService
{
    void register(UserServiceModel user);

    UserServiceModel findUserByUAndP(String username, String password);

    List<UserServiceModel> findAll();

    UserServiceModel getById(String id);

    void addFriend(UserServiceModel user);

    void unfriend(String userId, String friendId);
}
