package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ThreadServer extends Thread {
    Socket client;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ListaClient listaClient;
    String nomeClient;

    public ThreadServer(Socket client, ListaClient listaClient) {
        this.client = client;
        this.listaClient = listaClient;
        System.out.println("\n creo un thread con " + client.getInetAddress());
    }

    public void inviaMessaggio(Socket c, String messaggio) {
        try {
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            out.writeBytes(nomeClient + ": " + messaggio + "\n");
        } catch (Exception e) {
            System.out.println("errore nell'invio del messaggio");
        }
        System.out.println("\n invio il messaggio a client: " + c.getInetAddress() + " : " + messaggio);
    }

    public void run() {
        Socket client = this.client;
        try {
            // creao i tubi
            this.inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outVersoClient = new DataOutputStream(client.getOutputStream());
            String richiesta = "";
            do {
                System.out.println("aspetto il nome del client");
                richiesta = inDalClient.readLine();
                System.out.println("nome ricevuto : " + richiesta);

                if (listaClient.getClient(richiesta) != null) {
                    inviaMessaggio(client, "nickname gia' esistente");
                    continue;
                }
                this.nomeClient = richiesta;
                listaClient.aggiungiClient(client, richiesta);
                System.out.println("ho aggiunto " + nomeClient + " alla lista");
                break;
            } while (true);
            // invio al client
            do {
                richiesta = inDalClient.readLine();
                System.out.println("il client " + client.getInetAddress() + " inviato:" + richiesta);
                richiesta = richiesta.toUpperCase();
                switch (richiesta) {
                    case "C":
                        System.out.println("entro in c");
                        String listaNomi = listaClient.getAllNames();
                        inviaMessaggio(client, "i client disponibili sono: -" + listaNomi);
                        break;
                    case "B":
                        System.out.println("aspetto il messaggio:");
                        richiesta = inDalClient.readLine();
                        System.out.println("invio brodcast da parte di: " + nomeClient);
                        for (Socket c : listaClient.getAllClients()) {
                            if (this.client == c) {
                                continue;
                            }
                            inviaMessaggio(c, richiesta);
                        }
                        break;
                    case "Q":
                        System.out.println("entro in q");
                        System.out.println("client " + client.getInetAddress() + " ha richiesto di chiudere");
                        break;

                    default:
                        System.out.println("entro in default con richiesta: " + richiesta);
                        Socket c = listaClient.getClient(richiesta);
                        if (c == null) {
                            System.out.println("\n client non trovato");
                            inviaMessaggio(client, "il lama che cerchi non esiste, coglione!!");
                        } else {
                            System.out.println("aspetto il messaggio:");
                            richiesta = inDalClient.readLine();
                            inviaMessaggio(c, richiesta);
                        }
                        break;
                }
            } while (!richiesta.equals("Q"));
        } catch (Exception e) {
            System.out.println("\t\terrore nella comunicazione: " + e.getMessage());
        }
    }
}
