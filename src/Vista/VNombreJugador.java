package Vista;

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

    public void inicializar_comp(VistaGrafica vista, Controlador controlador){
        this.controlador = controlador;
        this.vista = vista;

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //si cerras esta ventana, se termina la aplicacion
        setBounds(100, 100, 247, 109);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        setTitle("Juego 10mil - Dados");
        panel.setLayout(new BorderLayout());

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

    //estos dos no se si son necesarios
    public void onClickContinuar(ActionListener listener) { //Permite que otra clase (por ejemplo VistaGrafica) decida qué pasa cuando se hace click en el botón.
        this.btnComenzar.addActionListener(listener);
    }

    public String getNombreUsuario() {
        return this.textNombreUser.getText();
    }


}

