package com.honeywell.atm.core.model.dto;

import java.io.Serializable;

public interface RequestDto extends Serializable {
    default void setCardNumber(String cardNumber) {}
    default void setPin(String pin) {}
}
