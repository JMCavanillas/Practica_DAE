package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionActualizarUsuario extends RuntimeException {
    public ExcepcionActualizarUsuario(){
        super("Error al sincronizar un usuario en la base de datos");
    }
}
