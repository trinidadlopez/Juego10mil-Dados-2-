package Modelo;

import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable { //cuarta cosa que agregue 17/12, serializable
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

    /*public void tirarse(){ //elige un indice del dado(una cara) y te dice que valor hay en él
        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(caras.length);
        int valor = caras[indiceAleatorio];
        valorCaraSuperior=valor;
    }*/

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

