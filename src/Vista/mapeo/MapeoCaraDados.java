package Vista.mapeo;

import javax.swing.*;
import java.util.HashMap;

public class MapeoCaraDados {
    private HashMap<Integer,String> mapeoCarasDados; // mapeo a imagenes dados


    public MapeoCaraDados(){
        mapeoCarasDados=new HashMap<>();
        mapear();
    }

    public ImageIcon obtenerDado(int nroDado){
        String nombre = mapeoCarasDados.get(nroDado);
        return new ImageIcon("src/ImagenesJuego/" + nombre);
    }

    private void mapear(){
        mapeoCarasDados.put(1, "cara1.png");
        mapeoCarasDados.put(2, "cara2.png");
        mapeoCarasDados.put(3, "cara3.png");
        mapeoCarasDados.put(4, "cara4.png");
        mapeoCarasDados.put(5, "cara5.png");
        mapeoCarasDados.put(6, "cara6.png");
    }


}
