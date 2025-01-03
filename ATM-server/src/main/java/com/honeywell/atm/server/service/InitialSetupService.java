package com.honeywell.atm.server.service;

import com.honeywell.atm.server.domain.CardEntity;
import com.honeywell.atm.server.domain.CardRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialSetupService {
    private final CardRepository cardRepository;

    public InitialSetupService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // creation of 2 bank accounts

        String geoCardNumber = "2938473737474845";
        CardEntity cardEntity1 = cardRepository.findByCardNumber(geoCardNumber);
        if (cardEntity1 == null) {
            cardEntity1 = new CardEntity("Georgiana T", geoCardNumber, "1234", 250.0);
            cardRepository.save(cardEntity1);
        }

        String petriCardNumber = "9299111130128890";
        CardEntity cardEntity2 = cardRepository.findByCardNumber(petriCardNumber);
        if (cardEntity2 == null) {
            cardEntity2 = new CardEntity("Petri T", petriCardNumber, "2025", 30.5);
            cardRepository.save(cardEntity2);
        }
    }
}
