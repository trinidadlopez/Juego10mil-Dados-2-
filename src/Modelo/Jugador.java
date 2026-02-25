package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Jugador implements Serializable { //tercer cosa que agregue 17/12, lo de serializable
    private String nombreJugador;
    private int puntajeTotal;
    private int puntajeParcial; //ese me va a ser util si necesito limpiar los dados
    private ArrayList<Dado> dadosApartados;
    private int nroJugador;
    private ArrayList<Dado> dadosParciales;
    private String fechaJugado;

    public Jugador(String nombreJugador, ArrayList<Dado> dadosApartados) {
        this.nombreJugador = nombreJugador;
        this.puntajeTotal = 0;
        this.puntajeParcial = 0;
        this.dadosApartados = new ArrayList<>();
        this.dadosParciales = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaJugado = fechaActual.format(formato);
    }

    //GETs
    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getFechaJugado() {
        return fechaJugado;
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

    //SETs
    public void setNroJugador(int nroJugador){
        this.nroJugador = nroJugador;
    }

    public void setDadosParciales(ArrayList<Dado> dadosParciales) {
        this.dadosParciales = dadosParciales;
    }

    public void setDadosApartados(Dado d) {
        dadosApartados.add(d);
    }

    public void setPuntajeTotal() {
        this.puntajeTotal = this.puntajeTotal + this.puntajeParcial;
    }

    public void setPuntajeParcial(int puntajeParcial) {
        this.puntajeParcial = puntajeParcial;
    }

}

