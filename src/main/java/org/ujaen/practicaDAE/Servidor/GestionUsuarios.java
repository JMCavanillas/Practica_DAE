/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;

/**
 *
 * @author macosx
 */
@Component
public class GestionUsuarios implements ServiciosUsuario {

    Map<String, String> usuarios = new TreeMap<>();

    @Override
    public boolean login(String usuario, String clave) {
        if (usuarios.get(usuario).equals(clave)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean registro(String usuario, String clave) {
        if (!usuarios.containsKey(usuario)) {
            usuarios.put(usuario, clave);
            return true;
        }
        return false;

    }

}
