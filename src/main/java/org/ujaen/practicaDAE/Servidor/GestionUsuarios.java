/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Excepciones.ExcepcionTokenInvalido;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaRegistrado;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosUsuario;

/**
 *
 * @author macosx
 */
@Component
public class GestionUsuarios implements ServiciosUsuario {

    Map<String, Usuario> usuarios = new TreeMap<>();
    
    Map<Integer, Usuario> registroTokens = new TreeMap<>();

    /**
     * Busca un usuario por identificador o nombre
     * 
     * @param usuario Nombre o identificador del usuario
     * @return usuario
     */
    protected Usuario buscarUsuario(String usuario) 
    {
        // Falta meter excepciones y tal 
        return usuarios.get(usuario);
    }
    
    /**
     * Devuelve el usuario al que pertenece el token en caso de que este sea
     * válido y esté registrado, en otro caso lanza una excepción
     * 
     * @param token Token a verificar
     * @return usuario
     */
    protected Usuario verificaToken(int token)
    {
        Usuario usuario = registroTokens.get(token);
        
        // TODO - Tunear Excepción ¿Estática o en Runtime?
        if (usuario == null)
            throw new ExcepcionTokenInvalido();
        
        return usuario;
    }
    
    @Override
    public int login(String usuario, String clave) 
    {
        int token = -1;
        Usuario r_usuario = usuarios.get(usuario);
        
        // Si no existe el usuario devuelve -1
        if (r_usuario == null)
            return token;
        
        if (r_usuario.getClave().equals(clave)) 
        {
            Random aleatorio = new Random(System.currentTimeMillis());
            token = aleatorio.nextInt(Integer.MAX_VALUE);
            
            // Genera token hasta que haya uno nuevo
            while (registroTokens.containsKey(token))
                token = aleatorio.nextInt(Integer.MAX_VALUE);
            
            registroTokens.put(token, r_usuario);
            
            // Ahora hay que ver si el token anterior esta en el registro 
            // y darlo de baja
            
            if (registroTokens.containsKey(r_usuario.getToken()))
                registroTokens.remove(r_usuario.getToken());
            
            // Asignamos el nuevo Token
            r_usuario.setToken(token);
            
            return token;
        }
        
        // Si la clave no existe, retorna codigo -2, ¿devolver excepción?
        token = -2;
        return token;
    }

    @Override
    public boolean registro(String usuario, String clave) {
        if (!usuarios.containsKey(usuario)) {
            Usuario tmp = new Usuario(usuario, clave);
            usuarios.put(usuario, tmp);
            return true;
        }else {
            throw new ExcepcionUsuarioYaRegistrado();
        }
       
    }

}
