package com.example;

import java.io.BufferedReader;

public class Ascolto extends Thread {

    BufferedReader inDalServer;

    public Ascolto(BufferedReader in) {
        this.inDalServer = in;
    }

    public void run() {
        BufferedReader inDalServer = this.inDalServer;
        String messaggioRicevuto;
        try {
            do {
                messaggioRicevuto = inDalServer.readLine();
                System.out.println(messaggioRicevuto);
            } while (!messaggioRicevuto.equals("Q"));
        } catch (Exception e) {
            System.out.println("errore...CHIUDO TUTTO (Ascolto.java)");
        }
    }
}
