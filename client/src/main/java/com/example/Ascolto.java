package com.example;

import java.io.BufferedReader;

public class Ascolto extends Thread {
    BufferedReader inDalServer;
    public Ascolto(BufferedReader in){
        this.inDalServer = in;
    }
    public void run(){
        String rigaRitornata = "";
        BufferedReader inDalServer = this.inDalServer;
        try {
            do{
                rigaRitornata = inDalServer.readLine();
                System.out.println("\t il server ha risposto: " + rigaRitornata);
                rigaRitornata = rigaRitornata.toUpperCase();
            }while(!rigaRitornata.equals("Q"));
        } catch (Exception e) {
            System.out.println("errore");
        }
    }
}
