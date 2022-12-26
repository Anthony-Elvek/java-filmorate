package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.storage.userStorage.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        return userStorage.update(user);
    }

    public User findById(Long userId) {
        return userStorage.findById(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> findAllFriends(Long userId) {
        final User user = userStorage.findById(userId);

        return user.getFriends().stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public List<User> findCommonFriends(Long userId, Long friendId) {
        final User user = userStorage.findById(userId);
        final User friend = userStorage.findById(friendId);

        return user.getFriends().stream()
                .filter(id -> friend.getFriends().contains(id))
                .map(this::findById)
                .collect(Collectors.toList());
    }
}
