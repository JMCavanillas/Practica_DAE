package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionUsuarioYaRegistrado extends RuntimeException {
    
    public ExcepcionUsuarioYaRegistrado()
    {
        super("Ya existe un usuario registrado con este nombre de usuario");
    }
}
