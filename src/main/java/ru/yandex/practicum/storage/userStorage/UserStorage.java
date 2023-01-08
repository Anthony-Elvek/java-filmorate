package ru.yandex.practicum.storage.userStorage;

import ru.yandex.practicum.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> allUsers();

    User create(User user);

    User update(User user);

    User findById(Long userId);
}
