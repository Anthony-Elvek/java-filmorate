package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    private Long id;
    @Email(message = "Invalid user email address has been entered")
    private String email;
    @NotBlank
    @NotNull(message = "Invalid user login has been entered")
    private String login;
    private String name;
    @Past(message = "Invalid user birthday has been entered")
    private LocalDate birthday;
    @JsonInclude
    private final Set<Long> friends = new HashSet<>();
}
