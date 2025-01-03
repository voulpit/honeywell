package com.honeywell.atm.console;

import com.honeywell.atm.console.interaction.AtmObject;
import com.honeywell.atm.console.socket.AtmClientSocket;

public class Main {
    public static void main(String[] args) {
        int port = 9000;
        while (true) {
            try {
                AtmClientSocket atmClientEndpoint = new AtmClientSocket(port);
                AtmObject atmObject = new AtmObject(atmClientEndpoint);
                atmObject.listen();
            } catch(Exception e){
                System.out.println("Connection error: " + e.getMessage());
            }
        }
    }
}