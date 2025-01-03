package com.honeywell.atm.core.model.dto.impl;

import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.validation.CardNumberValidation;
import com.honeywell.atm.core.validation.PinValidation;

import java.io.Serial;

public class PinChangeRequestDto implements RequestDto {
    @Serial
    private static final long serialVersionUID = 6529685098267757100L;

    @CardNumberValidation
    private String cardNumber;

    @PinValidation
    private String pin;

    @PinValidation
    private String newPin;

    public PinChangeRequestDto(String oldPin, String newPin) {
        this.pin = oldPin;
        this.newPin = newPin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
}
