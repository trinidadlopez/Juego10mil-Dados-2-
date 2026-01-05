package Modelo;

import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable { //cuarta cosa que agregue 17/12, serializable
    private int[] dados;
    private int valorCaraSuperior;

    public Dado() {
        dados=new int[6];
        dados[0]=1;
        dados[1]=2;
        dados[2]=3;
        dados[3]=4;
        dados[4]=5;
        dados[5]=6;
    }

    public void tirarse(){ //elige un indice del dado(una cara) y te dice que valor hay en él
        Random rand = new Random();
        int indiceAleatorio = rand.nextInt(dados.length);
        int valor = dados[indiceAleatorio];
        valorCaraSuperior=valor;
    }

    public int getValorCaraSuperior() {
        return valorCaraSuperior;
    }
}

