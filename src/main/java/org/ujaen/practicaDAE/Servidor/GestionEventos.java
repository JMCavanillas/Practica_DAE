/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosEvento;

/**
 * Clase que gestiona una coleccion de eventos y sus servicios relacionados
 *
 * @author macosx
 */
@Component
public class GestionEventos implements ServiciosEvento {

    @Autowired
    GestionUsuarios gestionusuarios;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    List<Evento> eventos = new ArrayList<>();

    public Evento obtenerEvento(int id) {
        for (Evento evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }
        return null;
    }

    @Override
    public List<EventoDTO> buscarEventoTipo(Evento.Tipo tipo) {

        List<EventoDTO> tmp = new ArrayList<>();

        for (Evento evento : eventos) {
            if (evento.getTipo().equals(tipo)) {

                tmp.add(evento.toDTO());
            }
        }

        return tmp;
    }

    @Override
    public List<EventoDTO> buscarEventoPalabrasClave(List<String> palabras) {
        List<EventoDTO> tmp = new ArrayList<>();

        //A lo mejor se podria cambiar para las primeras
        //posiciones de la lista la ocupen los eventos
        //que tengan mas coincidencias de palabras clave
        for (Evento evento : eventos) {
            for (String palabra : palabras) {
                if (evento.getDescripcion().toLowerCase().contains(palabra)) {

                    tmp.add(evento.toDTO());
                }
            }

        }

        return tmp;
    }

    /**
     * Crea un nuevo Evento
     *
     * @param evento
     * @param usuario
     * @return Un DTO con el evento creado, un evento nulo en caso de que no se
     * haya creado
     */
    @Override
    public EventoDTO crearEvento(EventoDTO evento, UsuarioDTO usuario) {
        //Añade el evento creado a la lista de eventos creados del usuario
        Evento nuevo_evento = gestionusuarios.buscarUsuario(usuario.getNombre())
                .creaEvento(evento);

        eventos.add(nuevo_evento);

        return nuevo_evento.toDTO();
    }

    @Override
    public boolean cancelarEvento(EventoDTO evento, UsuarioDTO usuario) {
        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
        Evento r_evento = obtenerEvento(evento.getId());

        Evento resultado = r_usuario.cancelaEvento(r_evento);

        if (resultado != null) {
            
            
            for(int i=0;i<r_evento.usuariosInscritos.size();i++){
                 r_evento.usuariosInscritos.get(i).cancelarSuscripcion(r_evento);
            }
 
            
            eventos.remove(r_evento);
            
        }

        return resultado != null;
    }

    @Override
    public boolean inscribirseEvento(EventoDTO evento, UsuarioDTO usuario) {

        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
        Evento r_evento = obtenerEvento(evento.getId());

        return r_usuario.inscribirseEvento(r_evento);

    }

    @Override
    public boolean cancelarInscripcionEvento(EventoDTO evento, UsuarioDTO usuario) {
       

        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
        Evento r_evento = obtenerEvento(evento.getId());

        return r_usuario.cancelarSuscripcion(r_evento);

    }

    @Override
    public List<EventoDTO> verEventosInscritosCelebrados(UsuarioDTO usuario) {
        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
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
    public List<EventoDTO> verEventosInscritosFuturos(UsuarioDTO usuario) {
        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
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
    public List<EventoDTO> verEventosOrganizados(UsuarioDTO usuario) {
        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
        List<Evento> eventos_organizados = r_usuario.getEventosCreados();

        List<EventoDTO> eventos_organizados_celebrados = new ArrayList<>();

        Date fecha_actual = new Date();
        //System.out.println(sdf.format(fecha_actual));

        for (Evento evento : eventos_organizados) {

            if (evento.getFecha().before(fecha_actual)) {
                System.out.println("true");
                eventos_organizados_celebrados.add(evento.toDTO());
            }
        }

        return eventos_organizados_celebrados;
    }

    @Override
    public List<EventoDTO> verEventosOrganizadosFuturos(UsuarioDTO usuario) {
        Usuario r_usuario = gestionusuarios.buscarUsuario(usuario.getNombre());
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

    public String eventoString(Evento e) {
        String tmp = "Evento:" + e.getId() + e.getFecha() + e.getLugar() + e.getTipo() + e.getDescripcion() + e.getOrganizador() + e.getNumeroMaxAsistentes();
        return tmp;
    }



    @Override
    public void mostrarEventos() {
        for (int i = 0; i < eventos.size(); i++) {
            System.out.println(eventoString(eventos.get(i)));
        }
    }

}
