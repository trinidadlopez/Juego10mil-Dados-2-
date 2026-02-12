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


    private int contar_puntos(ArrayList<Integer> dados_con_puntos){
        int respuesta=0;

        for(int i=0; i<dados_con_puntos.size();i++){
            switch(i){
                case 0://caso de 1
                    switch (dados_con_puntos.get(0)){
                        case 0:
                            respuesta=respuesta;
                        case 1:
                            respuesta=respuesta+100;
                            break;
                        case 2:
                            respuesta=respuesta+200;
                            break;
                        case 3:
                            respuesta=respuesta+1000;
                            break;
                        case 4:
                            respuesta=respuesta+1100;
                            break;
                        case 5:
                            respuesta=respuesta+10000;
                            break;
                    }
                    break;
                case 1: //caso de 2
                    switch (dados_con_puntos.get(1)){
                        case 3:
                            respuesta=respuesta+200;
                            break;
                        default:
                            respuesta=respuesta;
                            break;
                    }
                    break;
                case 2://caso de 3
                    switch (dados_con_puntos.get(2)){
                        case 3:
                            respuesta=respuesta+300;
                            break;
                        default:
                            respuesta=respuesta;
                            break;
                    }
                    break;
                case 3://caso de 4
                    switch (dados_con_puntos.get(3)){
                        case 3:
                            respuesta=respuesta+400;
                            break;
                        default:
                            respuesta=respuesta;
                            break;
                    }
                    break;
                case 4://caso de 5
                    switch (dados_con_puntos.get(4)){
                        case 0:
                            respuesta=respuesta;
                        case 1:
                            respuesta=respuesta+50;
                            break;
                        case 2:
                            respuesta=respuesta+100;
                            break;
                        case 3:
                            respuesta=respuesta+500;
                            break;
                        case 4:
                            respuesta=respuesta+550;
                            break;
                        case 5:
                            respuesta=respuesta+600;
                            break;
                    }
                    break;
                case 5://caso de 6
                    switch (dados_con_puntos.get(5)){
                        case 3:
                            respuesta=respuesta+600;
                            break;
                        default:
                            respuesta=respuesta;
                            break;
                    }
                    break;
            }
        }
        return respuesta;
    }
    public int calcularPuntaje(ArrayList<Dado> dados_con_puntos){
        ArrayList<Integer> cantidad_de_cada_numero = new ArrayList<>();
        int cantidad_uno=0;
        int cantidad_dos=0;
        int cantidad_tres=0;
        int cantidad_cuatro=0;
        int cantidad_cinco=0;
        int cantidad_seis=0;
        for(Dado d:dados_con_puntos){
            switch (d.getValorCaraSuperior()){
                case 1:
                    cantidad_uno++;
                    break;
                case 2:
                    cantidad_dos++;
                    break;
                case 3:
                    cantidad_tres++;
                    break;
                case 4:
                    cantidad_cuatro++;
                    break;
                case 5:
                    cantidad_cinco++;
                    break;
                case 6:
                    cantidad_seis++;
                    break;
            }
        }
        cantidad_de_cada_numero.add(cantidad_uno);
        cantidad_de_cada_numero.add(cantidad_dos);
        cantidad_de_cada_numero.add(cantidad_tres);
        cantidad_de_cada_numero.add(cantidad_cuatro);
        cantidad_de_cada_numero.add(cantidad_cinco);
        cantidad_de_cada_numero.add(cantidad_seis);
        return contar_puntos(cantidad_de_cada_numero);
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


    public ArrayList<Dado> obtenerDadosConPuntos(ArrayList<Dado> dadosParciales){
        ArrayList<Dado> dados_a_apartar=new ArrayList<>();
        int cantidad_dos_agregados=0;
        int cantidad_tres_agregados=0;
        int cantidad_cuatro_agregados=0;
        int cantidad_seis_agregados=0;
        for(Dado d: dadosParciales){
            switch (d.getValorCaraSuperior()){
                case 1:
                    dados_a_apartar.add(d);
                    break;
                case 2:
                    if(cantidad_caras(2,dadosParciales)==3){
                        dadosParciales.add(d);
                    }
                    else if(cantidad_caras(2,dadosParciales)>3){
                        if(cantidad_dos_agregados<3){
                            dados_a_apartar.add(d);
                            cantidad_dos_agregados++;
                        }
                    }
                    break;
                case 3:
                    if(cantidad_caras(3,dadosParciales)==3){
                        dadosParciales.add(d);
                    }
                    else if(cantidad_caras(3,dadosParciales)>3){
                        if(cantidad_tres_agregados<3){
                            dados_a_apartar.add(d);
                            cantidad_tres_agregados++;
                        }
                    }
                    break;
                case 4:
                    if(cantidad_caras(4,dadosParciales)==3){
                        dadosParciales.add(d);
                    }
                    else if(cantidad_caras(4,dadosParciales)>3){
                        if(cantidad_cuatro_agregados<3){
                            dados_a_apartar.add(d);
                            cantidad_cuatro_agregados++;
                        }
                    }
                    break;
                case 5:
                    dados_a_apartar.add(d);
                    break;
                case 6:
                    if(cantidad_caras(6,dadosParciales)==3){
                        dadosParciales.add(d);
                    }
                    else if(cantidad_caras(6,dadosParciales)>3){
                        if(cantidad_seis_agregados<3){
                            dados_a_apartar.add(d);
                            cantidad_seis_agregados++;
                        }
                    }
                    break;
            }
        }
        return dados_a_apartar;
    }
    private int cantidad_caras(int cara, ArrayList<Dado> dados){
        int cantidad_caras=0;
        for(Dado d: dados){
            if(d.getValorCaraSuperior()==cara){
                cantidad_caras++;
            }
        }
        return cantidad_caras;
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

    private boolean tieneDadosConPuntos(ArrayList<Dado> dadosParciales){ //con esto puedo chequear que si el usuario me pide seguir, pueda.
        int[] conteo = cantidadDados(dadosParciales);
        if(tieneEscalera(conteo) || tieneTrio(conteo) || conteo[1]>0 || conteo[5]>0){
            return true;
        }
        return false;
    }

    private boolean chequeo_escalera(ArrayList<Dado> dados){
        int[] conteo= cantidadDados(dados);
        return tieneEscalera(conteo);
    }

    public EstadoJugada decime_el_estado(ArrayList<Dado> dadosParciales){
        if(chequeo_escalera(dadosParciales) == true){
            return EstadoJugada.TIENE_ESCALERA;
        } else if (tieneDadosConPuntos(dadosParciales)) {
            return EstadoJugada.TIENE_DADOS_CON_PUNTOS;
        } else  {
            return EstadoJugada.TIENE_DADOS_SIN_PUNTOS;
        }
    }
}

