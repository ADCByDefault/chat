package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ArrayList <Socket> listaClient = new ArrayList();
        try {
            // creao un socket
            ServerSocket serverSocket = new ServerSocket(3000);
            Biglietto b = new Biglietto(6);
            // accetto il client che si collega
            while (true) {
                // apro il socket di ascolto
                Socket client = serverSocket.accept();
                // passo tutto alla classe ThreadGioco
                ThreadGioco th = new ThreadGioco(client, b, listaClient);
                th.start();
            }
        } catch (Exception e) {
            System.out.println("\nErrore");
        }
    }
}
