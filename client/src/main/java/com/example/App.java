package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            // creao un socket / collegamento al server
            Socket socket = new Socket("localhost", 3000);
            // creo i buffer e stream per comunicare
            DataOutputStream outVersoServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader leggiTastiera = new BufferedReader(new InputStreamReader(System.in));
            String rigaRitornata = "";
            String scelta = "";
            Ascolto a = new Ascolto(inDalServer);
            a.start();
            do {
                System.out.println("fai la scelta");
                scelta = leggiTastiera.readLine();
                outVersoServer.writeBytes(scelta+"\n");
                scelta = scelta.toUpperCase();
            } while (!scelta.equals("Q"));
            socket.close();

        } catch (Exception e) {
            System.out.println("Errore");
        }
    }
}
