package Controlador;

public enum EstadoInterfaz {
    NORMAL,
    MOSTRANDO_ESCALERA,
    MOSTRANDO_DADOS_SIN_PUNTOS,
    MOSTRANDO_MSJ,
    MOSTRANDO_PUNTAJE,
    ACTUALIZANDO_EL_TURNO,
    MAXIMO_APARTADOS
}

// este estado me permite saber en momento se encuentra el controlador y que esta ejecutando.
// si esta en estado normal --> ejecuta directamente los notificarObservadores que le llegan
// si esta en !estado_normal --> lo encola y despues procesa los eventos pendientes
