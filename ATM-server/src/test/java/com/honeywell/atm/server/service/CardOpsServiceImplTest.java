package com.honeywell.atm.server.service;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.exception.AtmException;
import com.honeywell.atm.server.domain.CardEntity;
import com.honeywell.atm.server.domain.CardRepository;
import com.honeywell.atm.server.service.impl.CardOpsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardOpsServiceImplTest {
    private static final String VALID_CARD_NO = "1234123412341234";
    private static final String VALID_PIN = "1234";
    private static final Double BALANCE = 100.0;
    private final CardEntity validCard = new CardEntity("Test", VALID_CARD_NO, VALID_PIN, BALANCE);

    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private CardOpsServiceImpl cardOpsService;

    @Test
    public void testGetCardDetails_invalidCardNumber() {
        String invalidCard = "invalidcard";
        when(cardRepository.findByCardNumber(invalidCard)).thenReturn(null);
        Assertions.assertThrows(AtmException.class, () -> cardOpsService.getCardDetails(invalidCard, VALID_PIN));
    }

    @Test
    public void testGetCardDetails_incorrectPin() {
        when(cardRepository.findByCardNumber(VALID_CARD_NO)).thenReturn(validCard);
        Assertions.assertThrows(AtmException.class, () -> cardOpsService.getCardDetails(VALID_CARD_NO, "12345"));
    }

    @Test
    public void testGetCardDetails_inactiveUser() {
        when(cardRepository.findByCardNumber(VALID_CARD_NO)).thenReturn(validCard);
        validCard.setActiveInd(false);
        Assertions.assertThrows(AtmException.class, () -> cardOpsService.getCardDetails(VALID_CARD_NO, VALID_PIN));
        validCard.setActiveInd(true);
    }

    @Test
    public void testGetCardDetails_ok() {
        when(cardRepository.findByCardNumber(VALID_CARD_NO)).thenReturn(validCard);
        CardDetails cardDetails = cardOpsService.getCardDetails(VALID_CARD_NO, VALID_PIN);
        Assertions.assertNotNull(cardDetails);
        Assertions.assertEquals(validCard.getCardNumber(), cardDetails.getCardNumber());
    }

    @Test
    public void testWithdrawAmount_insufficientFunds() {
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        Assertions.assertThrows(AtmException.class, () -> cardOpsService.withdrawAmount(VALID_CARD_NO, VALID_PIN, BALANCE + 1));
    }

    @Test
    public void testWithdrawAmount_ok() {
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        when(cardRepository.save(validCard)).thenReturn(validCard);

        CardDetails cardDetails = cardOpsService.withdrawAmount(VALID_CARD_NO, VALID_PIN, BALANCE);
        Assertions.assertNotNull(cardDetails);
        Assertions.assertEquals(0.0, cardDetails.getBalance());
    }

    @Test
    public void testDepositAmount_ok() {
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        when(cardRepository.save(validCard)).thenReturn(validCard);

        CardDetails cardDetails = cardOpsService.depositAmount(VALID_CARD_NO, VALID_PIN, 10.0);
        Assertions.assertNotNull(cardDetails);
        Assertions.assertEquals(BALANCE + 10.0, cardDetails.getBalance());
    }

    @Test
    public void testChangePin_ok() {
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        when(cardRepository.save(validCard)).thenReturn(validCard);

        CardDetails cardDetails = cardOpsService.changePin(VALID_CARD_NO, VALID_PIN, "0123");
        Assertions.assertNotNull(cardDetails);
        Assertions.assertEquals("0123", validCard.getPin());
    }
}
