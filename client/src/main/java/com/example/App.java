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
            Socket socket = new Socket("localhost", 3000);
            DataOutputStream outVersoServer = new DataOutputStream(socket.getOutputStream());
            BufferedReader inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // leggo dalla tastiera
            String rigaRitornata;
            do {
                System.out.println("metti un numero...");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                Integer num = Integer.parseInt(in.readLine());

                outVersoServer.writeBytes(num + "\n");
                rigaRitornata = inDalServer.readLine();
                switch (rigaRitornata) {
                    case "1":
                        System.out.println("il tuo numero inserito è basso...");
                        break;
                    case "2":
                        System.out.println("il tuo numero inserito è alto...");
                        break;
                    case "3":
                        System.out.println("il tuo numero inserito è giusto");
                        break;
                    default:
                        break;
                }
            } while (!rigaRitornata.equals("3"));

            rigaRitornata = inDalServer.readLine();
            System.out.println("\n\nFine " + rigaRitornata);
            socket.close();

        } catch (Exception e) {
            System.out.println("Errore");
        }
    }
}
