package org.ujaen.practicaDAE.Servidor.InterfacesREST;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public EventoDTO obtenerEvento(@PathVariable int id) {
        Evento evento = gestionEventos.obtenerEvento(id);
        EventoDTO eventoDTO = evento.toDTO();

        return eventoDTO;

    }

    @RequestMapping(value = "/evento", method = POST, produces = "application/json")
    public void crearEvento(@RequestBody EventoDTO evento) {

        gestionEventos.crearEvento(evento, evento.getOrganizador());

    }

    @RequestMapping(value = "/evento/{id}", method = DELETE, produces = "application/json")
    public void cancelarEvento(@PathVariable int id,
            @RequestBody EventoDTO evento) {

        gestionEventos.cancelarEvento(evento, evento.getOrganizador());

    }

    @RequestMapping(value = "/evento/tipo/{tipo}", method = GET, produces = "application/json")
    public Pagina<EventoDTO> buscarEventoTipo(@PathVariable Evento.Tipo tipo, 
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {
        
        List<EventoDTO> eventosTipo = gestionEventos.buscarEventoTipo(tipo);
        
        Pagina<EventoDTO>  pagina = new Pagina<>(eventosTipo, page, size);

        return pagina;
    }

    @RequestMapping(value = "/evento", method = GET, produces = "application/json")
    public Pagina<EventoDTO> buscarEventoPalabrasClave(@RequestParam List<String> palabras,
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {

        List<EventoDTO> eventosPalabras = gestionEventos.buscarEventoPalabrasClave(palabras);
        
        Pagina<EventoDTO>  pagina = new Pagina<>(eventosPalabras, page, size);

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

    @RequestMapping(value = "/usuario/{nombre}/evento/inscrito", method = POST, produces = "application/json")
    public void inscribirEvento(@PathVariable String nombre,
            @RequestBody int id) {

        Evento evento = gestionEventos.obtenerEvento(id);
        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.inscribirseEvento(eventoDTO, nombre);

    }

    @RequestMapping(value = "/usuario/{nombre}/evento/inscrito", method = DELETE, produces = "application/json")
    public void cancelarInscripcionEvento(@PathVariable String nombre,
            @RequestBody int id) {

        Evento evento = gestionEventos.obtenerEvento(id);
        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.cancelarInscripcionEvento(eventoDTO, nombre);

    }

    @RequestMapping(value = "/usuario/{nombre}/evento/organizado", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosCelebrados(@PathVariable String nombre,
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

//    @RequestMapping(value="/evento", method=POST, produces="application/json")
//    public boolean crearEvento(@RequestBody EventoDTO evento){
//        
//        return gestionEventos.crearEvento(evento, evento.getOrganizador());
//
//        
//        
//    }
//      
}
