package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static Modelo.Eventos.*;

public class Juego extends ObservableRemoto implements Serializable, IObservableRemoto, IJuego {
    private ArrayList<Jugador> jugadores;
    private Jugador ganador_parcial;
    //private ArrayList<Lanzamientos> lanzamientos;
    private Jugador jugadorActual;
    private int siguiente_nroJ=-1;
    private boolean minimoAlcanzado=false;

    public Juego(){
        jugadores = new ArrayList<>();
    }

    @Override
    public int siguienteNroJ(){
        siguiente_nroJ=siguiente_nroJ+1;
        return siguiente_nroJ;
    }

    @Override
    public void iniciar_jugador(Jugador jugador) throws RemoteException {
        jugadores.add(jugador);
        notificarObservadores(JUGADOR_AGREGADO);
        if(!minimoAlcanzado && jugadores.size()>=2){
            minimoAlcanzado=true;
            notificarObservadores(MINIMO_JUGADORES_ALCANZADOS);

        }
    }

    @Override
    public void comenzarJuego() throws RemoteException{
        notificarObservadores(COMENZAR_JUEGO);
    }
    @Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }


}
