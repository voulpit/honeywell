package com.honeywell.atm.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumberValidation,String> {
    public static final String FORMAT = "^[0-9]{16}";

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext constraintValidatorContext) {
        return cardNumber != null && cardNumber.matches(FORMAT);
    }
}
