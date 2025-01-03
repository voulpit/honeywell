package com.honeywell.atm.server.service.impl;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.exception.AtmException;
import com.honeywell.atm.core.model.exception.ExceptionMessage;
import com.honeywell.atm.server.domain.CardEntity;
import com.honeywell.atm.server.domain.CardRepository;
import com.honeywell.atm.server.service.CardOpsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CardOpsServiceImpl implements CardOpsService {
    private final CardRepository cardRepository;

    public CardOpsServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardDetails getCardDetails(String cardNumber, String pin) {
        CardEntity cardEntity = cardRepository.findByCardNumber(cardNumber);
        if (cardEntity == null) {
            throw new AtmException(ExceptionMessage.INVALID_CARD_NUMBER.getMessage());
        }
        if (!pin.equals(cardEntity.getPin())) {
            throw new AtmException(ExceptionMessage.INCORRECT_PIN.getMessage());
        }
        if (!cardEntity.getActiveInd()) {
            throw new AtmException(ExceptionMessage.INACTIVE_CARD.getMessage());
        }
        return entityToModel(cardEntity);
    }

    @Override
    public CardDetails withdrawAmount(String cardNumber, String pin, Double amount) {
        CardEntity cardEntity = getCardInformation(cardNumber, pin);
        if (amount > cardEntity.getBalance()) {
            throw new AtmException(ExceptionMessage.INSUFFICIENT_FUNDS.getMessage());
        }
        cardEntity.setBalance(cardEntity.getBalance() - amount);
        cardEntity = cardRepository.save(cardEntity);
        return entityToModel(cardEntity);
    }

    @Override
    public CardDetails depositAmount(String cardNumber, String pin, Double amount) {
        CardEntity cardEntity = getCardInformation(cardNumber, pin);
        Double newBalance = cardEntity.getBalance() + amount;
        cardEntity.setBalance(newBalance);
        cardEntity = cardRepository.save(cardEntity);
        return entityToModel(cardEntity);
    }

    @Override
    public CardDetails changePin(String cardNumber, String oldPin, String newPin) {
        CardEntity cardEntity = getCardInformation(cardNumber, oldPin);
        cardEntity.setPin(newPin);
        cardEntity = cardRepository.save(cardEntity);
        return entityToModel(cardEntity);
    }

    private CardEntity getCardInformation(String cardNumber, String pin) {
        CardEntity cardEntity = cardRepository.findByCardNumberAndPin(cardNumber, pin);
        if (cardEntity == null || !cardEntity.getActiveInd()) {
            throw new AtmException(ExceptionMessage.NOT_AUTHORIZED.getMessage());
        }
        return cardEntity;
    }

    private CardDetails entityToModel(CardEntity cardEntity) {
        CardDetails cardDetails = new CardDetails();
        BeanUtils.copyProperties(cardEntity, cardDetails);
        return cardDetails;
    }
}
