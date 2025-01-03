package com.honeywell.atm.server.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    CardEntity findByCardNumber(String cardNumber);
    CardEntity findByCardNumberAndPin(String cardNumber, String pin);
}
