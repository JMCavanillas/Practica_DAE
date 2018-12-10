package org.ujaen.practicaDAE.Servidor.InterfacesREST;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;
import org.ujaen.practicaDAE.Servidor.GestionUsuarios;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */

@RestController
@RequestMapping("/")
public class GestionUsuariosREST {
    
    @Autowired
    GestionUsuarios gestionUsuarios;
    
    @RequestMapping(value="/usuario/{nombre}", method=GET, produces="application/json")
    public UsuarioDTO buscarUsuario(@PathVariable String nombre)
    {
        Usuario usuario=gestionUsuarios.buscarUsuario(nombre);
        UsuarioDTO usuarioDTO = usuario.toDTO();
        
      
        return usuarioDTO;
        
    }
    
    @RequestMapping(value="/usuario/login/{nombre}", method=POST, produces="application/json")
    public boolean login(@PathVariable String nombre,
            @RequestBody UsuarioDTO usuario)
    {
        
        
        int autentificacion=gestionUsuarios.login(nombre, usuario.getClave());
        if(autentificacion==1){
            return true;
        }
        
      
        return false;
        
    }
    
    
    @RequestMapping(value="/usuario/{nombre}", method=POST, produces="application/json")
    public boolean registrarUsuario(
    @PathVariable String nombre,
            @RequestBody UsuarioDTO usuario){
        
        
        return gestionUsuarios.registro(nombre, usuario.getClave(), usuario.getCorreo());
      
        
    }
    
    
    

}
