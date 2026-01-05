package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable { //tercer cosa que agregue 17/12, lo de serializable
    private String nombreJugador;
    private int puntajeTotal;
    private int puntajeParcial; //ese me va a ser util si necesito limpiar los dados
    private ArrayList<Dado> dadosApartados;
    private int nroJugador;

    public Jugador(String nombreJugador, ArrayList<Dado> dadosApartados) {
        this.nombreJugador = nombreJugador;
        this.puntajeTotal = 0;
        this.puntajeParcial = 0;
        this.dadosApartados = dadosApartados;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNroJugador(int nroJugador){
        this.nroJugador = nroJugador;
    }

    public int getNroJugador(){
        return nroJugador;
    }
    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public int getPuntajeParcial() {
        return puntajeParcial;
    }

    public ArrayList<Dado> getDadosApartados() {
        return dadosApartados;
    }

    public void setDadosApartados(Dado dado) {
        this.dadosApartados.add(dado);
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public void setPuntajeParcial(int puntajeParcial) {
        this.puntajeParcial = puntajeParcial;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
}

