package Vista.grafica;

import Controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

public class JDMensajes extends JDialog{
    private JPanel panelPrincipal;
    private JLabel texto;
    private Controlador controlador;
    private Timer timer;

    public JDMensajes(JFrame vistaPadre, Controlador controlador){
        super(vistaPadre, false);
        inicializar(controlador);
    }

    private void inicializar(Controlador controlador){
        this.controlador = controlador;
        iniciarTimer();

        setResizable(false); //puede no estar. Es para que no se pueda redimensionar
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25)); //le agrega margen al texto dentro de la ventana (arriba, izq, abajo, der)
        texto = new JLabel();
        panelPrincipal.add(texto, BorderLayout.CENTER);
        texto.setVisible(true);
        setContentPane(panelPrincipal);
        pack(); //la ventana se ajusta al tamño del textos
        setLocationRelativeTo(null); // Centra la ventana en la pantalla (si todavía no está visible)
        setVisible(false);

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

    public void msjTurno(String nombre){
        texto.setText("Turno del jugador/a: " + nombre);
        pack();
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        timer.start();
    }

    public void maxApartado(String nombre, int puntos){
        texto.setText(nombre + " apartó la cantidad maxima de dados. Sus puntos en esta ronda son: " + puntos);
        pack();
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        timer.start();
    }

    public void mostrarPlantado(String nombre, int puntos){
        texto.setText(nombre + " se plantó, suma " + puntos + " puntos.");
        pack();
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        timer.start();
    }

    public void escalera(String nombre){
        texto.setText(nombre + " obtuvo escalera! +500 puntos. Turno finalizado");
        pack();
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        timer.start();
    }

    public void dadosSinPuntos(String nombre){
        texto.setText("¡Dados sin puntos! En esta ronda " + nombre + " no suma puntos");
        pack();
        setVisible(true);
        texto.setVisible(true);
        panelPrincipal.setVisible(true);
        timer.start();
    }


}
