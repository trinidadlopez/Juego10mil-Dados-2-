package Vista;

import Controlador.Controlador;
import Modelo.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class VistaGrafica {
    private Controlador controlador;
    private VMenuPrincipal menuPrincipal;
    private VNombreJugador nombreJugador;
    private VLobby lobby;
    private VJuego ventanaJuego;
    private VPuntaje tabla_puntaje;
    private boolean juegoMostrado = false;


    public VistaGrafica(Controlador controlador){
        this.controlador  = controlador;
        controlador.setVista(this);
    }

    public void iniciar(){
        menuPrincipal = new VMenuPrincipal(this, controlador);
        nombreJugador = new VNombreJugador(this, controlador);
        lobby = new VLobby(this, controlador);
        ventanaJuego= new VJuego(this, controlador);
        mostrarMenuPrincipal();
        tabla_puntaje = new VPuntaje();
    }
    public void mostrarMenuPrincipal(){
        menuPrincipal.setVisible(true);
    }

    public void mostrarNombreJugador(){
        nombreJugador.setVisible(true);
    }


    public void mostrarLobby(){
        lobby.setVisible(true);
    }

    public void ocultarLobby(){
        lobby.setVisible(false);
    }

    public void mostrarJuego() throws RemoteException {
        if (!juegoMostrado) { //para no tener problemas en que se me "reabra" la ventana muchas vecez y me permita minimizarla sin problemas
            ventanaJuego.setTitle("Juego 10mil - En curso. Vista de " + controlador.nombreJugadorVentana());
            ventanaJuego.setVisible(true);
            juegoMostrado = true;
        }
    }

    public void actualizarLobbyJugadores(ArrayList<Jugador> jugadores) {
        lobby.actualizarJugadores(jugadores);
    }

    public void iniciarTimerLobby() {
        lobby.iniciarTimer(); // SOLO el que arranca
    }

    public void mostrarDados(ArrayList<Integer> dadosLanzados, int idJugador){
        System.out.println("VISTA GRAFICA >> mostrarDados");
        ventanaJuego.mostrarDadosLanzados(dadosLanzados, idJugador);
    }


    public void agregarPuntajeRondaTabla(int puntaje, String nombre, int puntajeTotal, int ronda){
        tabla_puntaje.agregarPuntaje(puntaje, nombre, puntajeTotal, ronda);
        tabla_puntaje.setVisible(true);
    }

    /*public void mostrarHabilitarBotonesYMostrarMsj(){
        ventanaJuego.habilitarBotonesYMostrarMsj();
    }*/


    public void mostrarTurnoJugadorAdecuado(String nombre){
        ventanaJuego.mostrarJugadorActual(nombre);
    }

    public void mostrarDadosApartados(ArrayList<Integer> d){
        ventanaJuego.dados_apartados(d);
    }

    public void mostrarMsjEscalera(String nombre){
        ventanaJuego.msjEscalera(nombre);
    }

    public void mostrarMensajeDeDadosSinPuntos(String nombre){
        ventanaJuego.msjSinPuntos(nombre);
    }

    //hbailitar/deshabilitar botones
    public void habilitarBotonesLanzarSolo(){
        ventanaJuego.habilitarBotonLanzar();
    }

    public void deshabilitarBotones(){
        ventanaJuego.deshabilitarBotonesTodos();
    }

    public void habilitarBotonesPlantarseOApartar(){
        ventanaJuego.habilitarBotonesPlantarseYApartar();
    }

    public void limpiarDadosMesa() {
        ventanaJuego.limpiar_dados_mesa();
    }
}
