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
public interface ServiciosUsuario{
    
    boolean login(String usuario, String clave);
    
    boolean registro(String usuario, String clave);
    
    //------------------------
    //^Para depurar
    
    void mostrarUsuarios();
    
}
