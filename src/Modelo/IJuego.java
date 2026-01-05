package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IJuego extends IObservableRemoto {
    int siguienteNroJ() throws RemoteException;

    void iniciar_jugador(Jugador jugador) throws RemoteException;

    ArrayList<Jugador> getJugadores()throws RemoteException;

    void comenzarJuego() throws RemoteException;
}
