package ru.yandex.practicum.storage.userStorage;

import ru.yandex.practicum.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    Collection<User> allUsers();

    User create(User user);

    User update(User user);

    User findById(Long userId);

    void addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);

    List<User> findAllFriends(Long id);

    List<User> findCommonFriends(Long id, Long friendId);
}
