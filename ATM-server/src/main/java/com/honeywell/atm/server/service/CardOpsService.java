package com.honeywell.atm.server.service;

import com.honeywell.atm.core.model.CardDetails;

public interface CardOpsService {
    CardDetails getCardDetails(String cardNumber, String pin);
    CardDetails withdrawAmount(String cardNumber, String pin, Double amount);
    CardDetails depositAmount(String cardNumber, String pin, Double amount);
    CardDetails changePin(String cardNumber, String oldPin, String newPin);
}
