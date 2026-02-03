package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Cubilete implements Serializable { //fijarse que tan necesarrio es que sea serializable
    private ArrayList<Dado> dados;

    public Cubilete() {
        dados = new ArrayList<>();
        dados.add(new Dado());
        dados.add(new Dado());
        dados.add(new Dado());
        dados.add(new Dado());
        dados.add(new Dado());
    }

    public ArrayList<Dado> getDados() {
        return dados;
    }
}