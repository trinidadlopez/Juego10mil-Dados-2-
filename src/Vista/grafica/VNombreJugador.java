package Vista.grafica;

import Controlador.Controlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class VNombreJugador extends JFrame {
    private JPanel panel;
    private JTextField textNombreUser;
    private JButton btnComenzar;
    private JButton btnVolver;
    private JLabel lblNombreJ;
    private VistaGrafica vista;
    private Controlador controlador;

    public VNombreJugador(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    private void inicializar_comp(VistaGrafica vista, Controlador controlador){
        this.controlador = controlador;
        this.vista = vista;

        setTitle("Juego 10mil - Dados");
        setSize(400, 109);
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //si cerras esta ventana, se termina la aplicacion
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(new BorderLayout());

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));


        lblNombreJ = new JLabel("Ingrese el nombre:");
        panel.add(lblNombreJ, BorderLayout.WEST);

        textNombreUser = new JTextField();
        panel.add(textNombreUser, BorderLayout.CENTER);

        btnComenzar = new JButton("Comenzar");
        panel.add(btnComenzar, BorderLayout.SOUTH);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver, BorderLayout.EAST);

        SwingUtilities.getRootPane(btnComenzar).setDefaultButton(btnComenzar); //lo que quiere decir es que si el usuario presiona "enter" el boton por defecto que se va a presionar es el btnContinuar

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                vista.mostrarMenuPrincipal();
            }
        });

        btnComenzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                vista.mostrarLobby();
                try{
                    controlador.iniciarJugador(textNombreUser.getText());
                }catch (RemoteException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}

