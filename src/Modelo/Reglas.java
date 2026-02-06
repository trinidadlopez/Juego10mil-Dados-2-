package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Reglas {
    private static Reglas instance; //Permite que la clase controle cuantas instancias existen

    Reglas() {
    }

    public static Reglas getInstance(){
        if(instance==null){
            instance=new Reglas();
        }
        return instance;
    }
    public int[] cantidadDados(ArrayList<Dado> dados){ //cuenta la cantidad de dados que hay de cada numero
        int[] conteo = new int[7];
        for(Dado d : dados){
            int valorDado = d.getValorCaraSuperior();
            conteo[valorDado]++;
        }
        return conteo;
    }

    public boolean tieneTrio(int[] conteo){ //chequeo si tiene trio de alguno de los numeros
        for(int i=1; i <= 6; i++ ){
            if(conteo[i]>=3){
                return true;
            }
        }
        return false;
    }

    public boolean tieneEscalera(int[] conteo){ //chequeo si tiene escalera
        boolean escalera1a5 = true; //chequeo escalera: 1,2,3,4,5
        for(int i=1; i <= 5; i++){
            if(conteo[i] != 1){
                escalera1a5=false;
            }
        }
        if(escalera1a5){
            return true;
        }

        boolean escalera2a6 = true; //chequeo escalera: 2,3,4,5,6
        for(int i=2; i <= 6; i++){
            if(conteo[i] != 1){
                escalera2a6=false;
            }
        }
        if(escalera2a6){
            return true;
        }

        if(conteo[1]==1 && conteo[3]==1 && conteo[4]==1 && conteo[5]==1 && conteo[6]==1){ //chequeo 3,4,5,6,1
            return true;
        }

        return false;

    }


    public int calcularPuntaje(ArrayList<Dado> dadosApartados){
        int[] conteo = cantidadDados(dadosApartados);
        int puntos=0;

        if(conteo[1]==5){
            return 10000;
        }

        if(tieneEscalera(conteo)){
            return 500;
        }

        for(int i=1; i<=6; i++){
            if(conteo[i]>=3){
                if(i==1){
                    puntos += 1000;
                }else{
                    puntos += i*100;
                }
                conteo[i]-=3;
            }
            if(i==5){
                puntos += conteo[i]*50;
            }else if(i==1){
                puntos += conteo[i]*100;
            }
        }
        return puntos;
    }

    public void sumarPuntaje(int puntajeParcial, Jugador jugadorActual){
        int puntajeActual=jugadorActual.getPuntajeTotal();
        jugadorActual.setPuntajeTotal(puntajeActual+puntajeParcial);
    }

    public Jugador determinar_quien_gano(ArrayList<Jugador> jugadores){
        Jugador ganador=jugadores.get(0);
        for(Jugador j : jugadores){
            if(j.getPuntajeTotal()>ganador.getPuntajeTotal()){
                ganador=j;
            }
        }
        return ganador;
    }

    public ArrayList<Dado> obtenerDadosConPuntos(ArrayList<Dado> dadosParciales){ //con esto puedo chequear que si el usuario me pide seguir, pueda.
        ArrayList<Dado> dadosConPuntos = new ArrayList<>();
        int[] conteo = cantidadDados(dadosParciales);

        //chequeo escalera
        if(tieneEscalera(conteo)){
            dadosConPuntos.addAll(dadosParciales);
            return dadosConPuntos;
        }
        //chequeo trio
        if(tieneTrio(conteo)) {
            // identifico qué número forma el trío
            int numeroTrio = -1;
            for (int i = 1; i <= 6; i++) {
                if (conteo[i] >= 3) {
                    numeroTrio = i;
                    break;
                }
            }

            for (Dado d : dadosParciales) {
                if (d.getValorCaraSuperior() == numeroTrio) {
                    dadosConPuntos.add(d);
                }
            }
        }

            //chequeo individuales (1 y 5)
        for (Dado d : dadosParciales) {
            if (d.getValorCaraSuperior() == 1 || d.getValorCaraSuperior() == 5) {
                dadosConPuntos.add(d);
            }
        }

        return dadosConPuntos;
    }


    public boolean verificar_si_puede_apartar(ArrayList<Dado> dadosApartados){
        int[] cantidad= cantidadDados(dadosApartados);

        for (Dado dadosApartado : dadosApartados) {
            int n = dadosApartado.getValorCaraSuperior();
            if ((n != 5 && n != 1 && cantidad[n] != 3)) {
                return false;
            }
        }
        return true;
    }

    public boolean tieneDadosConPuntos(ArrayList<Dado> dadosParciales){ //con esto puedo chequear que si el usuario me pide seguir, pueda.
        int[] conteo = cantidadDados(dadosParciales);
        if(tieneEscalera(conteo) || tieneTrio(conteo) || conteo[1]>0 || conteo[5]>0){
            return true;
        }
        return false;
    }

    public boolean chequeo_escalera(ArrayList<Dado> dados){
        int[] conteo= cantidadDados(dados);
        return tieneEscalera(conteo);
    }
}

