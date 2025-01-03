package com.honeywell.atm.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CardEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String owner;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private Double balance;

    private Boolean activeInd;

    public CardEntity() {
    }

    public CardEntity(String owner, String cardNumber, String pin, Double balance) {
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.activeInd = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getActiveInd() {
        return activeInd;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }
}
