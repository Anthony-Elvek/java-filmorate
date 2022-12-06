package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id;

    @GetMapping
    public Collection<User> allUsers(){
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user){
        if(user.getName() == null){
            user.setName(user.getLogin());
        }
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        if (users.containsKey(user.getId())){
            if(user.getName() == null){
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Unknown user");
        }
        return user;
    }

}
