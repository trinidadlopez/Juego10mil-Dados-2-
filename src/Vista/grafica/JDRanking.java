package Vista.grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class JDRanking extends JDialog {
    private DefaultTableModel modeloTabla;
    private JTable tabla_ranking;
    private JPanel panel;
    private JButton btnVolver;


    public JDRanking(JFrame vistaPadre, VistaGrafica vistaGrafica){
        super(vistaPadre, false); //llamo al constructor de JDialog con los parametros: (ventana que lo contiene, modal=true/false)
        inicializar_comp(vistaGrafica);
    }

    private void inicializar_comp(VistaGrafica vista){
        setTitle("Juego10Mil - Ranking historico");
        setResizable(false);
        setBounds(100, 100, 500, 500);//posicion x (horizontal)=100, posicion y (vertical)=100, ancho=247 , largo=109
        setLocationRelativeTo(null);
        btnVolver = new JButton("Volver");

        modeloTabla = new DefaultTableModel();

        tabla_ranking = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla_ranking.setModel(modeloTabla);
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BorderLayout());
        panel.add(btnVolver, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tabla_ranking);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                vista.mostrarMenuPrincipal();
            }
        });

    }

    public void cargarDatos(Object[][] datosRanking){
        String[] columnas = {"Nombre", "Puntaje ganador","Fecha"};
        modeloTabla = new DefaultTableModel(datosRanking, columnas);
        tabla_ranking.setModel(modeloTabla);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla); //ordena la tabla
        tabla_ranking.setRowSorter(sorter); //asocia el ordenador a la tabla
        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.DESCENDING))); //manera en la que se ordena automaticamente(por puntaje, de mayor a menor)
    }

}
