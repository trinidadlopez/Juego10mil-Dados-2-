package Vista;

import Controlador.Controlador;
import Modelo.Jugador;

import java.util.ArrayList;

public class VistaGrafica {
    private Controlador controlador;
    private VMenuPrincipal menuPrincipal;
    private VNombreJugador nombreJugador;
    private VEsperandoJugador esperandoJ;
    public VJuego ventanaJuego;

    public VistaGrafica(Controlador controlador){
        this.controlador  = controlador;
        controlador.setVista(this);
    }

    public void iniciar(){
        menuPrincipal = new VMenuPrincipal(this, controlador);
        nombreJugador = new VNombreJugador(this, controlador);
        esperandoJ = new VEsperandoJugador(this, controlador);
        ventanaJuego= new VJuego(this, controlador);
        mostrarMenuPrincipal();
    }
    public void mostrarMenuPrincipal(){
        menuPrincipal.setVisible(true);
    }

    public void mostrarNombreJugador(){
        nombreJugador.setVisible(true);
    }

    public void mostrarEsperandoJugador(){
        esperandoJ.setVisible(true);
    }

    public void agregarJugadorALaEspera(ArrayList<Jugador> jugadores){
        esperandoJ.agregarJ(jugadores);
    }

    public void sacarVEspera(){
        esperandoJ.setVisible(false);
    }

    public void habilitarIniciar(boolean habilitar){
        esperandoJ.habilitarBotonIniciar(habilitar);
    }

    public void mostrarJuego(){
        ventanaJuego.setVisible(true);
    }
}
