package com.honeywell.atm.server.socket;

import com.honeywell.atm.server.service.handler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Component
public class AtmServerSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtmServerSocket.class);

    private final ServerSocket serverSocket;
    private final RequestHandler requestHandler;
    private final Set<String> clients;

    public AtmServerSocket(Environment environment, RequestHandler requestHandler) throws IOException {
        int port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("atm.server.port")));
        this.serverSocket = new ServerSocket(port);
        this.requestHandler = requestHandler;
        this.clients = new HashSet<>();
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            LOGGER.info("New client connected");

            new ServerThread(socket, requestHandler, Collections.synchronizedSet(clients)).start();
        }
    }

    public void close() throws IOException {
        LOGGER.info("Closing server socket");
        this.serverSocket.close();
    }
}
