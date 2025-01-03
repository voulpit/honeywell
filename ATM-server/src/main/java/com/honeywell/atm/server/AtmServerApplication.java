package com.honeywell.atm.server;

import com.honeywell.atm.server.socket.AtmServerSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class AtmServerApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(AtmServerApplication.class, args);
        AtmServerSocket serverSocket = (AtmServerSocket) context.getBean("atmServerSocket");
        try {
            serverSocket.listen();
        } catch (IOException e) {
            serverSocket.close();
        }
    }
}
