package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        try {
            // creao un socket / collegamento al server
            Socket socket = new Socket("localhost", 3000);
            // creo i buffer e stream per comunicare
            DataOutputStream outVersoServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader leggiTastiera = new BufferedReader(new InputStreamReader(System.in));
            String scelta = "";
            Ascolto a = new Ascolto(inDalServer);
            a.start();
            // Q per chiudere, C per vedere tutti gli utenti, @all o il nome del utente poi
            // invia il messaggio.
            // tutti gli input dalla tastiera sono messi in Uppercase automaticamente
            do {
                scelta = leggiTastiera.readLine();
                scelta = scelta.toUpperCase().trim();
                outVersoServer.writeBytes(scelta + "\n");
            } while (!scelta.equals("Q"));

            socket.close();

        } catch (Exception e) {
            System.out.println("Errore... CHIUDO TUTTO (main)");
        }
    }
}
