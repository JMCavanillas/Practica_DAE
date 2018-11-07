/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author jabm9
 */
public class ExcepcionCancelarInscripcion extends RuntimeException {

    public ExcepcionCancelarInscripcion() {
        super("No se pudo cancelar la inscripcion");
    }
}
