package app.service.impl;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;
import app.repository.UserRepository;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserServiceModel user)
    {
        userRepository.registerUser(modelMapper.map(user, User.class));
    }

    @Override
    public UserServiceModel findUserByUAndP(String username, String password)
    {
        return modelMapper.map(userRepository.findByUAndP(username, password), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAll()
    {
        return userRepository.getAllUsers()
                .stream()
                .map(u -> modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel getById(String id)
    {
        return modelMapper.map(userRepository.findById(id), UserServiceModel.class);
    }

    @Override
    public void addFriend(UserServiceModel user)
    {
        this.userRepository.update(this.modelMapper.map(user, User.class));
    }

    @Override
    public void unfriend(String userId, String friendId)
    {
        User loggedIn = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);

        loggedIn.getFriends().remove(friend);
        friend.getFriends().remove(loggedIn);

        userRepository.update(loggedIn);
        userRepository.update(friend);
    }
}
