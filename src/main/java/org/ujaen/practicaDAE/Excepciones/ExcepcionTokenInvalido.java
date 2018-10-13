package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionTokenInvalido extends RuntimeException {
    
    public ExcepcionTokenInvalido()
    {
        super("No puede cancelar un evento que no has organizado");
    }
}
