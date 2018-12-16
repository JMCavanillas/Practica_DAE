package org.ujaen.practicaDAE.Servidor;

import java.util.Date;
import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Servidor.DAOs.UsuarioDAO;
import org.ujaen.practicaDAE.Excepciones.ExcepcionTokenInvalido;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaRegistrado;
import org.ujaen.practicaDAE.Servidor.Correo.ServicioCorreo;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosUsuario;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Component
public class GestionUsuarios implements ServiciosUsuario {

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Autowired
    private ServicioCorreo servicioCorreo;
    

    //Map<String, Usuario> usuarios = new TreeMap<>();
    Map<Integer, Usuario> registroTokens = new TreeMap<>();

    /**
     * Busca un usuario por identificador o nombre
     *
     * @param usuario Nombre o identificador del usuario
     * @return usuario
     */
    public Usuario buscarUsuario(String usuario) {
        return getUsuarioDAO().buscarUsuario(usuario);

    }

    /**
     * Devuelve el usuario al que pertenece el token en caso de que este sea
     * válido y esté registrado, en otro caso lanza una excepción
     *
     * @param token Token a verificar
     * @return usuario
     */
    protected Usuario verificaToken(int token) {
        Usuario usuario = registroTokens.get(token);

        if (usuario == null) {
            throw new ExcepcionTokenInvalido();
        }

        return usuario;
    }

    @Override
    public boolean login(String usuario, String clave) {
        //Version login para probar con cliente externo
      
        Usuario r_usuario = getUsuarioDAO().buscarUsuario(usuario);
        
        if(r_usuario.getClave().equals(clave)){
            return true;
        }
        
        return false;

    }

    @Override
    public boolean registro(String usuario, String clave, String correo) {
        if (buscarUsuario(usuario) == null) {
            
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            clave = passwordEncoder.encode(clave);
            
            Usuario tmp = new Usuario(usuario, clave,correo);
            getUsuarioDAO().registrarUsuario(tmp);
            //servicioCorreo.sendSimpleMessage(correo, "Hola", usuario, "algo", new Date(), "Jaén");

            return true;
        } else {
            return false;
        }

    }

    /**
     * @return the usuarioDAO
     */
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }
}
