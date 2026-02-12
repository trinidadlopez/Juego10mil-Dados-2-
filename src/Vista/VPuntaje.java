package Vista;

import Controlador.Controlador;
import Modelo.Jugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;

public class VPuntaje extends JFrame{
    DefaultTableModel modeloTabla;
    JTable tabla_puntaje;
    JScrollPane panel;
    Timer timer;
    Controlador controlador;
    VistaGrafica vista;

    public VPuntaje(Controlador controlador, VistaGrafica vista){
        inicializar_comp(controlador, vista);
    }

    private void inicializar_comp(Controlador controlador, VistaGrafica vista){
        this.vista = vista;
        this.controlador = controlador;
        iniciarTimer();
        setTitle("Juego10Mil - Puntajes");
        setResizable(false);
        setBounds(100, 100, 500, 500);//posicion x (horizontal)=100, posicion y (vertical)=100, ancho=247 , largo=109
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("JUGADOR");
        modeloTabla.addColumn("RONDA NRO°");
        modeloTabla.addColumn("PUNTAJE RONDA");
        modeloTabla.addColumn("PUNTAJE TOTAL");

        tabla_puntaje = new JTable();
        tabla_puntaje.setModel(modeloTabla);
        panel = new JScrollPane(tabla_puntaje);
        setContentPane(panel);
    }

    public void agregarPuntaje(int puntaje, String nombre, int puntajeT, int ronda){
        modeloTabla.addRow(new Object[]{
                nombre,ronda,puntaje,puntajeT
        });
        setVisible(false);
    }

    public void mostrarTabla(){
        setVisible(false);
        timer.start();
    }

    public void iniciarTimer() { /// timer interno: no se ve por los jugadores.
        timer = new Timer(5000, e -> { //2000 = 2 segundos
            try {
                controlador.procesar_eventos_pendientes();
                setVisible(false);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        timer.setRepeats(false);
    }
}
