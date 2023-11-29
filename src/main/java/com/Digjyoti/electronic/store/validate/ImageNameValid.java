package com.Digjyoti.electronic.store.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=ImageNameValidation.class)

public @interface ImageNameValid {
//    Error message
    String message() default "Invalid Image Name !";
//    Represent group of Constraints
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
