package com.honeywell.atm.console.mapper;

import com.honeywell.atm.console.model.cmd.UserCommand;
import com.honeywell.atm.console.model.cmd.VisitorCommand;
import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.impl.CardDetailsRequestDto;
import com.honeywell.atm.core.model.dto.impl.DepositAmountRequestDto;
import com.honeywell.atm.core.model.dto.impl.PinChangeRequestDto;
import com.honeywell.atm.core.model.dto.impl.WithdrawAmountRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandToRequestMapperTest {
    private static final String CARD_NUMBER = "1234123412341234";
    private static final String PIN = "1234";
    private static final String NEW_PIN = "4321";
    private static final Double AMOUNT = 150.0;

    @Test
    public void testMap_VisitorCommand_CardDetailsRequestDto() {
        RequestDto requestDto = CommandToRequestMapper.map(VisitorCommand.LOGIN, List.of(CARD_NUMBER, PIN));
        Assertions.assertInstanceOf(CardDetailsRequestDto.class, requestDto);
        Assertions.assertEquals(CARD_NUMBER, ((CardDetailsRequestDto) requestDto).getCardNumber());
        Assertions.assertEquals(PIN, ((CardDetailsRequestDto) requestDto).getPin());
    }

    @Test
    public void testMap_VisitorCommand_unmapped() {
        Assertions.assertNull(CommandToRequestMapper.map(VisitorCommand.LOGIN, List.of(PIN, PIN, PIN)));
        Assertions.assertNull(CommandToRequestMapper.map(VisitorCommand.INFORMATION, new ArrayList<>()));
    }

    @Test
    public void testMap_UserCommand_DepositAmountRequestDto() {
        RequestDto requestDto = CommandToRequestMapper.map(UserCommand.DEPOSIT, List.of(AMOUNT));
        Assertions.assertInstanceOf(DepositAmountRequestDto.class, requestDto);
        Assertions.assertEquals(AMOUNT, ((DepositAmountRequestDto) requestDto).getAmount());
    }

    @Test
    public void testMap_UserCommand_WithdrawAmountRequestDto() {
        RequestDto requestDto = CommandToRequestMapper.map(UserCommand.WITHDRAW, List.of(AMOUNT));
        Assertions.assertInstanceOf(WithdrawAmountRequestDto.class, requestDto);
        Assertions.assertEquals(AMOUNT, ((WithdrawAmountRequestDto) requestDto).getAmount());
    }

    @Test
    public void testMap_UserCommand_PinChangeRequestDto() {
        RequestDto requestDto = CommandToRequestMapper.map(UserCommand.CHANGE_PIN, List.of(PIN, NEW_PIN));
        Assertions.assertInstanceOf(PinChangeRequestDto.class, requestDto);
        Assertions.assertEquals(PIN, ((PinChangeRequestDto) requestDto).getPin());
        Assertions.assertEquals(NEW_PIN, ((PinChangeRequestDto) requestDto).getNewPin());
    }

    @Test
    public void testMap_UserCommand_unmapped() {
        // not treated
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.VIEW_BALANCE, List.of()));
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.LOGOUT, List.of()));

        // invalid arguments
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.DEPOSIT, List.of()));
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.WITHDRAW, List.of()));
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.WITHDRAW, List.of("invalid_balance")));
        Assertions.assertNull(CommandToRequestMapper.map(UserCommand.CHANGE_PIN, List.of(NEW_PIN)));
    }
}
