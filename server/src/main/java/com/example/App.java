package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            int numrand = (int) (Math.random() * 9 + 1);
            System.out.println("numero generato è " + numrand);

            ServerSocket serverSocket = new ServerSocket(3000);

            System.out.println("sono in ascolto");
            // metto in ascolto
            Socket client = serverSocket.accept();
            serverSocket.close();
            // creao il buffer in entrata
            BufferedReader inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // creo un stream in out ver il server
            DataOutputStream outVersoClient = new DataOutputStream(client.getOutputStream());

            int num;
            int cont = 0;
            // invio al client
            do {
                // leggo il numero in arrivo dal client
                num = Integer.parseInt(inDalClient.readLine());
                if (num == numrand) {
                    System.out.println("\ninvio 3");
                    outVersoClient.writeBytes("3" + "\n");
                }
                if (num > numrand) {
                    System.out.println("\ninvio 3");
                    outVersoClient.writeBytes("2" + "\n");
                }
                if (num < numrand) {
                    System.out.println("\ninvio 3");
                    outVersoClient.writeBytes("1" + "\n");
                }
                cont++;
            } while (numrand != num);

            // invio il numero di tentativi
            outVersoClient.writeBytes(cont + "\n");

            System.out.println("l'utente ci ha messo " + cont + " tentativi");

        } catch (Exception e) {
            System.out.println("\nErrore");
        }
    }
}
