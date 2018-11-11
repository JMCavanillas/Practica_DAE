package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public class ExcepcionCancelarInscripcion extends RuntimeException {

    public ExcepcionCancelarInscripcion() {
        super("No se pudo cancelar la inscripcion");
    }
}
