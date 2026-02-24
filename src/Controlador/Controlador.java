package Controlador;

import Modelo.*;
import Vista.IVista;
import Vista.grafica.VistaGrafica;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    IJuego juego;
    IVista vista;
    int nroJugador;
    ArrayList<Dado> dadosApartados;
    private Jugador jugador;
    EstadoInterfaz estadoIz;
    private ArrayDeque<Eventos> cola;

    public Controlador() {
        estadoIz = EstadoInterfaz.NORMAL;
        nroJugador=10000;
        cola = new ArrayDeque<>();
    }

    public void iniciarJugador(String nombre) throws RemoteException{
        jugador = new Jugador(nombre, dadosApartados);
        jugador.setNroJugador(juego.siguienteNroJ());
        nroJugador=jugador.getNroJugador();
        if(juego.getEstadoJugada()!=EstadoJugada.ESPERANDO_JUGADORES){
            terminarJuego();
        }
        else{
            if(dadosApartados==null){
                dadosApartados = new ArrayList<>();
            }
            juego.iniciar_jugador(jugador);
        }

    }

    public void comenzarJuego() throws RemoteException{
        juego.comenzarJuego();
    }

    public void procesar_eventos_pendientes() throws RemoteException {
        estadoIz = EstadoInterfaz.NORMAL;
        while(!cola.isEmpty() && estadoIz == EstadoInterfaz.NORMAL){
            Eventos evento = cola.poll();
            procesar_evento(evento);
        }
    }

    private void procesar_evento(Eventos evento) throws RemoteException {
        switch (evento){
            case ACTUALIZACION_TURNO:
                estadoIz = EstadoInterfaz.ACTUALIZANDO_EL_TURNO;
                boolean es_mi_turno = juego.getJugadorActual().getNroJugador()==nroJugador;
                vista.chequear_botones_y_turno(juego.getJugadorActual().getNombreJugador(), es_mi_turno);
                break;
            case PUNTAJE_ACTUALIZADO:
                estadoIz=EstadoInterfaz.MOSTRANDO_PUNTAJE;
                vista.mostrarTablaPuntaje();
                break;
            case DADOS_LANZADOS: //chequear si funciona asi o si no lo bloqueo
                ArrayList<Integer> valores = new ArrayList<>();
                for (Dado d : juego.getJugadorActual().getDadosParciales()){
                    valores.add(d.getValorCaraSuperior());
                }
                int idJugador = juego.getJugadorActual().getNroJugador();
                if (idJugador == nroJugador) {
                    vista.mostrarMisDados(valores);
                } else {
                    vista.mostrarDadosOtros(valores);
                }
                break;
        }
    }

    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object o) throws RemoteException {
        try{
            Eventos evento = (Eventos) o;
            switch (evento){
                case JUGADOR_AGREGADO:
                    vista.actualizarLobby(juego.getJugadores());
                    if (juego.getJugadores().size() == 2 && nroJugador==0) {
                        vista.lobbyListo();
                    }
                    break;
                case COMENZAR_JUEGO:
                    boolean rta = false;
                    System.out.println("Mi numero de jugadro es: "+nroJugador);
                    for(Jugador j: juego.getJugadores()){
                        System.out.println(j.getNombreJugador());
                        if(j.getNroJugador() == nroJugador){
                            rta=true;
                        }
                    }
                    if(rta==false){
                        System.out.println("Entro al if de rta==false");
                        terminarJuego();
                    }
                    else{
                        System.out.println("Entro al else de rta == true");
                        boolean esMiTurno = juego.getJugadorActual().getNroJugador()==nroJugador;
                        vista.iniciar_juego(juego.getJugadorActual().getNombreJugador(), esMiTurno);
                    }
                    break;
                case DADOS_LANZADOS: //chequear si funciona asi o si no lo bloqueo
                    ArrayList<Integer> valores = new ArrayList<>();
                    for (Dado d : juego.getJugadorActual().getDadosParciales()){
                        valores.add(d.getValorCaraSuperior());
                    }
                    int idJugador = juego.getJugadorActual().getNroJugador();
                    if (idJugador == nroJugador) {
                        vista.mostrarMisDados(valores);
                    } else {
                        vista.mostrarDadosOtros(valores);
                    }
                    break;
                case DADOS_CON_PUNTOS:
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        vista.habilitarBotonesPlantarseOApartar();
                    }
                    break;
                case DADOS_SIN_PUNTOS:
                    manejarDadosSinPuntos();
                    break;
                case PUNTAJE_ACTUALIZADO:
                    vista.agregarPuntajeRondaTabla(juego.getJugadorActual().getPuntajeParcial(), juego.getJugadorActual().getNombreJugador(), juego.getJugadorActual().getPuntajeTotal(), juego.getNroRonda());
                    if(estadoIz!=EstadoInterfaz.NORMAL){
                        cola.add(Eventos.PUNTAJE_ACTUALIZADO);
                    }else {
                        estadoIz=EstadoInterfaz.MOSTRANDO_PUNTAJE;
                        vista.mostrarTablaPuntaje();
                    }
                    break;
                case ACTUALIZACION_TURNO:
                    if(estadoIz!=EstadoInterfaz.NORMAL){
                        cola.add(Eventos.ACTUALIZACION_TURNO);
                    }
                    else{
                        estadoIz = EstadoInterfaz.ACTUALIZANDO_EL_TURNO;
                        boolean es_mi_turno = juego.getJugadorActual().getNroJugador()==nroJugador;
                        vista.chequear_botones_y_turno(juego.getJugadorActual().getNombreJugador(), es_mi_turno);
                    }
                    break;
                case DADOS_APARTADOS:
                    ArrayList<Integer> dadosA = new ArrayList<>();
                    for (Dado d : juego.getJugadorActual().getDadosApartados()) {
                        dadosA.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDadosApartados(dadosA);
                    boolean es_mi_turno = juego.getJugadorActual().getNroJugador()==nroJugador;
                    vista.solo_chequear_botones(es_mi_turno);
                    break;
                case ESCALERA_OBTENIDA:
                    manejarEscalera();
                    break;
                case PLANTADO:
                    estadoIz = EstadoInterfaz.MOSTRANDO_MSJ;
                    vista.mensajeSePlanto(juego.getJugadorActual().getNombreJugador() ,juego.getJugadorActual().getPuntajeParcial() );
                    break;
                case JUGADOR_GANADOR:
                    vista.mostrarGanador(juego.getJugadorActual().getNombreJugador() , juego.getJugadorActual().getPuntajeTotal());
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //SETs
    public void setVista(IVista vista){
        this.vista = vista;
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.juego= (IJuego) t;
    }

    // juego.algo
    public void plantarse() throws RemoteException {
        juego.jugador_plantado();
    }

    public void lanzar_dados() throws RemoteException { //primer lanzamiento
        juego.lanzar();
    }

    public void apartarDados() throws RemoteException {
        juego.apartar_dados();
    }

    //manejar
    public void manejarEscalera() throws RemoteException {
        estadoIz = EstadoInterfaz.MOSTRANDO_ESCALERA;
        vista.mensajeEscalera(juego.getJugadorActual().getNombreJugador());
    }

    public void manejarDadosSinPuntos() throws RemoteException {
        estadoIz = EstadoInterfaz.MOSTRANDO_DADOS_SIN_PUNTOS;
        vista.mensajeDadosSinPuntos(juego.getJugadorActual().getNombreJugador());
    }

    //GET
    public Object[][] getTablaRanking() throws RemoteException {
        return juego.getTablaRanking();
    }

    // finalizar juego
    public void terminarJuego() throws RemoteException{
        juego.removerObservador(this);
        System.exit(0);
    }

    //otros
    public void volverAJugar() throws RemoteException{
        vista.limpiarTablaPuntaje();
        vista.mostrarMenuPrincipal();
    }

    public ArrayList<Jugador> jugadoresLobby() throws RemoteException {
        return juego.getJugadores();
    }

    public String nombreJugadorVentana() throws RemoteException{
        for(Jugador j: juego.getJugadores()){
            if(j.getNroJugador() == nroJugador){
                return j.getNombreJugador();
            }
        }
        return "";
    }
}
