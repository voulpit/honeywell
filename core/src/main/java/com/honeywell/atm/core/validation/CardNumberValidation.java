package com.honeywell.atm.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardNumberValidator.class)
public @interface CardNumberValidation {
    public String message() default "Invalid card number: the format should be 16 digits"; // error message

    public Class<?>[] groups() default {}; // group of constraints

    public Class<? extends Payload>[] payload() default {}; // additional info
}
