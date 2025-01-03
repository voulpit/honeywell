package com.honeywell.atm.server.service.handler;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.impl.CardDetailsRequestDto;
import com.honeywell.atm.core.model.dto.impl.DepositAmountRequestDto;
import com.honeywell.atm.core.model.dto.impl.PinChangeRequestDto;
import com.honeywell.atm.core.model.dto.impl.WithdrawAmountRequestDto;
import com.honeywell.atm.core.model.exception.AtmException;
import com.honeywell.atm.server.service.CardOpsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class RequestHandler {
    private final CardOpsService cardOpsService;

    public RequestHandler(CardOpsService cardOpsService) {
        this.cardOpsService = cardOpsService;
    }

    public CardDetails handle(@Valid RequestDto request) {
        if (request instanceof CardDetailsRequestDto) {
            return handle((CardDetailsRequestDto) request);
        }
        if (request instanceof DepositAmountRequestDto) {
            return handle((DepositAmountRequestDto) request);
        }
        if (request instanceof WithdrawAmountRequestDto) {
            return handle((WithdrawAmountRequestDto) request);
        }
        if (request instanceof PinChangeRequestDto) {
            return handle((PinChangeRequestDto) request);
        }
        throw new AtmException("Invalid request");
    }

    private CardDetails handle(@Valid CardDetailsRequestDto request) {
        return cardOpsService.getCardDetails(request.getCardNumber(), request.getPin());
    }

    private CardDetails handle(@Valid DepositAmountRequestDto request) {
        return cardOpsService.depositAmount(request.getCardNumber(), request.getPin(), request.getAmount());
    }

    private CardDetails handle(@Valid WithdrawAmountRequestDto request) {
        return cardOpsService.withdrawAmount(request.getCardNumber(), request.getPin(), request.getAmount());
    }

    private CardDetails handle(@Valid PinChangeRequestDto request) {
        return cardOpsService.changePin(request.getCardNumber(), request.getPin(), request.getNewPin());
    }
}
