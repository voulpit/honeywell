package com.honeywell.atm.core.model.dto.impl;

import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.validation.CardNumberValidation;
import com.honeywell.atm.core.validation.PinValidation;
import jakarta.validation.constraints.Positive;

import java.io.Serial;

public class DepositAmountRequestDto implements RequestDto {
    @Serial
    private static final long serialVersionUID = 6529610098267757690L;

    @CardNumberValidation
    private String cardNumber;

    @PinValidation
    private String pin;

    @Positive(message = "The amount should be a positive number!")
    private Double amount;

    public DepositAmountRequestDto(Double amount) {
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
