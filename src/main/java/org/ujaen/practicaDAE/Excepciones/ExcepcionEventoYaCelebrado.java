package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionEventoYaCelebrado extends RuntimeException {
    
    public ExcepcionEventoYaCelebrado()
    {
        super("Este evento ya ha sido celebrado");
    }
}
