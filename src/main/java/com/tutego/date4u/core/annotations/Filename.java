package com.tutego.date4u.core.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilenameValidator.class)
//@Constraint(validatedBy = {})
public @interface Filename {
    String message() default "must be a valid language display name." +
            " found: ${validatedValue}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class FilenameValidator implements ConstraintValidator<Filename, String> {
    private static final String FILENAME_PATTERN = "[\\w_-]{1,200}";
    //private static final java.util.regex.Pattern FILENAME_PATTERN = java.util.regex.Pattern.compile("[\\w_-]{1,200}");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        //return name != null && pattern.matcher(name).matches();
        return name != null && name.matches(FILENAME_PATTERN);
    }
}