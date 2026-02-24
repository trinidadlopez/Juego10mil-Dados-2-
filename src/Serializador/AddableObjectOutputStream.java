package Serializador;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class AddableObjectOutputStream extends ObjectOutputStream{ // EVITA QUE LAS CABECERAS DE LOS ARCHIVOS SE REESCRIBA, ES DECIR, LA CABECERA DEBE ESCRIBIRSE UNA SOLA VEZ, AL PRINCIPIO DEL ARCHIVO
    /** Constructor que recibe OutputStream */
    public AddableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    /** Constructor sin parametros */
    protected AddableObjectOutputStream() throws IOException, SecurityException {
        super();
    }

    /** Redefinicion del metodo de escribir la cabecera para que no haga nada. */
    protected void writeStreamHeader() throws IOException {
    }
}
