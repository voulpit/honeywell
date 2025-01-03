package com.honeywell.atm.core.model.dto;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.exception.AtmException;

import java.io.Serial;
import java.io.Serializable;

public class ResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1529685098267757690L;

    private CardDetails cardDetails;
    private AtmException exception;

    public ResponseDto() {
    }

    public ResponseDto(AtmException exception) {
        this.exception = exception;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    public AtmException getException() {
        return exception;
    }

    public void setException(AtmException exception) {
        this.exception = exception;
    }
}
