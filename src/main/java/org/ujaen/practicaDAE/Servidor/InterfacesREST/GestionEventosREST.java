package org.ujaen.practicaDAE.Servidor.InterfacesREST;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import org.ujaen.practicaDAE.Servidor.GestionEventos;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@RestController
@RequestMapping("/")
public class GestionEventosREST {

    @Autowired
    GestionEventos gestionEventos;

    @RequestMapping(value = "/evento/{id}", method = GET, produces = "application/json")
    public ResponseEntity<EventoDTO> obtenerEvento(@PathVariable int id) {
        Evento evento = gestionEventos.obtenerEvento(id);
        if (evento != null) {
            EventoDTO eventoDTO = evento.toDTO();
            return new ResponseEntity<>(eventoDTO, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/usuario/{nombre}/evento", method = POST, produces = "application/json")
    public ResponseEntity<Boolean> crearEvento(@PathVariable String nombre,
            @RequestBody EventoDTO evento) {

        if (!evento.getOrganizador().equals(nombre)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (gestionEventos.crearEvento(evento, evento.getOrganizador())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Mapeo cambiado
    @RequestMapping(value = "/usuario/{nombre}/evento/organizado/{id}", method = DELETE, produces = "application/json")
    public ResponseEntity<Void> cancelarEvento(@PathVariable int id, @PathVariable String nombre) {

        EventoDTO evento = gestionEventos.buscarEventoID(id).toDTO();

        if (!evento.getOrganizador().equals(nombre)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        gestionEventos.cancelarEvento(evento, evento.getOrganizador());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/evento/tipo/{tipo}", method = GET, produces = "application/json")
    public ResponseEntity<Pagina<EventoDTO>> buscarEventoTipo(@PathVariable Evento.Tipo tipo,
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {

        if (tipo.equals("CHARLA") || tipo.equals("CURSO") || tipo.equals("ACTIVIDAD_DEPORTIVA") || tipo.equals("VISITA_CULTURAL")) {
            List<EventoDTO> eventosTipo = gestionEventos.buscarEventoTipo(tipo);

            Pagina<EventoDTO> pagina = new Pagina<>(eventosTipo, page, size);
            
            return new ResponseEntity<>(pagina, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //?
    @RequestMapping(value = "/evento", method = GET, produces = "application/json")
    public Pagina<EventoDTO> buscarEventoPalabrasClave(@RequestParam List<String> palabras,
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {

        List<EventoDTO> eventosPalabras = gestionEventos.buscarEventoPalabrasClave(palabras);

        Pagina<EventoDTO> pagina = new Pagina<>(eventosPalabras, page, size);

        return pagina;

    }

    @RequestMapping(value = "/usuario/{nombre}/evento/inscrito", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosInscritos(@PathVariable String nombre,
            @RequestParam(value = "tiempo", required = false) String tiempo) {

        List<EventoDTO> eventosInscritos = new ArrayList<>();
        switch (tiempo) {
            case "futuro":
                eventosInscritos = gestionEventos.verEventosInscritosFuturos(nombre);
                break;
            case "pasado":
                eventosInscritos = gestionEventos.verEventosInscritosCelebrados(nombre);
                break;
            case "espera":
                eventosInscritos = gestionEventos.verEventosListaEspera(nombre);
                break;
            default:
                eventosInscritos.addAll(gestionEventos.verEventosInscritosCelebrados(nombre));
                eventosInscritos.addAll(gestionEventos.verEventosInscritosFuturos(nombre));
                break;

        }

        return eventosInscritos;
    }

    @RequestMapping(value = "/usuario/{nombre}/evento/inscrito/{id}", method = POST, produces = "application/json")
    public boolean inscribirEvento(@PathVariable String nombre, @PathVariable int id) {

        Evento evento = gestionEventos.obtenerEvento(id);
        
        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.inscribirseEvento(eventoDTO, nombre);

        return true;

    }

    @RequestMapping(value = "/usuario/{nombre}/evento/inscrito/{id}", method = DELETE, produces = "application/json")
    public void cancelarInscripcionEvento(@PathVariable String nombre, @PathVariable int id) {

        Evento evento = gestionEventos.obtenerEvento(id);
        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.cancelarInscripcionEvento(eventoDTO, nombre);

    }

    @RequestMapping(value = "/usuario/{nombre}/evento/organizado", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosOrganizado(@PathVariable String nombre,
            @RequestParam(value = "tiempo", required = false, defaultValue = "todos") String tiempo) {

        List<EventoDTO> eventosOrganizados = new ArrayList<>();
        switch (tiempo) {
            case "futuro":
                eventosOrganizados = gestionEventos.verEventosOrganizadosFuturos(nombre);
                break;
            case "pasado":
                eventosOrganizados = gestionEventos.verEventosOrganizados(nombre);
                break;
            default:
                eventosOrganizados.addAll(gestionEventos.verEventosOrganizados(nombre));
                eventosOrganizados.addAll(gestionEventos.verEventosOrganizadosFuturos(nombre));

        }

        return eventosOrganizados;
    }

}
