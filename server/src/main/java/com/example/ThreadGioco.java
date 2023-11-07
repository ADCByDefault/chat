package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadGioco extends Thread {
    Socket client;
    Biglietto b;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ArrayList<Socket> listaClient;

    public ThreadGioco(Socket client, Biglietto b, ArrayList<Socket> listaClient) {
        this.client = client;
        this.b = b;
        this.listaClient = listaClient;
        listaClient.add(client);
    }

    public void run() {
        Biglietto b = this.b;
        Socket client = this.client;
        try {
            // creao i tubi
            this.inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outVersoClient = new DataOutputStream(client.getOutputStream());
            BufferedReader leggiTastiera = new BufferedReader(new InputStreamReader(System.in));
            String daRitornare = "";
            String richiesta;
            // invio al client
            do {
                richiesta = inDalClient.readLine();
                System.out.println("il client " + client.getInetAddress() + " inviato:" + richiesta);
                richiesta = richiesta.toUpperCase();
                switch (richiesta) {
                    case "D":
                        daRitornare = "Biglietti disponibili " + b.getDisponibili() + "";
                        break;
                    case "A":
                        daRitornare = "Biglietti esauriti";
                        if (b.getDisponibili() > 0) {
                            b.acquista();
                            daRitornare = "Biglietto acquistato";
                            if (b.getDisponibili() <= 0) {

                                for (Socket socket : listaClient) {
                                    System.out.println("invio");
                                    DataOutputStream o = new DataOutputStream(socket.getOutputStream());
                                    o.writeBytes("TUTTO ESAURITO!!!!" + "\n");
                                }
                            }
                        }
                        break;
                    case "Q":
                        daRitornare = "q";
                        System.out.println("client " + client.getInetAddress() + " ha richiesto di chiudere");
                        break;

                    default:
                        daRitornare = "inserimento invalido";
                        break;
                }

                outVersoClient.writeBytes(daRitornare + "\n");
            } while (!richiesta.equals("Q"));
            System.out.println("\n tutto chiuso con il client " + client.getInetAddress());
        } catch (Exception e) {
            System.out.println("\t\terrore nella comunicazione: " + e.getMessage());
        }
    }
}
