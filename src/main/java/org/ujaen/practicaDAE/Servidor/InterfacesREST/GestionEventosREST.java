package org.ujaen.practicaDAE.Servidor.InterfacesREST;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

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
@CrossOrigin(origins = "http://localhost:8383")
@RequestMapping("/")
public class GestionEventosREST {

    @Autowired
    GestionEventos gestionEventos;

    @RequestMapping(value = "/eventos/{id}", method = GET, produces = "application/json")
    public ResponseEntity<EventoDTO> obtenerEvento(@PathVariable int id) {
        Evento evento = gestionEventos.obtenerEvento(id);
        if (evento != null) {
            EventoDTO eventoDTO = evento.toDTO();
            return new ResponseEntity<>(eventoDTO, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/organizados", method = POST, produces = "application/json")
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
    @RequestMapping(value = "/usuarios/{nombre}/eventos/organizados/{id}", method = DELETE, produces = "application/json")
    public ResponseEntity<Void> cancelarEvento(@PathVariable int id, @PathVariable String nombre) {

        EventoDTO evento = gestionEventos.buscarEventoID(id).toDTO();

        if (!evento.getOrganizador().equals(nombre)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        gestionEventos.cancelarEvento(evento, evento.getOrganizador());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(value = "/eventos/tipo/{tipo}", method = GET, produces = "application/json")
    public Pagina<EventoDTO> buscarEventoTipo(@PathVariable Evento.Tipo tipo,
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {

        // if (tipo.equals("CHARLA") || tipo.equals("CURSO") || tipo.equals("ACTIVIDAD_DEPORTIVA") || tipo.equals("VISITA_CULTURAL")) {
        List<EventoDTO> eventosTipo = gestionEventos.buscarEventoTipo(tipo);

        Pagina<EventoDTO> pagina = new Pagina<>(eventosTipo, page, size);

        return pagina;
        // }

        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //?

    @RequestMapping(value = "/eventos", method = GET, produces = "application/json")
    public Pagina<EventoDTO> buscarEvento(@RequestParam(required = false, defaultValue = "[]") List<String> palabras, @RequestParam(required = false, defaultValue = "NINGUNO") Evento.Tipo tipo,
            @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {

        List<EventoDTO> eventosTipo = new ArrayList<>();
        List<EventoDTO> eventosPalabras = gestionEventos.buscarEventoPalabrasClave(palabras);
        List<EventoDTO> eventosResponse = new ArrayList();
        
        //Caso 1: No se ha pasado tipo y hay una lista de palabras
        if(tipo==Evento.Tipo.NINGUNO && !palabras.isEmpty()){
            eventosResponse=eventosPalabras;
        }
        
        //Caso 2: Se ha pasado tipo y no lista de palabras
         if(tipo!=Evento.Tipo.NINGUNO){
            eventosResponse=gestionEventos.buscarEventoTipo(tipo);
        }
        
        if (tipo != Evento.Tipo.NINGUNO && !palabras.isEmpty()) {
            eventosTipo = gestionEventos.buscarEventoTipo(tipo);
            for (EventoDTO eventoPalabra : eventosPalabras) {
                //  EventoDTO encontrado=null;
                for (EventoDTO evento : eventosTipo) {
                    if (evento.getId() == eventoPalabra.getId()) {
                        eventosResponse.add(evento);
                        break;
                    }
                }

            }
        }

        Pagina<EventoDTO> pagina = new Pagina<>(eventosResponse, page, size);

        return pagina;

    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/inscritos", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosInscritos(@PathVariable String nombre,
            @RequestParam(value = "tiempo", required = false, defaultValue = "") String tiempo) {

        List<EventoDTO> eventosInscritos = new ArrayList<>();
        switch (tiempo) {
            case "todos":
                eventosInscritos.addAll(gestionEventos.verEventosInscritosFuturos(nombre));
                eventosInscritos.addAll(gestionEventos.verEventosInscritosCelebrados(nombre));
                break;
            case "pasado":
                eventosInscritos = gestionEventos.verEventosInscritosCelebrados(nombre);
                break;
            default:
                eventosInscritos = gestionEventos.verEventosInscritosFuturos(nombre);
                break;

        }

        return eventosInscritos;
    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/listaespera", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosEspera(@PathVariable String nombre) {

        List<EventoDTO> eventosInscritos = gestionEventos.verEventosListaEspera(nombre);

        return eventosInscritos;
    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/inscritos/{id}", method = POST, produces = "application/json")
    public boolean inscribirEvento(@PathVariable String nombre, @PathVariable int id) {

        Evento evento = gestionEventos.obtenerEvento(id);

        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.inscribirseEvento(eventoDTO, nombre);

        return true;

    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/inscritos/{id}", method = DELETE, produces = "application/json")
    public void cancelarInscripcionEvento(@PathVariable String nombre, @PathVariable int id) {

        Evento evento = gestionEventos.obtenerEvento(id);
        EventoDTO eventoDTO = evento.toDTO();
        gestionEventos.cancelarInscripcionEvento(eventoDTO, nombre);

    }

    @RequestMapping(value = "/usuarios/{nombre}/eventos/organizados", method = GET, produces = "application/json")
    public List<EventoDTO> verEventosOrganizado(@PathVariable String nombre,
            @RequestParam(value = "tiempo", required = false, defaultValue = "") String tiempo) {

        List<EventoDTO> eventosOrganizados = new ArrayList<>();
        switch (tiempo) {
            case "todos":
                eventosOrganizados.addAll(gestionEventos.verEventosOrganizados(nombre));
                eventosOrganizados.addAll(gestionEventos.verEventosOrganizadosFuturos(nombre));
                break;
            case "pasado":
                eventosOrganizados = gestionEventos.verEventosOrganizados(nombre);
                break;
            default:
                eventosOrganizados = gestionEventos.verEventosOrganizadosFuturos(nombre);

        }

        return eventosOrganizados;
    }

}
