package org.ujaen.practicaDAE.Servidor.InterfacesREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaRegistrado;
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
        
    @RequestMapping(value = "/usuarios/{nombre}", method = POST, produces = "application/json")
    public ResponseEntity<Boolean> registrarUsuario(
            @PathVariable String nombre,
            @RequestBody UsuarioDTO usuario) throws ExcepcionUsuarioYaRegistrado {

        if (!gestionUsuarios.registro(nombre, usuario.getClave(), usuario.getCorreo())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
