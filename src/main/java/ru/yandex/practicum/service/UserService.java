package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.userStorage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public Collection<User> allUsers() {
        return userStorage.allUsers();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        user = userStorage.update(user);

        if (Objects.isNull(user)) {
            throw new NotFoundException("This user was not found");
        }

        return user;
    }

    public User findById(Long userId) {
        final User user = userStorage.findById(userId);

        if (Objects.isNull(user)) {
            throw new NotFoundException(String.format("This user id%d was not found", userId));
        }

        return user;
    }

    public void addFriend(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        if (Objects.isNull(user) || Objects.isNull(friend)) {
            throw new NotFoundException(String.format("This user id%d or friend id%d was not found", userId, friendId));
        }

        userStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        if (Objects.isNull(user) || Objects.isNull(friend)) {
            throw new NotFoundException(String.format("This user id%d or friend id%d was not found", userId, friendId));
        }

        userStorage.deleteFriend(userId, friendId);
    }

    public List<User> findAllFriends(Long userId) {
        final User user = userStorage.findById(userId);

        if (Objects.isNull(user)) {
            throw new NotFoundException(String.format("This user id%d was not found", userId));
        }

        return userStorage.findAllFriends(userId);
    }

    public List<User> findCommonFriends(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        if (Objects.isNull(user) || Objects.isNull(friend)) {
            throw new NotFoundException(String.format("This user id%d or friend id%d was not found", userId, friendId));
        }

        return userStorage.findCommonFriends(userId, friendId);
    }
}
