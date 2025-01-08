package com.honeywell.atm.console.interaction;

import com.honeywell.atm.console.mapper.CommandToRequestMapper;
import com.honeywell.atm.console.model.AtmContext;
import com.honeywell.atm.console.model.cmd.Command;
import com.honeywell.atm.console.model.cmd.UserCommand;
import com.honeywell.atm.console.model.cmd.VisitorCommand;
import com.honeywell.atm.console.socket.AtmClientSocket;
import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.ResponseDto;
import com.honeywell.atm.core.model.dto.impl.CardDetailsRequestDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AtmObject {
    private final AtmContext context;
    private final AtmClientSocket clientSocket;

    public AtmObject(AtmClientSocket clientSocket) {
        this.context = new AtmContext();
        this.clientSocket = clientSocket;
    }

    public void listen() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Honeywell ATM!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!clientSocket.isClosed()) {
            System.out.println(getAvailableCommands() + "\n");
            String input = reader.readLine();
            Command command = getCommand(input);

            List<Object> params = new ArrayList<>();
            for (String paramPrompt : command.getParamPrompts()) {
                System.out.print(paramPrompt);
                params.add(reader.readLine());
            }

            RequestDto requestDto = interpretRequest(command, params);
            if (requestDto != null) {
                ResponseDto responseDto = clientSocket.request(requestDto);
                interpretResponse(requestDto, responseDto);
            }
        }
    }

    private RequestDto interpretRequest(Command command, List<Object> params) {
        if (command instanceof VisitorCommand visitorCommand) {
            if (visitorCommand.getShortcut() == VisitorCommand.INFORMATION.getShortcut()) {
                return null;
            }
        }
        if (command instanceof UserCommand userCommand) {
            if (userCommand.getShortcut() == UserCommand.VIEW_BALANCE.getShortcut()) {
                System.out.println("Balance = " + context.getBalance());
                return null;
            }
            if (userCommand.getShortcut() == UserCommand.LOGOUT.getShortcut()) {
                System.out.println("Goodbye, " + context.getOwnerName());
                context.logout();
                clientSocket.closeConnection();
                return null;
            }
            if (userCommand.getShortcut() == UserCommand.INFORMATION.getShortcut()) {
                return null;
            }
        }

        return CommandToRequestMapper.map(command, params);
    }

    private void interpretResponse(RequestDto request, ResponseDto response) {
        boolean successful = response.getException() == null;
        if (request instanceof CardDetailsRequestDto) {
            System.out.println(successful ?
                    "Welcome, " + response.getCardDetails().getOwner() + "\n" :
                    "Error: " + response.getException().getMessage() + "\n");
        } else {
            System.out.println(successful ? "OK\n" : "Error: " + response.getException().getMessage() + "\n");
        }
        if (successful) {
            context.setCardDetails(response.getCardDetails());
        }
    }

    private String getAvailableCommands() {
        return "Available commands:\n" + (context.isLogged() ? UserCommand.getAllCommandsDescription() : VisitorCommand.getAllCommandsDescription());
    }

    private Command getCommand(String input) {
        return context.isLogged() ? UserCommand.shortcutToCommand(input) : VisitorCommand.shortcutToCommand(input);
    }
}
