package Controlador;

import Modelo.*;
import Vista.Mensajes;
import Vista.VJuego;
import Vista.VistaGrafica;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    IJuego juego;
    VistaGrafica vista;
    int nroJugador;
    ArrayList<Dado> dadosApartados;
    private Jugador jugador;
    EstadoInterfaz estadoIz;
    private ArrayDeque<Eventos> cola;

    public Controlador() {
        estadoIz = EstadoInterfaz.NORMAL;
        cola = new ArrayDeque<>();
    }

    public void setVista(VistaGrafica vista){
        this.vista = vista;
    }

    public void procesar_eventos_pendientes() throws RemoteException {
        estadoIz = EstadoInterfaz.NORMAL;
        while(!cola.isEmpty() && estadoIz==EstadoInterfaz.NORMAL){
            Eventos evento = cola.poll();
            procesar_evento(evento);
        }
    }

    private void procesar_evento(Eventos evento) throws RemoteException {
        switch (evento){
            case ACTUALIZACION_TURNO:
                vista.mensajeUniversal("Turno del jugador/a: " + juego.getJugadorActual().getNombreJugador());
                if (juego.getJugadorActual().getNroJugador() == nroJugador) {
                    vista.habilitarBotonesLanzarSolo();
                } else {
                    vista.deshabilitarBotones();
                }
                break;
            case PUNTAJE_ACTUALIZADO:
                vista.mostrarTablaPuntaje();
                break;
            case DADOS_LANZADOS:
                ArrayList<Integer> valores = new ArrayList<>();
                for (Dado d : juego.getJugadorActual().getDadosParciales()){
                    valores.add(d.getValorCaraSuperior());
                }
                int idJugador = juego.getJugadorActual().getNroJugador();
                if (idJugador == getNroJugador()) {
                    vista.mostrarMisDados(valores);
                } else {
                    vista.mostrarDadosOtros(valores);
                }
                break;
        }
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
                    vista.actualizarLobbyJugadores(juego.getJugadores());
                    if (juego.getJugadores().size() == 2) {
                        vista.iniciarTimerLobby();
                    }
                    break;
                case COMENZAR_JUEGO:
                    vista.ocultarLobby();
                    vista.mostrarJuego();
                    vista.mensajeUniversal("Turno del jugador/a: " + juego.getJugadorActual().getNombreJugador());
                    if(juego.getJugadorActual().getNroJugador()==nroJugador){
                        vista.habilitarBotonesLanzarSolo();
                    }else {
                        vista.deshabilitarBotones();
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
                        vista.mostrarTablaPuntaje();
                    }
                    break;
                case ACTUALIZACION_TURNO:
                    vista.limpiarDadosMesa();
                    if(estadoIz!=EstadoInterfaz.NORMAL){
                        cola.add(Eventos.ACTUALIZACION_TURNO);
                    }
                    else{
                        vista.mensajeUniversal("Turno del jugador/a: " + juego.getJugadorActual().getNombreJugador());
                        if (juego.getJugadorActual().getNroJugador() == nroJugador) {
                            vista.habilitarBotonesLanzarSolo();
                        }else {
                            vista.deshabilitarBotones();
                        }
                    }
                    break;
                case DADOS_APARTADOS:
                    ArrayList<Integer> dadosA = new ArrayList<>();
                    for (Dado d : juego.getJugadorActual().getDadosApartados()) {
                        dadosA.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDadosApartados(dadosA);
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        vista.habilitarBotonesLanzarSolo();
                    }else {
                        vista.deshabilitarBotones();
                    }
                    break;
                case ESCALERA_OBTENIDA:
                    manejarEscalera();
                    break;
                case PLANTADO:
                    estadoIz = EstadoInterfaz.MOSTRANDO_MSJ;
                    System.out.println("2)Entró a: case PLANTADO (en el CONTROLADOR)");
                    vista.mensajeUniversal(juego.getJugadorActual().getNombreJugador() + " se plantó, suma " + juego.getJugadorActual().getPuntajeParcial() + "puntos.");
                    break;
                case JUGADOR_GANADOR:
                    vista.mensajeDeGanador(" ¡JUGADOR GANADOR! " + juego.getJugadorActual().getNombreJugador() + " llegó/pasó los 10.000 puntos. Juego finalizado.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public void plantarse() throws RemoteException {
        juego.jugador_plantado();
    }

    public void iniciarJugador(String nombre) throws RemoteException{
        if(dadosApartados==null){
            dadosApartados = new ArrayList<>();
        }
        jugador = new Jugador(nombre, dadosApartados);
        jugador.setNroJugador(juego.siguienteNroJ());
        nroJugador=jugador.getNroJugador();
        juego.iniciar_jugador(jugador);
    }

    public void comenzarJuego() throws RemoteException{
        juego.comenzarJuego();
    }

    public void lanzar_dados() throws RemoteException { //primer lanzamiento
        juego.lanzar();
    }

    public void apartarDados() throws RemoteException {
        juego.apartar_dados();
    }

    public String nombreJugadorVentana() throws RemoteException{
        for(Jugador j: juego.getJugadores()){
            if(j.getNroJugador() == nroJugador){
                return j.getNombreJugador();
            }
        }
        return "";
    }

    public void terminarJuego() throws RemoteException{
        System.exit(0);
    }

    public int getNroJugador() {
        return nroJugador;
    }

    public void manejarEscalera() throws RemoteException {
        vista.deshabilitarBotones();
        vista.mensajeUniversal(juego.getJugadorActual().getNombreJugador() + "Obtuvo escalera! +500 puntos. Turno finalizado");
        estadoIz = EstadoInterfaz.MOSTRANDO_ESCALERA;
    }

    public void manejarDadosSinPuntos() throws RemoteException {
        vista.deshabilitarBotones();
        vista.mensajeUniversal("¡Dados sin puntos! En esta ronda " + juego.getJugadorActual().getNombreJugador() + " no suma puntos");
        estadoIz = EstadoInterfaz.MOSTRANDO_DADOS_SIN_PUNTOS;
    }
}
