package Vista;

import Modelo.Jugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VPuntaje extends JFrame{
    DefaultTableModel modeloTabla;
    JTable tabla_puntaje;
    JScrollPane panel;

    public VPuntaje(){
        inicializar_comp();
    }

    public void inicializar_comp(){
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
    }
}
