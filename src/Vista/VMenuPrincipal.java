package Vista;

import Controlador.Controlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMenuPrincipal extends JFrame {
    private JPanel panel;
    private JButton btnJugar;
    private JButton btnReglas;
    private JButton btnSalir;
    private JLabel lblMP;
    private VistaGrafica vista;
    private Controlador controlador;

    public VMenuPrincipal(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    public void inicializar_comp(VistaGrafica vista, Controlador controlador){
        this.controlador = controlador;
        this.vista = vista;

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //si cerras esta ventana, se termina la aplicacion
        setBounds(100, 100, 300, 300);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        setTitle("Juego 10mil - Dados");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalStrut(20));
        lblMP = new JLabel("MENU PRINCIPAL");
        lblMP.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMP.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblMP);


        btnJugar = new JButton("Iniciar Juego");
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnJugar);
        panel.add(Box.createVerticalStrut(15));

        btnReglas = new JButton("Reglas");
        btnReglas.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnReglas);
        panel.add(Box.createVerticalStrut(15));

        btnSalir = new JButton("Salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnSalir);
        panel.add(Box.createVerticalStrut(15));


        btnJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                vista.mostrarNombreJugador();
            }
        });

        /*btnReglas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                vista.mostrarReglas(); //me gustaria mostrar una imagen con las reglas o un texto con scroll1!!!
            }
        });*/

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
