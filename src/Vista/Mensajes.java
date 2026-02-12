package Vista;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class Mensajes extends JFrame {
    private JPanel panelPrincipal;
    private JLabel texto;
    private Controlador controlador;
    private VistaGrafica vista;
    private Timer timer;
    private JButton btnOK;

    public Mensajes(Controlador controlador, VistaGrafica vista){
        inicializar(controlador,vista);
    }

    private void inicializar(Controlador controlador, VistaGrafica vista){
        this.controlador = controlador;
        this.vista = vista;
        iniciarTimer();

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setBounds(100, 100, 400,200);

        panelPrincipal = new JPanel(new BorderLayout());
        texto = new JLabel();
        panelPrincipal.add(texto, BorderLayout.CENTER);
        texto.setVisible(true);

        setContentPane(panelPrincipal);
        setVisible(false);

        btnOK = new JButton("Ok");
        panelPrincipal.add(btnOK, BorderLayout.SOUTH);
        btnOK.setVisible(false);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.terminarJuego();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    public void msj_universal(String cadena){
        texto.setText(cadena);
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
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

    public void msj_ganador(String cadena){
        texto.setText(cadena);
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        btnOK.setVisible(true);
        setEnabled(true);
    }

}
