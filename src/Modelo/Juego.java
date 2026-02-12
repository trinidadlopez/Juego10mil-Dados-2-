package Modelo;

import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Eventos.*;

public class Juego extends ObservableRemoto implements Serializable, IJuego {
    private ArrayList<Jugador> jugadores;
    private Jugador ganador_parcial;
    private Jugador jugadorActual;
    private int siguiente_nroJ=-1;
    private Reglas reglas;
    private int nroRonda=1;
    private EstadoJugada estadoJugada;
    private Cubilete cubilete;


    public Juego(){
        jugadores = new ArrayList<>();
        reglas = Reglas.getInstance();
        cubilete = new Cubilete();
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
        if(jugadores.size() == 1){
            jugadorActual = jugador;
        }
        ganador_parcial = jugadores.getFirst();
    }

    @Override
    public void comenzarJuego() throws RemoteException{
        notificarObservadores(COMENZAR_JUEGO);
    }

    @Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void lanzar() throws RemoteException {
        System.out.println("Entro en lanzar()");

        ArrayList<Dado> resultados = cubilete.tirarse();
        jugadorActual.setDadosParciales(resultados);

        notificarObservadores(DADOS_LANZADOS);
        chequear_estado_tirada();
        analizar_estado_tirada();
    }

    private void chequear_estado_tirada(){
        estadoJugada = reglas.decime_el_estado(jugadorActual.getDadosParciales());
    }

    public void analizar_estado_tirada() throws RemoteException {
        switch (estadoJugada){
            case TIENE_ESCALERA:
                System.out.println("Entró a: TIENE_ESCALERA (en el JUEGO)");
                jugadorActual.setPuntajeParcial(500);
                jugadorActual.setPuntajeTotal();
                notificarObservadores(Eventos.ESCALERA_OBTENIDA);
                notificarObservadores(PUNTAJE_ACTUALIZADO);
                actualizar_turno_jugador();
                break;
            case TIENE_DADOS_CON_PUNTOS:
                System.out.println("Entró a: TIENE_DADOS_CON_PUNTOS (en el JUEGO)");
                notificarObservadores(Eventos.DADOS_CON_PUNTOS); //habilito los botones de apartar o plantarse
                break;
            case TIENE_DADOS_SIN_PUNTOS:
                System.out.println("Entró a: TIENE_DADOS_SIN_PUNTOS (en el JUEGO)");
                notificarObservadores(DADOS_SIN_PUNTOS); //msj perdio los puntos y actualizo turno
                jugadorActual.setPuntajeParcial(0);
                notificarObservadores(PUNTAJE_ACTUALIZADO);
                actualizar_turno_jugador();
                break;
        }
    }

    public void jugador_plantado() throws RemoteException {
        System.out.println("1)Entró a: jugador_plantado() (en el JUEGO)");
        estadoJugada = EstadoJugada.SE_PLANTO;
        actualizar_parciales();
        jugadorActual.setPuntajeParcial(reglas.calcularPuntaje(jugadorActual.getDadosParciales()));
        jugadorActual.setPuntajeTotal();
        notificarObservadores(PLANTADO);
        notificarObservadores(PUNTAJE_ACTUALIZADO);
        actualizar_turno_jugador();
    }

    private void actualizar_parciales(){
        for(Dado d: jugadorActual.getDadosApartados()){
            jugadorActual.getDadosParciales().add(d);
        }
    }

    public void apartar_dados() throws RemoteException{
        reglas = Reglas.getInstance();
        ArrayList<Dado> dadosConPuntos = reglas.obtenerDadosConPuntos(jugadorActual.getDadosParciales());
        for(Dado d: dadosConPuntos){
            System.out.println("dados con puntos que se van a apartar: " + d.getValorCaraSuperior());
            jugadorActual.setDadosApartados(d);
        }
        jugadorActual.getDadosParciales().clear();
        for(Dado d: jugadorActual.getDadosApartados()){
            System.out.println("lo que hay en jugadorActual.getDadosApartados: " + d.getValorCaraSuperior());
        }

        cubilete.actualizar_cubilete(jugadorActual.getDadosApartados());
        notificarObservadores(DADOS_APARTADOS);
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void actualizar_turno_jugador() throws RemoteException {
        cubilete.reestablecer_cubilete();
        jugadorActual.getDadosParciales().clear();
        jugadorActual.getDadosApartados().clear();

        if(jugadorActual.getPuntajeTotal() >= 10000){ //es 10.000, puse dos mil para probar
            notificarObservadores(JUGADOR_GANADOR);
            return;
        }

        int i = jugadores.indexOf(jugadorActual);
        i++;
        i=i%jugadores.size();
        jugadorActual=jugadores.get(i);
        if(jugadores.getFirst() == jugadorActual){
            nroRonda ++; //solo incremento el nro de ronda cuando paso por de nuevo por el primer jugador.
        }

        notificarObservadores(ACTUALIZACION_TURNO);
    }


    public int getNroRonda(){
        return nroRonda;
    }


}
