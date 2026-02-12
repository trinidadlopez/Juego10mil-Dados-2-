/*package Vista;

import Controlador.Controlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VReglas extends JFrame {
    private JPanel panel;
    private JTextArea txt;

    public VReglas() {
        this.panel = new JPanel();
        this.txt = new JTextArea();

        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setBounds(100, 100, 300, 300);
        setContentPane(panel);
        setTitle("Juego 10mil - Reglas");
        txt.setEditable(false);
        setVisible(true);

        txt.append("REGLAS DEL JUEGO 10 MIL - DADOS\n" +
                "---------------------------\n"+
                "Objetivo del Juego\n" +
                "---------------------------\n"+

                "Para sumar puntos cada jugador/a debera obtener puntos a traves de diferentes combinaciones en su turno.\n" +
                "\n" +
                "Gana el jugador/a que llegue primero a 10mil puntos!\n" +
                "-------------------------------\n"+

                "Cómo sumar puntos?:\n" +

                "\n" +
                "Para sumar puntos deberas obtener en cada lanzamientos dados o combinaciones que te ayuden a sumar puntos.\n" +
                "\n" +
                "Los dados con el numero suman cada uno 100 puntos y los dados con el numero 5 suman 50 puntos cada uno.\n" +
                "Si obtenes tres dados iguales del mismo valor, se suma como una centena.\n" +
                "Por ejemplo, tres dados iguales con el numero cuatro suma 400 puntos y tres iguales con el numero dos suman 200 puntos.\n" +
                "La unica expecion es el numero uno porque al obtenes tres dados iguales se suman 1000 puntos.\n" +
                "-----------------------------\n"+
                "En caso de con seguir una escalera el jugador sumara 500 puntos.\n" +


                "\n" +
                "TABLA DE PUTOS:\n" +
                "\n" +
                "- 50 puntos con cada numero 5 \n"+
                "- 100 puntos con cada numero 1 \n"+
                "- 200 puntos con tres numeros 2 \n"+
                "- 300 puntos con tres numeros 3 \n"+
                "- 400 puntos con tres numeros 4 \n"+
                "- 500 puntos con tres numeros 5 \n"+
                "- 500 puntos con escalera (1,2,3,4,5 o 2,3,4,5,6 o 3,4,5,6,1) \n"+
                "- 1000 puntos con tres numeros 1 \n" +


                "-----------------------------\n"+
                "Dinamica del Juego\n" +
                "-----------------------------\n"+

                "Un jugador/ lanza los dados: el jugador decide en cada turno si se planta y conserva lo acumulado, \n" +
                "\n" +
                "o si arriesga un nuevo lanzamiento con los dados restantes. Si en un lanzamiento tiene puntos, estos se suman\n" +
                "\n" +
                "al total acumulado. Pero si en un lanzamiento no aparece ni un 1 ni un 5 o alguna combinacion, pierde el turno\n"+
                "\n" +
                "y los puntos obtenidos en esa ronda.\n"+
                "\n" +
                "yLa estatregia está en elegir entre asegurar lo ganado o seguir arriesgando.\n"+
                "\n" +
                "En ocasiones, un buen lanzamiento puede disparar el puntaje (como obtener tres 1 y sumar 1000 puntos),\n"+
                "\n" +
                "pero siempre con el riesgo de perderlo todo si no salen ni un 1 ni un 5 o combinaciones.\n"+


                "---------------------------\n"+

                "Fin del Juego\n" +
                "----------------------\n"+

                "El juego termina cuando algun jugador llega a 10mil puntos\n");

        txt.setCaretPosition(0);
    }

}
*/