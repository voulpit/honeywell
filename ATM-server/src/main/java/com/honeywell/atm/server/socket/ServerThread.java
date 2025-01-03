package com.honeywell.atm.server.socket;

import com.honeywell.atm.core.model.CardDetails;
import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.ResponseDto;
import com.honeywell.atm.core.model.dto.impl.*;
import com.honeywell.atm.core.model.exception.AtmException;
import com.honeywell.atm.core.model.exception.ExceptionMessage;
import com.honeywell.atm.server.service.handler.RequestHandler;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ServerThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerThread.class);

    private final Socket socket;
    private final RequestHandler requestHandler;
    private final Session session;
    private final Set<String> onlineUsers;

    public ServerThread(Socket socket, RequestHandler requestHandler, Set<String> onlineUsers) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.session = new Session();
        this.onlineUsers = onlineUsers;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RequestDto requestDto = getRequest(socket);
                boolean isUserOnline = (requestDto instanceof CardDetailsRequestDto cardDetailsRequestDto) &&
                        this.onlineUsers.contains(cardDetailsRequestDto.getCardNumber());

                if (isUserOnline) {
                    respond(socket, new ResponseDto(new AtmException(ExceptionMessage.ALREADY_LOGGED.getMessage())));
                } else if (requestDto instanceof LogoutRequestDto) {
                    respond(socket, new ResponseDto());
                    this.onlineUsers.remove(session.getCardNumber());
                } else {
                    enhanceRequest(requestDto);
                    ResponseDto responseDto = buildResponse(requestDto);
                    respond(socket, responseDto);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception encountered; closing current socket: ", e);
            this.closeSocket();
            this.onlineUsers.remove(session.getCardNumber());
        }
    }

    private RequestDto getRequest(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (RequestDto) objectInputStream.readObject();
    }

    private void enhanceRequest(RequestDto requestDto) {
        if (requestDto instanceof PinChangeRequestDto) {
            requestDto.setCardNumber(session.getCardNumber());
        } else if (requestDto instanceof DepositAmountRequestDto || requestDto instanceof WithdrawAmountRequestDto) {
            requestDto.setCardNumber(session.getCardNumber());
            requestDto.setPin(session.getPin());
        }
    }

    private void updateSession(RequestDto requestDto) {
        if (requestDto instanceof CardDetailsRequestDto) {
            session.setCardNumber(((CardDetailsRequestDto) requestDto).getCardNumber());
            session.setPin(((CardDetailsRequestDto) requestDto).getPin());
        }
        if (requestDto instanceof PinChangeRequestDto) {
            session.setPin(((PinChangeRequestDto) requestDto).getNewPin());
        }
    }

    private ResponseDto buildResponse(RequestDto requestDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            CardDetails cardDetails = requestHandler.handle(requestDto);
            responseDto.setCardDetails(cardDetails);
            updateSession(requestDto);
            this.onlineUsers.add(session.getCardNumber());
        } catch (ConstraintViolationException e) {
            StringBuilder errorSb = new StringBuilder();
            e.getConstraintViolations().forEach(cv -> errorSb.append(cv.getMessage()).append("; "));
            responseDto.setException(new AtmException(errorSb.toString()));
        } catch (AtmException e) {
            responseDto.setException(e);
        }
        return responseDto;
    }

    private void respond(Socket socket, ResponseDto responseDto) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(responseDto);
    }

    private void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            LOGGER.error("Error closing socket, ", e);
        }
    }
}
