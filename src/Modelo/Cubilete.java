package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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

    public ArrayList<Dado> tirarse(){ //elige un indice del dado(una cara) y te dice que valor hay en él
        Random rand = new Random();
        ArrayList<Dado> rta = new ArrayList<>();
        for(Dado dado : dados){
            int indiceAleatorio = rand.nextInt(dado.getCaras().length);
            int valor = dado.getCaras()[indiceAleatorio];
            dado.setValorCaraSuperior(valor);
            rta.add(dado);
        }
        return rta;
    }

    public void setDados(ArrayList<Dado> dados) {
        this.dados = dados;
    }

    public void actualizar_cubilete(ArrayList<Dado> dados_borrar){
        for(Dado d:dados_borrar){
            dados.remove(d);
        }
    }

    public void reestablecer_cubilete(){
        while(dados.size()<5){
            dados.add(new Dado());
        }
    }
}