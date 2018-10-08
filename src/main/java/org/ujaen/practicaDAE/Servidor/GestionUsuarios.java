/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosUsuario;

/**
 *
 * @author macosx
 */
@Component
public class GestionUsuarios implements ServiciosUsuario {

    Map<String, Usuario> usuarios = new TreeMap<>();

    /*
    Devuelve -1 si el usuario no está registrado o un número aleatorio entre 0 y 999 si lo está
     */
    @Override
    public int login(String usuario, String clave) {
        int token = -1;
        if (usuarios.get(usuario).getContraseña().equals(clave)) {
            Random aleatorio = new Random(System.currentTimeMillis());
            token = aleatorio.nextInt(1000);
            return token;

        }
        return token;

    }

    @Override
    public boolean registro(String usuario, String clave) {
        if (!usuarios.containsKey(usuario)) {
            Usuario tmp = new Usuario(usuario, clave);
            usuarios.put(usuario, tmp);
            return true;
        }
        return false;
    }

    @Override
    public void mostrarUsuarios() {
        for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
            System.out.println(entry.getValue().getNombre());
            
            
        }

    }

    public Usuario buscarUsuario(String usuario) {
        //Falta meter excepciones y tal 
        return usuarios.get(usuario);

    }

    @Override
    public boolean registro(UsuarioDTO usuario) {
        if (!usuarios.containsKey(usuario.getNombre())) {
            Usuario tmp = new Usuario(usuario.getNombre(), usuario.getContraseña());
            usuarios.put(usuario.getNombre(), tmp);
            return true;
        }
        return false;
    }

}
