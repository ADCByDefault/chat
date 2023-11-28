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
            out.writeBytes("\u001B[35m"+nomeClient + ":\u001B[37m " + messaggio + "\n");
            System.out.println("\n" + c.getInetAddress() + ": " + messaggio);
        } catch (Exception e) {
            System.out.println("\u001B[31merrore nell'invio del messaggio\u001B[37m");
        }
    }

    public void inviaMessaggioDalServer(String messaggio) {
        try {
            outVersoClient.writeBytes("\u001B[32mserver: " + messaggio + "\u001B[37m\n");
        } catch (Exception e) {
            System.out.println("\u001B[31merrore nell'invio del messaggio\u001B[37m");
        }
    }

    public void broadcast(String messaggio) {
        for (Socket c : listaClient.getAllClients()) {
            if (this.client == c) {
                continue;
            }
            inviaMessaggio(c, "\u001B[33m" + messaggio + "\u001B[37m");
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
                    inviaMessaggioDalServer("\u001B[31Nickname gia' esistente\u001B[37");
                    continue;
                }
                if (richiesta.equals("") || richiesta.equals("SERVER") || richiesta.equals("@ALL")) {
                    inviaMessaggioDalServer("\u001B[31Nickname non valido\u001B[37");
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
                        broadcast("\u001B[31mse l'e' cantata e se l'e' suonata\u001B[37m");
                        listaClient.rimuoviClient(nomeClient);
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
                            inviaMessaggioDalServer("\u001B[31mIl lama che cerchi non esiste!\u001B[37m");
                            break;
                        }
                        inviaMessaggioDalServer("Cosa gli vuoi dire?...");
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
