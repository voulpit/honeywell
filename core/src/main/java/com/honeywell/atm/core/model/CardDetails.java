package com.honeywell.atm.core.model;

import java.io.Serial;
import java.io.Serializable;

public class CardDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 6529611098267757690L;

    private String owner;
    private String cardNumber;
    private Double balance;

    public CardDetails() {
    }

    public CardDetails(String owner, String cardNumber, Double balance) {
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
