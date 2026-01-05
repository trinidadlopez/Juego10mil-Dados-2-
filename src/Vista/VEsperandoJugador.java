package Vista;

import Controlador.Controlador;
import Modelo.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VEsperandoJugador extends JFrame {
    private VistaGrafica vista;
    private Controlador controlador;
    DefaultTableModel tablaModelo;
    JTable tabla_jugadores;
    JScrollPane panel;
    private JButton btnIniciar;
    private JPanel contenedor;

    public VEsperandoJugador(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    public void inicializar_comp(VistaGrafica vista, Controlador controlador) {
        this.controlador = controlador;
        this.vista = vista;

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //si cerras esta ventana, se termina la aplicacion
        setBounds(100, 100, 500, 500);

        setTitle("Esperando (al menos) 2 jugadores");
        tablaModelo = new DefaultTableModel();
        tablaModelo.addColumn("Nombre jugador");
        tablaModelo.addColumn("N° de jugador");

        tabla_jugadores = new JTable();
        tabla_jugadores.setModel(tablaModelo);
        panel = new JScrollPane(tabla_jugadores);
        //setContentPane(panel);

        btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(e -> {
            try {
                controlador.comenzarJuego();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(new EmptyBorder(5,5,5,5));          
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.add(btnIniciar, BorderLayout.SOUTH);

        setContentPane(contenedor);

        btnIniciar.addActionListener(e -> {
            try {
                controlador.comenzarJuego();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            setVisible(false);
            vista.mostrarJuego();
        });


    }

    public void agregarJ(ArrayList<Jugador> jugadores) {
        tablaModelo.setRowCount(0);
        for (Jugador jugador : jugadores) {
            tablaModelo.addRow(new Object[]{
                    jugador.getNombreJugador(),
                    jugador.getNroJugador()
            });
        }
        btnIniciar.setEnabled(jugadores.size()>=2);
    }

    public void habilitarBotonIniciar(boolean habilitar){
        btnIniciar.setEnabled(habilitar);
    }


}
