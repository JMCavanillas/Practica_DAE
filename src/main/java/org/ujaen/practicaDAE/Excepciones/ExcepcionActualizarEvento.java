package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionActualizarEvento extends RuntimeException{
    public ExcepcionActualizarEvento() {
        super("Error al sincronizar el evento con la Base de datos");
    }
}
