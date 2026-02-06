package Vista;

import Controlador.Controlador;
import Vista.mapeo.MapeoCaraDados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class VJuego extends JFrame {
    private VistaGrafica vista;
    private Controlador controlador;
    private MapeoCaraDados mapeo;
    private JLabel fondo;
    private JLabel cubilete;
    private JButton btnLanzar;
    private JButton btnApartar;
    private JButton btnPlantarse;
    private JLabel dado1;
    private JLabel dado2;
    private JLabel dado3;
    private JLabel dado4;
    private JLabel dado5;
    private JLabel lblUniversal;
    private JPanel panelDados;
    private JPanel panelDadosApartados;

    public VJuego(VistaGrafica vista, Controlador controlador) {
        inicializar_comp(vista, controlador);
    }

    private void inicializar_comp(VistaGrafica vista, Controlador controlador) {
        this.vista = vista;
        this.controlador = controlador;
        this.mapeo = new MapeoCaraDados();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700); //le da tamaño a la ventana y la centra
        setLocationRelativeTo(null);
        setResizable(true);
        

        // fondo
        ImageIcon fondoJ = new ImageIcon("src/ImagenesJuego/fondo.png");
        this.fondo = new JLabel(fondoJ);
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);

        // cubilete escalado
        ImageIcon iconoOriginal = new ImageIcon("src/ImagenesJuego/cubilete.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                150, 200, Image.SCALE_SMOOTH
        );
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        cubilete = new JLabel(iconoEscalado);
        cubilete.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botones
        btnLanzar = new JButton("Lanzar dados");
        btnApartar = new JButton("Apartar dados");
        btnPlantarse = new JButton("Plantarse");

        //PANEL INFERIOR DEL FONDO
        JPanel panelInferiorSouth = new JPanel(new BorderLayout());
        panelInferiorSouth.setOpaque(false);
        panelInferiorSouth.add(cubilete, BorderLayout.NORTH);

        panelInferiorSouth.add(btnLanzar, BorderLayout.CENTER);
        panelInferiorSouth.add(btnApartar, BorderLayout.WEST);
        panelInferiorSouth.add(btnPlantarse, BorderLayout.EAST);

        fondo.add(panelInferiorSouth, BorderLayout.SOUTH);
        btnApartar.setEnabled(false);
        btnPlantarse.setEnabled(false);
        btnLanzar.setEnabled(false);

        //PARA DARLE MAS PROTAGONISMO A LOS BOTONES
        btnLanzar.setPreferredSize(new Dimension(160, 45));
        btnApartar.setPreferredSize(new Dimension(160, 45));
        btnPlantarse.setPreferredSize(new Dimension(160, 45));

        //PANEL DADOS APARTADOS
        panelDadosApartados = new JPanel();
        panelDadosApartados.setLayout(new BoxLayout(panelDadosApartados, BoxLayout.Y_AXIS)); //esto es para que pueda aparecer en vertical
        panelDadosApartados.setOpaque(false);

        fondo.add(panelDadosApartados, BorderLayout.EAST);
        panelDadosApartados.setVisible(false);

        // LABEL UNIVERSAL - por ahora solo tiene el msj del turno actual
        lblUniversal = new JLabel();
        fondo.add(lblUniversal, BorderLayout.NORTH);
        lblUniversal.setVisible(false);

        // PANEL DADOS
        panelDados = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelDados.setOpaque(false);
        //lanzamiento (labels de dados vacíos)
        dado1 = new JLabel();
        dado2 = new JLabel();
        dado3 = new JLabel();
        dado4 = new JLabel();
        dado5 = new JLabel();

        panelDados.add(dado1);
        panelDados.add(dado2);
        panelDados.add(dado3);
        panelDados.add(dado4);
        panelDados.add(dado5);

        fondo.add(panelDados, BorderLayout.CENTER);
        panelDados.setVisible(false);

        btnPlantarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.calcularPuntajeTabla();
                    controlador.actualizar_turno();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                //btnPlantarse.setEnabled(false);
                //btnLanzar.setEnabled(false);
                //btnApartar.setEnabled(false);
            }
        });

        // acción del botón lanzar
        btnLanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.lanzar_dados();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                //btnLanzar.setEnabled(false);
                //btnApartar.setEnabled(true);
                //btnPlantarse.setEnabled(true);
            }
        });

        btnApartar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controlador.apartarDados();
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                //btnApartar.setEnabled(false);
                //btnLanzar.setEnabled(true);
                //btnPlantarse.setEnabled(false);
            }
        });


    }

    public void mostrarDadosLanzados(ArrayList<Integer> resultados, int idJugador) {
        //si no llegan todos es porque hay error en el modelo/reglas, el error está antes
        System.out.println(">>> INICIO mostrarDadosLanzados");
        System.out.println("VISTA: mostrarDados()");
        System.out.println("cantidad dados recibidos: " + resultados.size());

        int ancho;
        int alto;

        System.out.println("ANTES removeAll - componentes en panelDados: "
                + panelDados.getComponentCount());
        panelDados.removeAll();
        System.out.println("DESPUÉS removeAll - componentes en panelDados: "
                + panelDados.getComponentCount());

        JLabel[] labels = {dado1, dado2, dado3, dado4, dado5};

        //limpio los labels, que quiza nates tenian otra imagen
        for (JLabel lbl : labels) {
            lbl.setIcon(null);
        }

        fondo.remove(panelDados);

        //SI SOY YO SE MUESTREN LOS DADOS GRANDES EN EL CENTRO Y SI ES OTRO JUGADOR SE MUESTRE CHIQUITOS EN LA PARTE SUPERIOR
        if(idJugador == controlador.getNroJugador()){
            System.out.println("MOSTRANDO dados GRANDES en CENTER");
            ancho = 100;
            alto = 100;
            fondo.add(panelDados, BorderLayout.CENTER);
        }else{
            System.out.println("MOSTRANDO dados CHICOS en NORTH");
            ancho = 50;
            alto = 50;
            fondo.add(panelDados, BorderLayout.NORTH);
        }

        //le pongo los nuevos dados
        for (int i =0 ; i < resultados.size() && i < labels.length; i++) {
            int valor = resultados.get(i);
            System.out.println("Cargando dado " + i + " valor=" + valor);
            ImageIcon iconoOriginal = new ImageIcon(
                    getClass().getResource("/ImagenesJuego/cara" + valor + ".png" )
            );
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    ancho, alto, Image.SCALE_SMOOTH
            );
            labels[i].setIcon(new ImageIcon(imagenEscalada));
            panelDados.add(labels[i]);
        }

        fondo.revalidate();
        fondo.repaint();
        panelDados.setVisible(true);
        System.out.println("<<< FIN mostrarDadosLanzados");

    }

    public void mostrarJugadorActual(String nombre){
        lblUniversal.setVisible(true);
        lblUniversal.setText("Es el turno del jugador: " + nombre);
    }

    public void dados_apartados(ArrayList<Integer> dadosApartados){
        int ancho = 50;
        int alto = 50;

        panelDadosApartados.removeAll();

        lblUniversal.setText("Dados apartados: ");
        lblUniversal.setVisible(true);


        //creo tantos labels como dados
        for (int v : dadosApartados){
            JLabel lbldado = new JLabel();
            ImageIcon iconoOriginal = new ImageIcon(
                    getClass().getResource("/ImagenesJuego/cara" + v + ".png" )
            );
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    ancho, alto, Image.SCALE_SMOOTH
            );
            lbldado.setIcon(new ImageIcon(imagenEscalada));

            panelDadosApartados.add(lbldado);
        }

        panelDadosApartados.setVisible(true);
        panelDadosApartados.revalidate();
        panelDadosApartados.repaint();
    }

    //BOTONES
    public void deshabilitarBotonesTodos(){
        btnLanzar.setEnabled(false);
        btnPlantarse.setEnabled(false);
        btnApartar.setEnabled(false);
    }

    public void habilitarBotonLanzar(){
        btnLanzar.setEnabled(true);
        btnPlantarse.setEnabled(false);
        btnApartar.setEnabled(false);
    }


    public void habilitarBotonesPlantarseYApartar() {
        btnPlantarse.setEnabled(true);
        btnApartar.setEnabled(true);
        btnLanzar.setEnabled(false);
        lblUniversal.setVisible(false);
    }

    public void msjEscalera(String nombre){
        lblUniversal.setText(nombre + "¡Obtuvo escalera! +500 puntos. Turno finalizado");
        lblUniversal.setVisible(true);
    }

    public void msjSinPuntos(String nombre){
        lblUniversal.setText("¡Dados sin puntos! En esta ronda " + nombre + "no suma puntos");
        lblUniversal.setVisible(true);
    }

    public void limpiar_dados_mesa(){
        System.out.println("!!! limpiar_dados_mesa EJECUTADO");

        JLabel[] labels = {dado1, dado2, dado3, dado4, dado5};

        for (JLabel lbl : labels) {
            lbl.setIcon(null);
        }

        panelDados.removeAll();
        panelDados.revalidate();
        panelDados.repaint();

        panelDadosApartados.removeAll();
        panelDadosApartados.revalidate();
        panelDadosApartados.repaint();
    }

}
