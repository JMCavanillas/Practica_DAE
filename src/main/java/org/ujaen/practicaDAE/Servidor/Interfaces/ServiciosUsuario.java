/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Interfaces;

/**
 *
 * @author jabm9
 */
public interface ServiciosUsuario 
{
    /**
     * Dado un usuario le da acceso al sistema y le entrega un token 
     * identificador con el que podrá validar las operaciones posteriores
     * 
     * Si el usuario que realiza el login ya tiene un token registrado, este se 
     * eliminará del sistema y se le asignará uno nuevo, por lo que un mismo 
     * usuario no podrá estar conectado simultáneamente desde dos clientes 
     * distintos
     * 
     * @param usuario
     * @param clave
     * @return Token si el usuario existe y la clave es correcta
     *         -1 si el usuario no existe
     *         -2 si la contraseña especificada no es correcta
     */
    int login(String usuario, String clave);
    
    
    /**
     * Registra un nuevo usuario en el sistema
     * 
     * @param usuario id del nuevo usuario
     * @param clave clave del nuevo usuario
     * @return Boolean indicando se la operación se ha realizado con exito o no
     */
    boolean registro(String usuario, String clave);
    
//    boolean registro(UsuarioDTO usuario);   
//    void mostrarUsuarios();
    
}
