package ru.yandex.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.annotations.ReleaseDateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Month;

@Slf4j
public class ReleaseDateValidator implements ConstraintValidator<ReleaseDateConstraint, LocalDate> {

    private static final LocalDate START_MOVIE_ERA = LocalDate.of(1895, Month.DECEMBER, 28);

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext){
        log.debug("Validation entered date : {}", date);
        return date.isAfter(START_MOVIE_ERA);
    }
}
