/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Servidor.DAOs.UsuarioDAO;
import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;

/**
 *
 * @author jabm9
 */

@Component
public class ServicioDatosUsuario implements UserDetailsService {
    
    @Autowired
    UsuarioDAO usuarioDAO;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Usuario usuario = usuarioDAO.buscarUsuario(userName);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
            
        }
        
        UserDetails userDetails =  User.withUsername(userName).password("{bcrypt}" + usuario.getClave()).roles("USUARIO").build();
        
        return userDetails;
    }
    
}
