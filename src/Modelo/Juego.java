package Modelo;

import Serializador.AdministradorRanking;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static Modelo.Eventos.*;

public class Juego extends ObservableRemoto implements Serializable, IJuego {
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    private int siguiente_nroJ = -1;
    private Reglas reglas;
    private int nroRonda = 1;
    private EstadoJugada estadoJugada;
    private Cubilete cubilete;
    private AdministradorRanking admRanking = new AdministradorRanking();
    private ArrayList<Jugador> ranking;

    public Juego() {
        estadoJugada = EstadoJugada.ESPERANDO_JUGADORES;
        jugadores = new ArrayList<>();
        reglas = Reglas.getInstance();
        cubilete = new Cubilete();
        this.ranking = this.admRanking.cargarRanking(); //le pide al administrador de ranking que cargue el ranking(desde el archivo) y guarda el resultado en el atributo ranking
        //admRanking.vaciarRanking();
    }

    // inicializar y jugadores
    @Override
    public int siguienteNroJ() { //jugador.id
        siguiente_nroJ = siguiente_nroJ + 1;
        return siguiente_nroJ;
    }

    @Override
    public void iniciar_jugador(Jugador jugador) throws RemoteException {
        if(estadoJugada == EstadoJugada.ESPERANDO_JUGADORES){
            jugadores.add(jugador);
            notificarObservadores(JUGADOR_AGREGADO);
            if (jugadores.size() == 1) {
                jugadorActual = jugador;
            }
        }
    }

    @Override
    public void comenzarJuego() throws RemoteException {
        estadoJugada = EstadoJugada.JUEGO_ACTIVO;
        notificarObservadores(COMENZAR_JUEGO);
    }

    // en juego
    public void lanzar() throws RemoteException {
        ArrayList<Dado> resultados = cubilete.tirarse();
        jugadorActual.setDadosParciales(resultados);
        notificarObservadores(DADOS_LANZADOS);
        chequear_estado_tirada(); //cheqeuo en que estado estan mis dados
        analizar_estado_tirada(); //me fijo que hago con ese estado
    }

    private void chequear_estado_tirada() {
        estadoJugada = reglas.decime_el_estado(jugadorActual.getDadosParciales());
    }

    private void analizar_estado_tirada() throws RemoteException {
        switch (estadoJugada) {
            case TIENE_ESCALERA:
                jugadorActual.setPuntajeParcial(500);
                jugadorActual.setPuntajeTotal();
                notificarObservadores(Eventos.ESCALERA_OBTENIDA);
                notificarObservadores(PUNTAJE_ACTUALIZADO);
                actualizar_turno_jugador();
                break;
            case TIENE_DADOS_CON_PUNTOS:
                notificarObservadores(Eventos.DADOS_CON_PUNTOS); //habilito los botones de apartar o plantarse
                break;
            case TIENE_DADOS_SIN_PUNTOS:
                notificarObservadores(DADOS_SIN_PUNTOS); //msj perdio los puntos y actualizo turno
                jugadorActual.setPuntajeParcial(0);
                notificarObservadores(PUNTAJE_ACTUALIZADO);
                actualizar_turno_jugador();
                break;
        }
    }

    public void jugador_plantado() throws RemoteException {
        estadoJugada = EstadoJugada.SE_PLANTO;
        actualizar_parciales();
        jugadorActual.setPuntajeParcial(reglas.calcularPuntaje(jugadorActual.getDadosParciales()));
        jugadorActual.setPuntajeTotal();
        notificarObservadores(PLANTADO);
        notificarObservadores(PUNTAJE_ACTUALIZADO);
        actualizar_turno_jugador();
    }

    private void actualizar_parciales() {
        for (Dado d : jugadorActual.getDadosApartados()) {
            jugadorActual.getDadosParciales().add(d);
        }
    }

    public void apartar_dados() throws RemoteException {
        reglas = Reglas.getInstance();
        ArrayList<Dado> dadosConPuntos = reglas.obtenerDadosConPuntos(jugadorActual.getDadosParciales());
        for (Dado d : dadosConPuntos) {
            jugadorActual.setDadosApartados(d);
        }
        jugadorActual.getDadosParciales().clear();

        cubilete.actualizar_cubilete(jugadorActual.getDadosApartados());

        if (jugadorActual.getDadosApartados().size() == 5) {
            jugadorActual.setPuntajeParcial(
                    reglas.calcularPuntaje(jugadorActual.getDadosApartados())
            );
            jugadorActual.setPuntajeTotal();
            notificarObservadores(MAX_APARTADOS);
            notificarObservadores(PUNTAJE_ACTUALIZADO);
            actualizar_turno_jugador();
            return;
        }
        notificarObservadores(DADOS_APARTADOS);
    }

    public void actualizar_turno_jugador() throws RemoteException {
        cubilete.reestablecer_cubilete();
        jugadorActual.getDadosParciales().clear();
        jugadorActual.getDadosApartados().clear();

        if (jugadorActual.getPuntajeTotal() >= 10000) { //son 10.000 puntos, pongo menos para chequear cosas
            finalizar_partida(jugadorActual);
            return;
        }

        int i = jugadores.indexOf(jugadorActual);
        i++;
        i = i % jugadores.size();
        jugadorActual = jugadores.get(i);
        if (jugadores.getFirst() == jugadorActual) {
            nroRonda++; //solo incremento el nro de ronda cuando paso por de nuevo por el primer jugador.
        }
        notificarObservadores(ACTUALIZACION_TURNO);
    }

    // volver a jugar
    public void resetearJuego() throws RemoteException{
        jugadores.clear();
        jugadorActual = null;
        siguiente_nroJ=-1;
        nroRonda=1;
        estadoJugada=null;
        cubilete.reestablecer_cubilete();
        estadoJugada=EstadoJugada.ESPERANDO_JUGADORES;
    }

    // GETs
    public int getNroRonda() {
        return nroRonda;
    }

    @Override
    public Object[][] getTablaRanking() throws RemoteException {
        Object[][] datos = new Object[this.ranking.size()][3]; //3 columnas: nombre, fecha, puntajeGanador
        int i = 0;
        for (Jugador j : this.ranking) {
            datos[i][0] = j.getNombreJugador();
            datos[i][1] = j.getPuntajeTotal();
            datos[i][2] = j.getFechaJugado();
            i++;
        }
        return datos;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    @Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public EstadoJugada getEstadoJugada() {
        return estadoJugada;
    }

    // finalizar
    private void finalizar_partida(Jugador jugador) throws RemoteException {
        ranking.add(jugador);
        admRanking.guardarRanking(ranking);
        notificarObservadores(JUGADOR_GANADOR);
        resetearJuego();
    }

}
