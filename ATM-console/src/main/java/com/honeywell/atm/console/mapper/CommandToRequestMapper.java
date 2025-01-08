package com.honeywell.atm.console.mapper;

import com.honeywell.atm.console.model.cmd.Command;
import com.honeywell.atm.console.model.cmd.UserCommand;
import com.honeywell.atm.console.model.cmd.VisitorCommand;
import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.impl.CardDetailsRequestDto;
import com.honeywell.atm.core.model.dto.impl.DepositAmountRequestDto;
import com.honeywell.atm.core.model.dto.impl.PinChangeRequestDto;
import com.honeywell.atm.core.model.dto.impl.WithdrawAmountRequestDto;
import com.honeywell.atm.core.model.exception.ExceptionMessage;

import java.util.List;

public class CommandToRequestMapper {

    public static RequestDto map (Command command, List<Object> params) {
        if (command instanceof VisitorCommand) {
            return map ((VisitorCommand) command, params);
        }
        return map ((UserCommand) command, params);
    }

    private static RequestDto map (VisitorCommand command, List<Object> params) {
        if (command.getShortcut() == VisitorCommand.LOGIN.getShortcut() && params.size() == 2) {
            return new CardDetailsRequestDto((String) params.get(0), (String) params.get(1));
        }
        return null;
    }

    private static RequestDto map (UserCommand command, List<Object> params) {
        try {
            if (command.getShortcut() == UserCommand.DEPOSIT.getShortcut() && params.size() == 1) {
                return new DepositAmountRequestDto(Double.parseDouble(params.getFirst().toString()));
            }
            if (command.getShortcut() == UserCommand.WITHDRAW.getShortcut() && params.size() == 1) {
                return new WithdrawAmountRequestDto(Double.parseDouble(params.getFirst().toString()));
            }
            if (command.getShortcut() == UserCommand.CHANGE_PIN.getShortcut() && params.size() == 2) {
                return new PinChangeRequestDto((String) params.get(0), (String) params.get(1));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: " + ExceptionMessage.INVALID_AMOUNT.getMessage() + "\n");
        }
        return null;
    }

    private CommandToRequestMapper() {
    }
}
