/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Interfaces;

import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Usuario;

/**
 *
 * @author jabm9
 */
public interface ServiciosUsuario{
    
    int login(String usuario, String clave);
    
    boolean registro(String usuario, String clave);
    
    boolean registro(UsuarioDTO usuario);
    
    Usuario buscarUsuario(String usuario);
    
    //------------------------
    //^Para depurar
    
    void mostrarUsuarios();
    
}
