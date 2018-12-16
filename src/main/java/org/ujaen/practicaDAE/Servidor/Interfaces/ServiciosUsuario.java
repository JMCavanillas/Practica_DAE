/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Interfaces;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public interface ServiciosUsuario 
{
    /**
     * Login al sistema
     * @param usuario
     * @param clave
     * @return true si el usuario está registrado en el sistema, false
     * en caso contrario
     */
    boolean login(String usuario, String clave);
    
    
    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @param usuario id del nuevo usuario
     * @param clave clave del nuevo usuario
     * @param correo correo del usuario
     * @return Boolean indicando se la operación se ha realizado con exito o no
     */
    boolean registro(String usuario, String clave, String correo);
    
}
