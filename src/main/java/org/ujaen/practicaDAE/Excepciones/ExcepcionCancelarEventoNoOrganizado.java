package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionCancelarEventoNoOrganizado extends RuntimeException {
    
    public ExcepcionCancelarEventoNoOrganizado()
    {
        super("El evento a cancelar no existe");
    }
}
