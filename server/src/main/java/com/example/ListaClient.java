package com.example;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaClient {
    HashMap<String, Socket> lista;

    public ListaClient () {
        lista = new HashMap();
    }

    public void aggiungiClient(Socket socket, String nome) {
        lista.put(nome, socket);
    }

    public Socket getSocketByName (String nome) {
        return lista.get(nome);
    }
    public String getAllNames () {
        String s = String.join("\n-", lista.keySet());
        return s;
    }

    public Socket getClient (String nome) {
        if (lista.containsKey(nome)) {
            return lista.get(nome);
        }
        return null;
    }
    
    public ArrayList<Socket> getAllClients() {
        return new ArrayList<Socket>(lista.values());
    }

    public void rimuoviClient(String name){
        lista.remove(name);
    }
}
