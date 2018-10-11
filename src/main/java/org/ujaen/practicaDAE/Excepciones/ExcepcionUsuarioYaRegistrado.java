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
public class ExcepcionUsuarioYaRegistrado extends RuntimeException {

    @Override
    public String toString() {
        return ("Ya existe un usuario registrado con este nombre de usuario");

    }
}
