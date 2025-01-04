package com.honeywell.atm.server.service;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.dto.impl.*;
import com.honeywell.atm.core.model.exception.AtmException;
import com.honeywell.atm.server.domain.CardEntity;
import com.honeywell.atm.server.domain.CardRepository;
import com.honeywell.atm.server.service.handler.RequestHandler;
import com.honeywell.atm.server.service.impl.CardOpsServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RequestHandlerTest {
    private static final String VALID_CARD_NO = "1234123412341234";
    private static final String VALID_PIN = "1234";
    private static final Double BALANCE = 100.0;
    private final CardEntity validCard = new CardEntity("Test", VALID_CARD_NO, VALID_PIN, BALANCE);

    @Mock
    private CardRepository cardRepository;
    @Autowired
    private RequestHandler requestHandler;

    public void init() {
        CardOpsServiceImpl cardOpsService = new CardOpsServiceImpl(cardRepository);
        requestHandler.setCardOpsService(cardOpsService);
    }

    @Test
    public void testHandleCardDetailsRequestDto_invalidCardNumber() {
        CardDetailsRequestDto requestDto = new CardDetailsRequestDto("123", VALID_PIN);
        Assertions.assertThrows(ConstraintViolationException.class, () -> requestHandler.handle(requestDto));
    }

    @Test
    public void testHandleCardDetailsRequestDto_invalidPin() {
        CardDetailsRequestDto requestDto = new CardDetailsRequestDto(VALID_CARD_NO, "pin");
        Assertions.assertThrows(ConstraintViolationException.class, () -> requestHandler.handle(requestDto));
    }

    @Test
    public void testHandleCardDetailsRequestDto_ok() {
        init();
        when(cardRepository.findByCardNumber(VALID_CARD_NO)).thenReturn(validCard);
        CardDetailsRequestDto requestDto = new CardDetailsRequestDto(VALID_CARD_NO, VALID_PIN);
        Assertions.assertDoesNotThrow(() -> requestHandler.handle(requestDto));
    }

    @Test
    public void handleDepositAmountRequestDto_invalidAmount() {
        DepositAmountRequestDto requestDto = new DepositAmountRequestDto(-5.0);
        requestDto.setCardNumber(VALID_CARD_NO);
        requestDto.setPin(VALID_PIN);
        Assertions.assertThrows(ConstraintViolationException.class, () -> requestHandler.handle(requestDto));
    }

    @Test
    public void handleDepositAmountRequestDto_ok() {
        init();
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        Mockito.when(cardRepository.save(validCard)).thenReturn(validCard);

        DepositAmountRequestDto requestDto = new DepositAmountRequestDto(20.0);
        requestDto.setCardNumber(VALID_CARD_NO);
        requestDto.setPin(VALID_PIN);

        CardDetails cardDetails = requestHandler.handle(requestDto);
        Assertions.assertNotNull(cardDetails);
    }

    @Test
    public void handleWithdrawAmountRequestDto_invalidAmount() {
        WithdrawAmountRequestDto requestDto = new WithdrawAmountRequestDto(0.0);
        requestDto.setCardNumber(VALID_CARD_NO);
        requestDto.setPin(VALID_PIN);
        Assertions.assertThrows(ConstraintViolationException.class, () -> requestHandler.handle(requestDto));
    }

    @Test
    public void handleWithdrawAmountRequestDto_ok() {
        init();
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        Mockito.when(cardRepository.save(validCard)).thenReturn(validCard);

        WithdrawAmountRequestDto requestDto = new WithdrawAmountRequestDto(10.0);
        requestDto.setCardNumber(VALID_CARD_NO);
        requestDto.setPin(VALID_PIN);

        CardDetails cardDetails = requestHandler.handle(requestDto);
        Assertions.assertNotNull(cardDetails);
    }

    @Test
    public void handlePinChangeRequestDto_invalidNewPin() {
        PinChangeRequestDto requestDto = new PinChangeRequestDto(VALID_PIN, "12345");
        requestDto.setCardNumber(VALID_CARD_NO);
        requestDto.setPin(VALID_PIN);
        Assertions.assertThrows(ConstraintViolationException.class, () -> requestHandler.handle(requestDto));
    }

    @Test
    public void handlePinChangeRequestDto_ok() {
        init();
        when(cardRepository.findByCardNumberAndPin(VALID_CARD_NO, VALID_PIN)).thenReturn(validCard);
        Mockito.when(cardRepository.save(validCard)).thenReturn(validCard);

        PinChangeRequestDto requestDto = new PinChangeRequestDto(VALID_PIN, "4321");
        requestDto.setCardNumber(VALID_CARD_NO);

        CardDetails cardDetails = requestHandler.handle(requestDto);
        Assertions.assertNotNull(cardDetails);
    }

    @Test
    public void handleInvalidRequest() {
        Assertions.assertThrows(AtmException.class, () -> requestHandler.handle(new LogoutRequestDto()));
    }
}
