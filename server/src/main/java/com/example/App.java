package com.example;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ListaClient lista = new ListaClient();
        try {
            // creato un socket
            ServerSocket serverSocket = new ServerSocket(3000);
            // accetto il client che si collega
            while (true) {
                // apro il socket di ascolto
                Socket client = serverSocket.accept();
                // passo tutto alla classe ThreadServer
                ThreadServer th = new ThreadServer(client, lista);
                th.start();
            }
        } catch (Exception e) {
            System.out.println("\nErrore nel Main del server");
        }
    }
}
