package com.honeywell.atm.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PinValidator implements ConstraintValidator<PinValidation,String> {
    public static final String FORMAT = "^[0-9]{4}";

    @Override
    public boolean isValid(String pin, ConstraintValidatorContext constraintValidatorContext) {
        return pin != null && pin.matches(FORMAT);
    }
}
