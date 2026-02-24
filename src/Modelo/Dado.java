package Modelo;

import java.io.Serializable;

public class Dado implements Serializable {
    private int[] caras;
    private int valorCaraSuperior;

    public Dado() {
        caras=new int[6];
        caras[0]=1;
        caras[1]=2;
        caras[2]=3;
        caras[3]=4;
        caras[4]=5;
        caras[5]=6;
    }

    public int getValorCaraSuperior() {
        return valorCaraSuperior;
    }

    public int[] getCaras(){
        return caras;
    }

    public void setValorCaraSuperior(int valorCaraSuperior) {
        this.valorCaraSuperior = valorCaraSuperior;
    }
}

