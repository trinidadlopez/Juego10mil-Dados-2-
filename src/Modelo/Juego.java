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
    private int indiceJugadorActual = 0;


    public Juego(){
        jugadores = new ArrayList<>();
        reglas = Reglas.getInstance();
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
        for(Jugador j: jugadores){
        }
        if(jugadores.size() == 1){
            jugadorActual = jugador;
            //actualizar_turno_jugador();
            //System.out.println("MODELO: iniciarJugador, actualizacion turno pero no notifica");

        }
    }

    @Override
    public void comenzarJuego() throws RemoteException{
        notificarObservadores(COMENZAR_JUEGO);
        notificarObservadores(ACTUALIZACION_TURNO);
    }

    @Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void lanzar() throws RemoteException {
        System.out.println("Entro en lanzar()");
        Cubilete cubilete = jugadorActual.getCubilete();
        //si está vacío, arrancá con los dados del cubilete
        if (jugadorActual.getDadosParciales() == null || jugadorActual.getDadosParciales().isEmpty()) {
            jugadorActual.setDadosParciales(new ArrayList<>(cubilete.getDados()));
        }
        ArrayList<Dado> resultados = new ArrayList<>();
        for (Dado dado : jugadorActual.getDadosParciales()) {
            dado.tirarse();
            resultados.add(dado);
        }
        jugadorActual.setDadosParciales(resultados);
        ArrayList<Integer> valores = new ArrayList<>();
        for(Dado d: resultados){
            valores.add(d.getValorCaraSuperior());
        }
        notificarObservadores(DADOS_LANZADOS);

        //ESCALERA
        if (reglas.chequeo_escalera(jugadorActual.getDadosParciales())) {
            jugadorActual.setPuntajeParcial(500);
            reglas.sumarPuntaje(500, jugadorActual);
            notificarObservadores(Eventos.ESCALERA_OBTENIDA); //msj escalera obtenida, muestra y suma los puntos,avanza el turno.
            notificarObservadores(PUNTAJE_ACTUALIZADO); //agrega puntaje a la tabla de puntajes y la muestra
            actualizar_turno_jugador();
            return;
        }
        //TIENE DADOS_CON_PUNTOS
        if(reglas.tieneDadosConPuntos(jugadorActual.getDadosParciales())) {
            jugadorActual.setPuntajeParcial(reglas.calcularPuntaje(jugadorActual.getDadosParciales()));
            notificarObservadores(Eventos.DADOS_CON_PUNTOS); //habilito los botones de apartar o plantarse
            return;
        }
        if(jugadorActual.getDadosApartados().size()>0){
            notificarObservadores(DADOS_SIN_PUNTOS); //msj perdio los puntos y actualizo turno
            jugadorActual.setPuntajeParcial(0);
            notificarObservadores(PUNTAJE_ACTUALIZADO);
            // limpiar estado antes de pasar turno
            jugadorActual.getDadosApartados().clear();
            jugadorActual.setDadosParciales(new ArrayList<>(jugadorActual.getCubilete().getDados()));

            actualizar_turno_jugador();
            return;
        }
        notificarObservadores(Eventos.DADOS_SIN_PUNTOS);
        notificarObservadores(PUNTAJE_ACTUALIZADO); //agrega puntaje a la tabla de puntajes y la muestra{
        // limpiar estado antes de pasar turno
        jugadorActual.getDadosApartados().clear();
        jugadorActual.setDadosParciales(new ArrayList<>(jugadorActual.getCubilete().getDados()));

        actualizar_turno_jugador();
    }


    public int calcularPuntajeDados() throws RemoteException{
        int rta = reglas.calcularPuntaje(jugadorActual.getCubilete().getDados());
        return rta;
    }

    public int calcularPuntajeTotal() throws RemoteException{
        int puntajeParcial = calcularPuntajeDados();
        reglas.sumarPuntaje(puntajeParcial, jugadorActual);
        int puntajeT = jugadorActual.getPuntajeTotal();
        return puntajeT;
    }

    public void apartar_dados() throws RemoteException{
        ArrayList<Dado> dados = jugadorActual.getDadosParciales();
        reglas = Reglas.getInstance();

        ArrayList<Dado> dadosConPuntos = new ArrayList<>(reglas.obtenerDadosConPuntos(jugadorActual.getDadosParciales()));

        for(Dado d: dadosConPuntos){
            dados.remove(d);
            jugadorActual.setDadosApartados(d);
        }

        //notificarObservadores(Eventos.ACTUALIZAR_DADOS_LANZADOS);
        notificarObservadores(DADOS_APARTADOS);

    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void actualizar_turno_jugador() throws RemoteException {
        // limpiar al jugador que termina
        jugadorActual.getDadosApartados().clear();
        jugadorActual.getDadosParciales().clear();


        int i = jugadores.indexOf(jugadorActual);
        i++;
        i=i%jugadores.size();
        jugadorActual=jugadores.get(i);

        jugadorActual.getDadosApartados().clear(); // limpiar apartados
        jugadorActual.getDadosParciales().clear();

        //ACTUALIZAR NUMERO DE RONDA PARA QUE SE ACTUALICE EN LA TABLA
        indiceJugadorActual++;
        if(indiceJugadorActual >= jugadores.size()){
            indiceJugadorActual = 0;
            nroRonda ++; //solo incremento el nro de ronda cuando paso por todos los jugadores.
        }notificarObservadores(ACTUALIZACION_TURNO);
    }

    public void calcularPuntaje() throws RemoteException{
        int rtaPuntaje = calcularPuntajeDados();
        int puntajeTotal = calcularPuntajeTotal();
        Jugador j = getJugadorActual();

        j.setPuntajeParcial(rtaPuntaje);
        j.setPuntajeTotal(puntajeTotal);

        notificarObservadores(PUNTAJE_ACTUALIZADO);
    }

    public String nombreJugador() throws RemoteException {
        int nroActual = getJugadorActualNro();

        for(Jugador j: getJugadores()){
            if(j.getNroJugador() == nroActual){
                return j.getNombreJugador();
            }
        }
        return "error";
    }

    public int getNroRonda(){
        return nroRonda;
    }

    public int getJugadorActualNro(){
        return jugadorActual.getNroJugador();
    }

}
