package Controlador;

import Modelo.*;
import Vista.VistaGrafica;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
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
                    //si esto no aparece siempre el problema esta e
                    System.out.println("EVENTO DADOS_LANZADOS");
                    System.out.println("Jugador actual: " + juego.getJugadorActual().getNroJugador());
                    System.out.println("Dados parciales: " + juego.getJugadorActual().getDadosParciales().size());
                    ArrayList<Integer> valores = new ArrayList<>();
                    for(Dado d: juego.getJugadorActual().getDadosParciales()){
                        valores.add(d.getValorCaraSuperior());
                    }
                    int idJugador = juego.getJugadorActual().getNroJugador();
                    SwingUtilities.invokeLater(()->{
                        try{
                            vista.mostrarDados(valores, idJugador);
                        } catch (RuntimeException e) {
                            throw new RuntimeException(e);

                        }
                    });
                    break;
                case DADOS_CON_PUNTOS:
                    System.out.println("Entro a DADOS_CON_PUNTOS");
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        vista.habilitarBotonesPlantarseOApartar();
                    }
                    break;
                case DADOS_SIN_PUNTOS:
                    System.out.println("Entro a DADOS_SIN_PUNTOS");
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        System.out.println("case DADOS_SIN_PUNTOS >> deshabilitar botones");
                        vista.deshabilitarBotones();
                    }
                    System.out.println("case DADOS_SIN_PUNTOS >> mostrar mensaje dados sin puntos");
                    vista.mostrarMensajeDeDadosSinPuntos(juego.getJugadorActual().getNombreJugador());
                    break;
                case PUNTAJE_ACTUALIZADO:
                    System.out.println("Entro a PUNTAJE_ACTUALIZADO");
                    vista.agregarPuntajeRondaTabla(juego.getJugadorActual().getPuntajeParcial(), juego.getJugadorActual().getNombreJugador(), juego.getJugadorActual().getPuntajeTotal(), juego.getNroRonda());
                    //vista.limpiarDadosMesa();
                    break;
                case ACTUALIZACION_TURNO:
                    System.out.println("Entro a ACTUALIZAR TURNO");
                    System.out.println("ACTUALIZACION_TURNO >> mostrar turno jugador");
                    vista.limpiarDadosMesa();
                    vista.mostrarTurnoJugadorAdecuado(juego.nombreJugador());
                    System.out.println("ACTUALIZACION TURNO: limpiar mesa");
                    if (juego.getJugadorActual().getNroJugador() == nroJugador) {
                        System.out.println("ACTUALIZACION_TURNO >> vista habilitar boton lanzar");
                        vista.habilitarBotonesLanzarSolo();
                    } else {
                        System.out.println("ACTUALIZACION_TURNO >> deshabilitar todos los botones" +
                                "");
                        vista.deshabilitarBotones();
                    }
                    break;
                case ACTUALIZAR_DADOS_LANZADOS:
                    System.out.println("Entro a ACTUALIZAR DADOS LANZADOS");
                    ArrayList<Integer> v = new ArrayList<>(); //v = valores
                    for (Dado d : juego.getJugadorActual().getDadosParciales()) {
                        v.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDados(v, juego.getJugadorActual().getNroJugador());
                    break;
                case DADOS_APARTADOS:
                    System.out.println("Entro a DADOS_APARTADOS");
                    ArrayList<Integer> dadosA = new ArrayList<>();
                    for (Dado d : juego.getJugadorActual().getDadosApartados()) {
                        dadosA.add(d.getValorCaraSuperior());
                    }
                    vista.mostrarDadosApartados(dadosA);
                    if(juego.getJugadorActual().getNroJugador() == nroJugador){
                        vista.habilitarBotonesLanzarSolo();
                    }else{
                        vista.deshabilitarBotones();
                    }
                    break;
                case ESCALERA_OBTENIDA:
                    System.out.println("Entro a ESCALERA_OBTENIDA");
                    vista.mostrarMsjEscalera(juego.getJugadorActual().getNombreJugador());
                    vista.deshabilitarBotones();
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
