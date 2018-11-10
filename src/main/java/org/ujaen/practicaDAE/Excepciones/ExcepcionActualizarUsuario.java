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
public class ExcepcionActualizarUsuario extends RuntimeException {
    public ExcepcionActualizarUsuario(){
        super("Error al sincronizar un usuario en la base de datos");
    }
}
