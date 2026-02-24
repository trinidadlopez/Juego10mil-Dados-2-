package Vista.grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VReglas extends JFrame {
    private JPanel panel;
    private JTextArea txt;
    private JButton btnVolver;
    private VistaGrafica vista;

    public VReglas(VistaGrafica vista) {
        inicializar();
        this.vista=vista;
    }

    private void inicializar(){
        this.panel = new JPanel(new BorderLayout());
        this.txt = new JTextArea();

        setSize(500, 500); //x: posicion horizontal(pantalla), y: posicion vertical(pantalla), width: ancho, height: alto
        setContentPane(panel);
        setLocationRelativeTo(null);
        setTitle("Juego 10mil - Reglas");
        txt.setLineWrap(true); //activo el salto de linea automatico cuando llega al borde derecho
        txt.setWrapStyleWord(true); //hace que el salto de linea sea por palabra completa y no se corte en el medio
        txt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txt);
        panel.add(scrollPane, BorderLayout.CENTER);
        setVisible(false);
        txt.setCaretPosition(0); //mueve el cursor al principio del texto asi arranca a mostrarse desde ahi
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14)); //(nombre, estilo, tamaño)
        btnVolver = new JButton("Volver");
        panel.add(btnVolver, BorderLayout.SOUTH);

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                vista.mostrarMenuPrincipal();
            }
        });

        txt.append("REGLAS DEL JUEGO 10 MIL - DADOS\n" +
                "\n" +

                "---------------------------\n"+
                "Objetivo del Juego\n" +
                "---------------------------\n"+
                "Para sumar puntos cada jugador/a debera obtener puntos a traves de diferentes combinaciones en su turno.\n" +
                "Gana el jugador/a que llegue primero a 10mil puntos!\n" +
                "\n"+

                "-------------------------------\n"+
                "Cómo sumar puntos?:\n" +
                "-------------------------------\n"+

                "Para sumar puntos deberas obtener en cada lanzamientos dados o combinaciones que te ayuden a sumar puntos.\n" +
                "\n" +
                "Los dados con el numero suman cada uno 100 puntos y los dados con el numero 5 suman 50 puntos cada uno.\n" +
                "Si obtenes tres dados iguales del mismo valor, se suma como una centena.\n" +
                "Por ejemplo, tres dados iguales con el numero cuatro suma 400 puntos y tres iguales con el numero dos suman 200 puntos.\n" +
                "La unica expecion es el numero uno porque al obtenes tres dados iguales se suman 1000 puntos.\n" +
                "\n" +
                "En caso de con seguir una escalera el jugador sumara 500 puntos.\n" +
                "\n"+

                "-------------------------------\n"+
                "TABLA DE PUNTOS:\n" +
                "-------------------------------\n"+
                "- 50 puntos con cada numero 5 \n"+
                "- 100 puntos con cada numero 1 \n"+
                "- 200 puntos con tres numeros 2 \n"+
                "- 300 puntos con tres numeros 3 \n"+
                "- 400 puntos con tres numeros 4 \n"+
                "- 500 puntos con tres numeros 5 \n"+
                "- 500 puntos con escalera (1,2,3,4,5 o 2,3,4,5,6 o 3,4,5,6,1) \n"+
                "- 1000 puntos con tres numeros 1 \n" +

                "\n" +

                "-----------------------------\n"+
                "Dinamica del Juego\n" +
                "-----------------------------\n"+

                "Un jugador/ lanza los dados: el jugador decide en cada turno si se planta y conserva lo acumulado, \n" +
                "o si arriesga un nuevo lanzamiento con los dados restantes. Si en un lanzamiento tiene puntos, estos se suman\n" +
                "al total acumulado. Pero si en un lanzamiento no aparece ni un 1 ni un 5 o alguna combinacion, pierde el turno\n"+
                "y los puntos obtenidos en esa ronda.\n"+
                "yLa estatregia está en elegir entre asegurar lo ganado o seguir arriesgando.\n"+
                "En ocasiones, un buen lanzamiento puede disparar el puntaje (como obtener tres 1 y sumar 1000 puntos),\n"+
                "pero siempre con el riesgo de perderlo todo si no salen ni un 1 ni un 5 o combinaciones.\n"+

                "\n" +

                "---------------------------\n"+
                "Fin del Juego\n" +
                "----------------------\n"+

                "El juego termina cuando algun jugador llega a 10mil puntos\n");
    }

}
