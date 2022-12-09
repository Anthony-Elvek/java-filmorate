package ru.yandex.practicum.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    private Integer id;
    @Email(message = "Invalid user email address has been entered")
    private String email;
    @NotBlank
    @NotNull(message = "Invalid user login has been entered")
    private String login;
    private String name;
    @Past(message = "Invalid user birthday has been entered")
    private LocalDate birthday;
}
