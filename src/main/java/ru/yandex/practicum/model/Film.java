package ru.yandex.practicum.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.yandex.practicum.annotations.ReleaseDateConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Film {

    private Long id;
    @NotBlank(message = "Invalid film name has been entered")
    private String name;
    @Size(max = 200, message = "Invalid description film has been entered")
    private String description;
    @ReleaseDateConstraint(message = "Invalid release date has been entered")
    private LocalDate releaseDate;
    @Positive(message = "Invalid duration film has been entered")
    private Integer duration;
    @JsonInclude
    private final Set<Long> likes = new HashSet<>();
}
