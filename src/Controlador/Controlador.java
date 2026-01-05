package Controlador;

import Modelo.Dado;
import Modelo.Eventos;
import Modelo.IJuego;
import Modelo.Jugador;
import Vista.VistaGrafica;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    private IJuego juego;
    //private IVista vista;
    private VistaGrafica vista;
    int nroJugador;
    ArrayList<Dado> dadosApartados;

    public Controlador() {
    }

    public void setVista(VistaGrafica vista){
        this.vista = vista;
    }
    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.juego= (IJuego) t;
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {
        try{
            Eventos evento = (Eventos) o;
            switch (evento){
                case JUGADOR_AGREGADO:
                    vista.agregarJugadorALaEspera(juego.getJugadores());
                    break;
                case MINIMO_JUGADORES_ALCANZADOS:
                    vista.habilitarIniciar(true);
                    break;
                case COMENZAR_JUEGO:
                    vista.sacarVEspera();
                    //juego.repartirDados();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void iniciarJugador(String nombre) throws RemoteException{
        if(dadosApartados==null){
            dadosApartados = new ArrayList<>();
        }
        Jugador jugador = new Jugador(nombre, dadosApartados);
        jugador.setNroJugador(juego.siguienteNroJ());
        nroJugador=jugador.getNroJugador();
        juego.iniciar_jugador(jugador);
    }

    public void comenzarJuego() throws RemoteException{
        juego.comenzarJuego();
    }

}
