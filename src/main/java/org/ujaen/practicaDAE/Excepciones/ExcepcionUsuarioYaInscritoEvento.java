package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionUsuarioYaInscritoEvento extends RuntimeException {
    
    public ExcepcionUsuarioYaInscritoEvento()
    {
        super("Ya estás inscrito en este evento");
    }
}
