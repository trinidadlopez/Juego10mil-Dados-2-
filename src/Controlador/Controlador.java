package Controlador;

import Modelo.*;
import Vista.VistaGrafica;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    private IJuego juego;
    private VistaGrafica vista;
    int nroJugador;
    ArrayList<Dado> dadosApartados;
    private Jugador jugador;

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
                    vista.actualizarLobbyJugadores(juego.getJugadores());
                    if (juego.getJugadores().size() == 2) {
                        vista.iniciarTimerLobby();
                    }
                    break;
                case COMENZAR_JUEGO:
                    vista.ocultarLobby();
                    
                    vista.mostrarJuego();
                    vista.deshabilitarBotones(); //no deberia haber ningun boton habilitado pero con esto me aseguro
                    break;
                case DADOS_LANZADOS: //muestra los dados luego de presionar el btnLanzar
                    ArrayList<Integer> valores = new ArrayList<>();
                    for(Dado d: juego.getJugadorActual().getDadosParciales()){
                        valores.add(d.getValorCaraSuperior());
                    }
                    int idJugador = juego.getJugadorActual().getNroJugador();
                    vista.mostrarDados(valores, idJugador);
                    break;
                case DADOS_CON_PUNTOS:
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        System.out.println("Controlador: DADOS_CON_PUNTOS: habilita los botones PLANTARSE y APARTAR");
                        vista.habilitarBotonesPlantarseOApartar();
                    }
                    break;
                case DADOS_SIN_PUNTOS:
                    System.out.println("Controlador:DADOS SIN PUNTOS: deshabilita todos los botones");
                    vista.deshabilitarBotones();
                    //vista.mostrarMensajeDeDadosSinPuntos();
                    //vista.actualizarTurno();
                    break;
                case PUNTAJE_ACTUALIZADO:
                    vista.agregarPuntajeRondaTabla(juego.getJugadorActual().getPuntajeParcial(), juego.getJugadorActual().getNombreJugador(), juego.getJugadorActual().getPuntajeTotal(), juego.getNroRonda());
                    break;
                case ACTUALIZACION_TURNO:
                    System.out.println("Controlador:ACTUALIZACION TURNO: ...");
                    vista.mostrarTurnoJugadorAdecuado(juego.nombreJugador());
                    if (juego.getJugadorActual().getNroJugador() == nroJugador) {
                        System.out.println("Controlador:ACTUALIZACION TURNO: habilitar boton lanzar");
                        vista.habilitarBotonesLanzarSolo();
                    } else {
                        System.out.println("Controlador:ACXTUALIZACION TURNO: deshabilitar todos los botones");
                        vista.deshabilitarBotones();
                    }
                    break;
                case ACTUALIZAR_DADOS_LANZADOS:
                    ArrayList<Integer> v = new ArrayList<>(); //v = valores
                    for (Dado d : juego.getJugadorActual().getDadosParciales()) {
                        v.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDados(v, juego.getJugadorActual().getNroJugador());
                    break;
                case DADOS_APARTADOS:
                    ArrayList<Integer> dadosA = new ArrayList<>();
                    for (Dado d : juego.getJugadorActual().getDadosApartados()) {
                        dadosA.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDadosApartados(dadosA);
                    break;
                case ESCALERA_OBTENIDA:
                    vista.mostrarMsjEscalera();
                    System.out.println("Controlador:ESCALERA: deshabilitar todos los botones");
                    vista.deshabilitarBotones();
                    break;
                case RONDA_PERDIDA:
                    vista.mostrarMsjPuntosPerdidos();
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

    public void calcularPuntajeTabla() throws RemoteException{
        juego.calcularPuntaje();
    }

    public void apartarDados() throws RemoteException {
        System.out.println("CONTROLADOR llama a juego.apartar_dados()");
        juego.apartar_dados();
    }

    public void actualizar_turno() throws RemoteException {
        juego.actualizar_turno_jugador();
    }


    public String nombreJugadorVentana() throws RemoteException{
        for(Jugador j: juego.getJugadores()){
            if(j.getNroJugador() == nroJugador){
                return j.getNombreJugador();
            }
        }
        return "error";
    }

    public int getNroJugador() {
        return nroJugador;
    }
}
