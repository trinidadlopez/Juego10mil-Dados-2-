package Vista;

import Modelo.Jugador;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IVista {
    void mostrarMenuPrincipal();

    void mostrarReglas();

    void iniciar() throws RemoteException;

    void lobbyListo();

    void mostrarLobby();

    void actualizarLobby(ArrayList<Jugador> j) throws RemoteException;

    void iniciar_juego(String nombre, boolean esMiTurno) throws RemoteException;

    void mostrarDadosOtros(ArrayList<Integer> valores);

    void mostrarMisDados(ArrayList<Integer> valores);

    void habilitarBotonesPlantarseOApartar();

    void mensajeSePlanto(String nombre, int puntos) throws RemoteException;

    void agregarPuntajeRondaTabla(int puntaje, String nombre, int puntajeTotal, int ronda);

    void mostrarTablaPuntaje() throws RemoteException;

    void limpiarPantalla();

    void chequear_botones_y_turno(String nombre, boolean esMiTurno);

    //void mensajeDeGanador(String nombre, int puntos) throws RemoteException;

    void mensajeEscalera(String nombre) throws RemoteException;

    void mensajeDadosSinPuntos(String nombre) throws RemoteException;

    void solo_chequear_botones(boolean esMiTurno);

    void mostrarDadosApartados(ArrayList<Integer> d);

    void mostrarGanador(String nombre, int puntos);

    void limpiarTablaPuntaje();

    void mensajeMaxApartado(String nombre, int punto) throws RemoteException;

    void msjJugadorFuera() throws RemoteException;

}
