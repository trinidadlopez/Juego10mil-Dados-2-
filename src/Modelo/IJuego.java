package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IJuego extends IObservableRemoto {

    int siguienteNroJ() throws RemoteException;

    void iniciar_jugador(Jugador jugador) throws RemoteException;

    void comenzarJuego() throws RemoteException;

   void lanzar()throws RemoteException;

    void apartar_dados() throws RemoteException;

    void actualizar_turno_jugador() throws RemoteException;

    void jugador_plantado() throws RemoteException;

    Object[][] getTablaRanking() throws RemoteException;

    ArrayList<Jugador> getJugadores()throws RemoteException;

    int getNroRonda() throws RemoteException;

    Jugador getJugadorActual() throws RemoteException;

    void resetearJuego() throws RemoteException;

    EstadoJugada getEstadoJugada() throws RemoteException;
}
