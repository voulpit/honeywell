package com.honeywell.atm.console.model;

import com.honeywell.atm.core.model.CardDetails;

public class AtmContext {
    private boolean logged;
    private CardDetails cardDetails;

    public String getCardNumber() {
        assert (cardDetails != null);
        return cardDetails.getCardNumber();
    }

    public String getOwnerName() {
        assert (cardDetails != null);
        return cardDetails.getOwner();
    }

    public Double getBalance() {
        assert (cardDetails != null);
        return cardDetails.getBalance();
    }

    public boolean isLogged() {
        return logged;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
        this.logged = this.logged || cardDetails != null;
    }

    public void logout() {
        this.logged = false;
        this.cardDetails = null;
    }
}
