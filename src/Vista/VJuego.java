package Vista;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VJuego extends JFrame {
    private VistaGrafica vista;
    private Controlador controlador;
    private JPanel panelMesa;
    private Image fondo;

    public VJuego(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    private void inicializar_comp(VistaGrafica vista, Controlador controlador) {
        this.vista = vista;
        this.controlador = controlador;

        setTitle("Juego 10mil - En curso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("src/ImagenesJuego/fondoJuego.jpg");
        fondo = icon.getImage();

        panelMesa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelMesa.setLayout(null);
        setContentPane(panelMesa);
    }


}
