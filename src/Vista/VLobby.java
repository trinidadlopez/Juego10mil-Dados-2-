package Vista;

import Controlador.Controlador;
import Modelo.Jugador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VLobby extends JFrame {
    private VistaGrafica vista;
    private Controlador controlador;
    private JPanel panel;
    private JPanel panelJugadores;
    private JLabel lblEstado;
    private JPanel panelEstado;
    private JLabel lblTimer;
    private Timer timer;
    private int segundosRestantes;


    public VLobby(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    public void inicializar_comp(VistaGrafica vista, Controlador controlador){
        this.controlador = controlador;
        this.vista = vista;

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        setLocationRelativeTo(null);         // Centra la ventana en la pantalla (si todavía no está visible)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //si cerras esta ventana, se termina la aplicacion
        setBounds(100, 100, 400, 300); //100, 100, 350, 300
        setTitle("Juego 10mil - Lobby");
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(new BorderLayout());

        //estado
        panelEstado = new JPanel();
        panelEstado.setLayout(new BoxLayout(panelEstado, BoxLayout.Y_AXIS));
        lblEstado =new JLabel("Esperando al menos 2 jugadores");
        panel.add(panelEstado, BorderLayout.NORTH);
        panelEstado.add(lblEstado);

        //jugadores
        panelJugadores = new JPanel();
        panelJugadores.setLayout(new BoxLayout(panelJugadores, BoxLayout.Y_AXIS));
        panelJugadores.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(panelJugadores, BorderLayout.CENTER);

        //timer
        lblTimer = new JLabel("");
        lblTimer.setVisible(false);
        panel.add(lblTimer, BorderLayout.SOUTH);
        panelEstado.add(lblTimer);

    }

    public void actualizarJugadores(ArrayList<Jugador> jugadores) {
        panelJugadores.removeAll();
        for (Jugador j : jugadores) {
            JLabel lblJugador = new JLabel(
                    j.getNroJugador() + " - " +j.getNombreJugador()
            );
            panelJugadores.add(lblJugador);
        }
        panelJugadores.revalidate();
        panelJugadores.repaint();
        if (jugadores.size() < 2) {
            lblEstado.setText("Esperando al menos 2 jugadores (" + jugadores.size() + "/6)");
        } else {
            lblEstado.setText("Esperando a mas jugadores...\n Jugadores conectados: " + jugadores.size() + "/6.");
        }
        if (jugadores.size() >= 2) {
            lblTimer.setVisible(true);
        }
    }

    public void iniciarTimer() { /// timer interno: no se ve por los jugadores.
        timer = new Timer(2000, e -> { //2000 = 2 segundos
            try {
                controlador.comenzarJuego();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        timer.start();
    }

    /*public void actualizarTimer(int segundosRestantes) {
        int min = segundosRestantes / 60;
        int sec = segundosRestantes % 60;

        lblTimer.setText("La partida comienza en " + String.format("%02d:%02d", min, sec));
        lblTimer.setVisible(true);
    }*/
}
