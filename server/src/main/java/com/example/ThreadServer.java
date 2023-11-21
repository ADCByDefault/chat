package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadServer extends Thread {
    Socket client;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ListaClient listaClient;
    String nomeClient;

    public ThreadServer(Socket client, ListaClient listaClient) {
        this.client = client;
        this.listaClient = listaClient;
        this.nomeClient = "null";
    }

    public void inviaMessaggio(Socket c, String messaggio) {
        try {
            DataOutputStream out = new DataOutputStream(c.getOutputStream());
            out.writeBytes(nomeClient + ": " + messaggio + "\n");
            System.out.println("\n" + c.getInetAddress() + ": " + messaggio);
        } catch (Exception e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

    public void inviaMessaggioDalServer(String messaggio) {
        try {
            outVersoClient.writeBytes("server: " + messaggio + "\n");
        } catch (Exception e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

    public void broadcast(String messaggio) {
        for (Socket c : listaClient.getAllClients()) {
            if (this.client == c) {
                continue;
            }
            inviaMessaggio(c, messaggio);
        }
    }

    public void run() {
        Socket client = this.client;
        try {
            // creao i tubi
            this.inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outVersoClient = new DataOutputStream(client.getOutputStream());
            String richiesta;
            String messaggio;
            do {
                inviaMessaggioDalServer("Inserisci un nome utente...");
                richiesta = inDalClient.readLine();
                if (listaClient.getClient(richiesta) != null) {
                    inviaMessaggioDalServer("Nickname già esistente");
                    continue;
                }
                if (richiesta.equals("") || richiesta.equals("SERVER") || richiesta.equals("@ALL")) {
                    inviaMessaggioDalServer("Nickname non valido");
                }
                this.nomeClient = richiesta;
                listaClient.aggiungiClient(client, this.nomeClient);
                broadcast("Sono Venuto a fare festa!!!");
                break;
            } while (true);
            do {
                inviaMessaggioDalServer("Fai la tua scelta...");
                richiesta = inDalClient.readLine();
                richiesta = richiesta.toUpperCase();
                switch (richiesta) {
                    case "Q":
                        System.out.println("client " + client.getInetAddress() + " ha chiesto di chiudere");
                        broadcast("se l'è cantata e se l'è suonata");
                        break;
                    case "C":
                        inviaMessaggioDalServer("I client disponibili sono: -" + listaClient.getAllNames());
                        break;
                    case "@ALL":
                        // "" o "null" per annulare la scelta
                        inviaMessaggioDalServer("Il tuo messaggio boradcast...");
                        messaggio = inDalClient.readLine();
                        if (messaggio.equals("") || messaggio.equals("NULL")) {
                            break;
                        }
                        System.out.println("invio brodcast da parte di: " + nomeClient);
                        this.broadcast(messaggio);
                        break;
                    default:
                        Socket c = listaClient.getClient(richiesta);
                        if (c == null) {
                            inviaMessaggio(client, "Il lama che cerchi non esiste!");
                            break;
                        }
                        inviaMessaggioDalServer("Cosa li vuoi dire?...");
                        messaggio = inDalClient.readLine();
                        if (messaggio.equals("") || messaggio.equals("NULL")) {
                            break;
                        }
                        inviaMessaggio(c, messaggio);
                        break;
                }
            } while (!richiesta.equals("Q"));
        } catch (Exception e) {
            System.out.println("\t\terrore nella comunicazione: " + e.getMessage());
        }
    }
}
