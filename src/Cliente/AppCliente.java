package Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import Controlador.Controlador;
import Vista.IVista;
import Vista.consola.VistaConsola;
import Vista.grafica.VistaGrafica;
//import VistaConsola.VistaJuego;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;

public class AppCliente {

    public static void main(String[] args) throws RemoteException{
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );

        Controlador controlador = new Controlador();

        JFrame elegir = new JFrame("Juego 10mil Dados - Eleccion vista");
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnVConsola = new JButton("Consola");
        JButton btnVGrafica = new JButton("Grafica");
        JLabel eleccion = new JLabel("Elija la vista que desea usar: ");
        panelBotones.add(btnVGrafica, SwingConstants.CENTER);
        panelBotones.add(btnVConsola, SwingConstants.CENTER);
        panelPrincipal.add(eleccion, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        elegir.setContentPane(panelPrincipal);
        elegir.setSize(400,100);
        elegir.setLocationRelativeTo(null);
        elegir.setVisible(true);
        eleccion.setHorizontalAlignment(SwingConstants.CENTER);
        eleccion.setVerticalAlignment(SwingConstants.CENTER);
        ar.edu.unlu.rmimvc.cliente.Cliente c = new ar.edu.unlu.rmimvc.cliente.Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        btnVGrafica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IVista vistaGrafica = new VistaGrafica(controlador);
                controlador.setVista(vistaGrafica);
                try {
                    vistaGrafica.iniciar();
                    c.iniciar(controlador);
                } catch (RemoteException | RMIMVCException ex) {
                    throw new RuntimeException(ex);
                }
                elegir.setVisible(false);
            }

        });
        btnVConsola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IVista vistaConsola = new VistaConsola(controlador);
                    vistaConsola.iniciar();
                    c.iniciar(controlador);
                } catch (RemoteException | RMIMVCException ex) {
                    throw new RuntimeException(ex);
                }
                elegir.setVisible(false);
            }
        });


    }

}
