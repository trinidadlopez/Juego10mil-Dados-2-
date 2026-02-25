package Vista.grafica;

import Controlador.Controlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.rmi.RemoteException;

public class JDPuntaje extends JDialog{
    DefaultTableModel modeloTabla;
    JTable tabla_puntaje;
    JScrollPane panel;
    Timer timer;
    Controlador controlador;

    public JDPuntaje(JFrame vistaPadre, Controlador controlador){
        super(vistaPadre, false);
        inicializar_comp(controlador);
        
    }

    private void inicializar_comp(Controlador controlador){
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

        tabla_puntaje = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla_puntaje.setModel(modeloTabla);
        panel = new JScrollPane(tabla_puntaje);
        setContentPane(panel);
    }

    public void agregarPuntaje(int puntaje, String nombre, int puntajeT, int ronda){
        modeloTabla.addRow(new Object[]{
                nombre,ronda,puntaje,puntajeT
        });
    }

    public void mostrarTabla(){
        setLocationRelativeTo(null);
        setVisible(true);
        timer.start();
    }

    public void iniciarTimer() { /// timer interno: no se ve por los jugadores.
        timer = new Timer(4000, e -> { //2000 = 2 segundos
            try {
                setVisible(false);
                controlador.procesar_eventos_pendientes();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        timer.setRepeats(false);
    }

    public void clear(){
        modeloTabla.setRowCount(0);
    }

}