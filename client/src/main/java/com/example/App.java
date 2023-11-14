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
            System.out.println("C per vedere i client, per inviare un messaggio invia prima il nome del client e poi messaggio");
            do {
                scelta = leggiTastiera.readLine();
                scelta = scelta.toUpperCase().trim();
                System.out.println("invio al server : " + scelta );
                outVersoServer.writeBytes(scelta+"\n");
            } while (!scelta.equals("Q"));
            socket.close();
        } catch (Exception e) {
            System.out.println("Errore... CHIUDO TUTTO (main)");
        }
    }
}
