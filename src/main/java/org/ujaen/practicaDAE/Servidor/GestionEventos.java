package org.ujaen.practicaDAE.Servidor;

import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;
import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Excepciones.ExcepcionCancelarEventoNoOrganizado;
import org.ujaen.practicaDAE.Excepciones.ExcepcionCancelarInscripcion;
import org.ujaen.practicaDAE.Excepciones.ExcepcionEventoYaCelebrado;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaInscritoEvento;
import org.ujaen.practicaDAE.Servidor.Correo.ServicioCorreo;
import org.ujaen.practicaDAE.Servidor.DAOs.EventoDAO;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosEvento;

/**
 * Clase que gestiona una coleccion de eventos y sus servicios relacionados
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Component
public class GestionEventos implements ServiciosEvento {

    @Autowired
    GestionUsuarios gestionusuarios;

    @Autowired
    EventoDAO eventoDAO;

    @Autowired
    private ServicioCorreo servicioCorreo;



    /**
     * Constructor por defecto
     */
    public GestionEventos() {
        
    }

    protected Evento obtenerEvento(int id) {
        return eventoDAO.buscarEventoID(id);
    }

    @Override
    public List<EventoDTO> buscarEventoTipo(Evento.Tipo tipo) {
        List<EventoDTO> eventosDTO = new ArrayList<>();
        List<Evento> eventos = eventoDAO.buscarEventoTipo(tipo);
        for (Evento e : eventos) {
            eventosDTO.add(e.toDTO());
        }

        return eventosDTO;

    }

    @Override
    public List<EventoDTO> buscarEventoPalabrasClave(List<String> palabras) {
        List<EventoDTO> eventosDTO = new ArrayList<>();
        List<Evento> eventos = eventoDAO.buscarEventoPalabraClave(palabras);
        for (Evento e : eventos) {
            eventosDTO.add(e.toDTO());
        }

        return eventosDTO;
    }

    @Override
    public Boolean crearEvento(EventoDTO evento, int sec_token) {

        Date fecha_actual = new Date();
        if (evento.getFecha().before(fecha_actual)) {
            throw new ExcepcionEventoYaCelebrado();
        }
        Usuario usuario = gestionusuarios.verificaToken(sec_token);
        //Evento nuevo_evento = usuario.creaEvento(evento);
        
        eventoDAO.crearEvento(evento.getFecha(), evento.getLugar(), evento.getTipo(), evento.getDescripcion(), evento.getNumeroMaxAsistentes(), usuario.getNombre());

        return true;
    }

    @Override
    public void cancelarEvento(EventoDTO evento, int sec_token) {
        Date fecha_actual = new Date();
        if (evento.getFecha().before(fecha_actual)) {
            throw new ExcepcionEventoYaCelebrado();
        }

        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());

        Evento resultado = r_usuario.cancelaEvento(r_evento);

        if (resultado != null) {
            eventoDAO.borraEvento(r_evento);
        } else {
            throw new ExcepcionCancelarEventoNoOrganizado();
        }

    }

    @Override
    public void inscribirseEvento(EventoDTO evento, int sec_token) {
        Date fecha_actual = new Date();
        if (evento.getFecha().before(fecha_actual)) {
            throw new ExcepcionEventoYaCelebrado();
        }

        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());
        
        

        if (!r_usuario.inscribirseEvento(r_evento)) {
            throw new ExcepcionUsuarioYaInscritoEvento();
        } else {
            eventoDAO.actualizarEvento(r_evento);
        }

    }

    @Override
    public boolean cancelarInscripcionEvento(EventoDTO evento, int sec_token) {

        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());
        r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        Usuario usuarioEnviarCorreo=new Usuario();
        
        try {
            eventoDAO.cancelarInscripcion(r_evento, r_usuario, usuarioEnviarCorreo);

            if(!usuarioEnviarCorreo.getNombre().isEmpty()){
                servicioCorreo.sendSimpleMessage(usuarioEnviarCorreo.getCorreo(), "Aceptado en el evento "+r_evento.getDescripcion(),
                        usuarioEnviarCorreo.getNombre(), r_evento.getDescripcion(), r_evento.getFecha(), r_evento.getLugar());
            }
        } catch (RuntimeException ex){
            return false;
        }
                    
        return true;
    }

    @Override
    public List<EventoDTO> verEventosInscritosCelebrados(int sec_token) {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        List<Evento> eventos_inscritos = r_usuario.getEventosInscritos();
        List<EventoDTO> eventos_inscritos_celebrados = new ArrayList<>();
        Date fecha_actual = new Date();

        for (Evento evento : eventos_inscritos) {
            if (evento.getFecha().before(fecha_actual)) {
                eventos_inscritos_celebrados.add(evento.toDTO());
            }
        }

        return eventos_inscritos_celebrados;
    }

    @Override
    public List<EventoDTO> verEventosInscritosFuturos(int sec_token) {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        List<Evento> eventos_inscritos = r_usuario.getEventosInscritos();
        List<EventoDTO> eventos_inscritos_futuros = new ArrayList<>();
        Date fecha_actual = new Date();

        for (Evento evento : eventos_inscritos) {
            if (!evento.getFecha().before(fecha_actual)) {
                eventos_inscritos_futuros.add(evento.toDTO());
            }
        }

        return eventos_inscritos_futuros;
    }

    @Override
    public List<EventoDTO> verEventosOrganizados(int sec_token) {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        List<Evento> eventos_organizados = r_usuario.getEventosCreados();

        List<EventoDTO> eventos_organizados_celebrados = new ArrayList<>();

        Date fecha_actual = new Date();

        for (Evento evento : eventos_organizados) {

            if (evento.getFecha().before(fecha_actual)) {
                eventos_organizados_celebrados.add(evento.toDTO());
            }
        }

        return eventos_organizados_celebrados;
    }

    @Override
    public List<EventoDTO> verEventosOrganizadosFuturos(int sec_token) {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
       r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        List<Evento> eventos_organizados = r_usuario.getEventosCreados();
        List<EventoDTO> eventos_organizados_futuros = new ArrayList<>();
        Date fecha_actual = new Date();

        for (Evento evento : eventos_organizados) {
            if (!evento.getFecha().before(fecha_actual)) {
                eventos_organizados_futuros.add(evento.toDTO());
            }
        }

        return eventos_organizados_futuros;
    }

    @Override
    public List<EventoDTO> verEventosListaEspera(int sec_token) {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        r_usuario = gestionusuarios.getUsuarioDAO().buscarUsuario(r_usuario.getNombre());
        List<Evento> eventos_inscritos_lista_espera = r_usuario.getEventosInscritosEspera();
        List<EventoDTO> eventos_inscritos_lista_espera_DTO = new ArrayList<>();

        for (Evento evento : eventos_inscritos_lista_espera) {
            eventos_inscritos_lista_espera_DTO.add(evento.toDTO());
        }

//        List<EventoDTO> eventos_inscritos_lista_espera = new ArrayList<>();
//
//        for (Evento evento : eventos_inscritos) {
//            if (evento.getListaEspera().contains(r_usuario)) {
//                eventos_inscritos_lista_espera.add(evento.toDTO());
//            }
//        }
        return eventos_inscritos_lista_espera_DTO;
    }

}
