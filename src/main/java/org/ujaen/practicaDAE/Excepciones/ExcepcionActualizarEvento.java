/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Excepciones;

/**
 *
 * @author javier
 */
public class ExcepcionActualizarEvento extends RuntimeException{
    public ExcepcionActualizarEvento() {
        super("Error al sincronizar el evento con la Base de datos");
    }
}
