package com.example;

public class Biglietto {
    int numBiglietti;

    public Biglietto(int n){
        this.numBiglietti = n;
    }

    public boolean acquista(){
        if(numBiglietti > 0){
            this.numBiglietti--;
        }
        if(numBiglietti <=0){
            return false;
        }
        return true;
    }
    public int getDisponibili(){
        return this.numBiglietti;
    }
}
