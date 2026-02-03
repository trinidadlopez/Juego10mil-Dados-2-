package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable { //tercer cosa que agregue 17/12, lo de serializable
    private String nombreJugador;
    private int puntajeTotal;
    private int puntajeParcial; //ese me va a ser util si necesito limpiar los dados
    private ArrayList<Dado> dadosApartados;
    private int nroJugador;
    private ArrayList<Dado> dadosParciales;
    private Cubilete cubilete;

    public Jugador(String nombreJugador, ArrayList<Dado> dadosApartados) {
        this.nombreJugador = nombreJugador;
        this.puntajeTotal = 0;
        this.puntajeParcial = 0;
        this.dadosApartados = dadosApartados;
        this.dadosParciales = new ArrayList<>();
        this.cubilete = new Cubilete();
    }

    public void apartarDados(ArrayList<Dado> dado){
        dadosApartados.addAll(dado);
        getCubilete().getDados().removeAll(dado);
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

    public ArrayList<Dado> getDadosParciales() {
        return dadosParciales;
    }

    public int getPuntajeParcial() {
        return puntajeParcial;
    }

    public ArrayList<Dado> getDadosApartados() {
        return dadosApartados;
    }

    public Cubilete getCubilete() {
        return cubilete;
    }

    public void setDadosParciales(ArrayList<Dado> dadosParciales) {
        this.dadosParciales = dadosParciales;
    }

    public void setDadosApartados(Dado d) {
        dadosApartados.add(d);
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

