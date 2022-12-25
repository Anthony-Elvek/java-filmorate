package ru.yandex.practicum.storage.userStorage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        }
        return user;
    }

    @Override
    public User findById(Long userId){
        return users.get(userId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        if(!users.get(userId).getFriends().contains(friendId)){
            users.get(userId).getFriends().add(friendId);
            users.get(friendId).getFriends().add(userId);
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        if(!users.get(userId).getFriends().contains(friendId)){
            users.get(userId).getFriends().remove(friendId);
            users.get(friendId).getFriends().remove(userId);
        }
    }

    @Override
    public List<User> findAllFriends(Long userId) {
        return users.get(userId).getFriends().stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findCommonFriends(Long userId, Long friendId) {
        return users.get(userId).getFriends().stream()
                .filter(id -> users.get(friendId).getFriends().contains(id))
                .map(this::findById)
                .collect(Collectors.toList());
    }
}
