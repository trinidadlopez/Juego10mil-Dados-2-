package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IJuego extends IObservableRemoto {

    int siguienteNroJ() throws RemoteException;

    void iniciar_jugador(Jugador jugador) throws RemoteException;

    ArrayList<Jugador> getJugadores()throws RemoteException;

    void comenzarJuego() throws RemoteException;


   void lanzar()throws RemoteException;

    int calcularPuntajeDados() throws RemoteException;

    void apartar_dados() throws RemoteException;

    Jugador getJugadorActual() throws RemoteException;

    void actualizar_turno_jugador() throws RemoteException;

    int getJugadorActualNro() throws RemoteException;

    int calcularPuntajeTotal() throws RemoteException;

    String nombreJugador() throws RemoteException;

    void calcularPuntaje() throws RemoteException;

    int getNroRonda() throws RemoteException;

}
