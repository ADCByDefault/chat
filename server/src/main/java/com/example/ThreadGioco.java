package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadGioco extends Thread {
    Socket client;
    int numrand;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    public ThreadGioco(Socket client, int numrand){
        this.client = client;
        this.numrand = numrand;
    }
    public void run(){
        int numrand = this.numrand;
        int num;
        int cont = 0;
         try{
            System.out.println("sono collegato al client: " + client.getInetAddress());
            // creao i tubi
            this.inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.outVersoClient = new DataOutputStream(client.getOutputStream());
            // invio al client
            do {
                // leggo il numero in arrivo dal client
                num = Integer.parseInt(inDalClient.readLine());
                if (num == numrand) {
                    System.out.println("\ninvio 3");
                    outVersoClient.writeBytes("3" + "\n");
                }
                if (num > numrand) {
                    System.out.println("\ninvio 2");
                    outVersoClient.writeBytes("2" + "\n");
                }
                if (num < numrand) {
                    System.out.println("\ninvio 1");
                    outVersoClient.writeBytes("1" + "\n");
                }
                cont++;
            } while (numrand != num);
            // invio il numero di tentativi
            outVersoClient.writeBytes(cont + "\n");
        }catch(Exception e){
            System.out.println("/t/terrore nella comunicazione: " + e.getMessage());
        }
    }
}
