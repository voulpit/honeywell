package com.honeywell.atm.core.model.dto.impl;

import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.validation.CardNumberValidation;
import com.honeywell.atm.core.validation.PinValidation;

import java.io.Serial;

public class CardDetailsRequestDto implements RequestDto {
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;

    @CardNumberValidation
    private String cardNumber;

    @PinValidation
    private String pin;

    public CardDetailsRequestDto(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
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
}
