package ru.yandex.practicum.model;

import lombok.*;
import ru.yandex.practicum.annotations.ReleaseDateConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Film {

    private Integer id;
    @NotBlank(message = "Invalid film name has been entered")
    private String name;
    @Size(max = 200, message = "Invalid description film has been entered")
    private String description;
    @ReleaseDateConstraint(message = "Invalid release date has been entered")
    private LocalDate releaseDate;
    @Positive(message = "Invalid duration film has been entered")
    private Integer duration;
}
