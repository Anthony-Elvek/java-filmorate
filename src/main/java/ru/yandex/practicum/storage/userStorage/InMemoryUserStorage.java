package ru.yandex.practicum.storage.userStorage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private AtomicLong id = new AtomicLong();

    private Long getId() {
        return id.incrementAndGet();
    }

    @Override
    public Collection<User> allUsers() {
        return users.values();
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("This user is not found");
        }
        return user;
    }

    @Override
    public User findById(Long userId) {
        User user;

        if (users.containsKey(userId)) {
            user = users.get(userId);
        } else {
            throw new NotFoundException(String.format("This user id%d is not found", userId));
        }
        return user;
    }
}
