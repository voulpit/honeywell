package com.honeywell.atm.console.socket;

import com.honeywell.atm.core.model.dto.RequestDto;
import com.honeywell.atm.core.model.dto.ResponseDto;

import java.io.*;
import java.net.Socket;

public class AtmClientSocket {
    private final Socket socket;

    public AtmClientSocket(int port) throws IOException {
        this.socket = new Socket("localhost", port);
    }

    public ResponseDto request(RequestDto requestDto) throws IOException, ClassNotFoundException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(requestDto);

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (ResponseDto) objectInputStream.readObject();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void closeConnection() {
        try {
            this.socket.close();
        } catch (IOException exception) {
            System.out.println("Error closing the socket");
        }
    }
}
