/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Excepciones.ExcepcionCancelarEventoNoOrganizado;
import org.ujaen.practicaDAE.Excepciones.ExcepcionEventoYaCelebrado;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaInscritoEvento;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
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

    private static final DateFormat sdf
            = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    Map<Integer, Evento> eventos;

    /**
     * Constructor por defecto
     */
    public GestionEventos() {
        eventos = new TreeMap<>();
    }

    protected Evento obtenerEvento(int id) {
        return eventos.get(id);
    }

    @Override
    public List<EventoDTO> buscarEventoTipo(Evento.Tipo tipo) {

        List<EventoDTO> tmp = new ArrayList<>();
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {

            if (entry.getValue().getTipo().equals(tipo)) {

                tmp.add(entry.getValue().toDTO());
            }

        }

        return tmp;
    }

    @Override
    public List<EventoDTO> buscarEventoPalabrasClave(String[] palabras) {
        List<EventoDTO> tmp = new ArrayList<>();

        //A lo mejor se podria cambiar para las primeras
        //posiciones de la lista la ocupen los eventos
        //que tengan mas coincidencias de palabras clave
        for (Map.Entry<Integer, Evento> entry : eventos.entrySet()) {
            for (String palabra : palabras) {
                if (entry.getValue().getDescripcion().toLowerCase()
                        .contains(palabra.toLowerCase())) {
                    tmp.add(entry.getValue().toDTO());
                }
            }
        }

        return tmp;
    }

    @Override
    public EventoDTO crearEvento(EventoDTO evento, int sec_token)
            throws Exception {
        //Añade el evento creado a la lista de eventos creados del usuario
        Evento nuevo_evento = gestionusuarios.verificaToken(sec_token)
                .creaEvento(evento);

        eventos.put(nuevo_evento.getId(), nuevo_evento);

        return nuevo_evento.toDTO();
    }

    @Override
    public boolean cancelarEvento(EventoDTO evento, int sec_token)
            throws Exception {
        Date fecha_actual = new Date();
        if (evento.getFecha().before(fecha_actual)) {
            throw new ExcepcionEventoYaCelebrado();
        }
        
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());

        Evento resultado = r_usuario.cancelaEvento(r_evento);

        if (resultado != null) {

            for (int i = 0; i < r_evento.usuariosInscritos.size(); i++) {
                r_evento.usuariosInscritos.get(i).cancelarInscripcion(r_evento);
            }

            eventos.remove(r_evento.getId());
            return true;

        } else {
            throw new ExcepcionCancelarEventoNoOrganizado();
        }

    }

    @Override
    public boolean inscribirseEvento(EventoDTO evento, int sec_token)
            throws Exception {
        Date fecha_actual = new Date();
        if (evento.getFecha().before(fecha_actual)) {
            throw new ExcepcionEventoYaCelebrado();
        }

        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());

        if (r_usuario.inscribirseEvento(r_evento)) {
            return true;
        } else {
            throw new ExcepcionUsuarioYaInscritoEvento();
        }

    }

    @Override
    public boolean cancelarInscripcionEvento(EventoDTO evento, int sec_token)
            throws Exception {

        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
        Evento r_evento = obtenerEvento(evento.getId());

        return r_usuario.cancelarInscripcion(r_evento);

    }

    @Override
    public List<EventoDTO> verEventosInscritosCelebrados(int sec_token)
            throws Exception {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
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
    public List<EventoDTO> verEventosInscritosFuturos(int sec_token)
            throws Exception {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
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
    public List<EventoDTO> verEventosOrganizados(int sec_token)
            throws Exception {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
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
    public List<EventoDTO> verEventosOrganizadosFuturos(int sec_token)
            throws Exception {
        Usuario r_usuario = gestionusuarios.verificaToken(sec_token);
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

//    public String eventoString(Evento e) 
//    {
//        String tmp = "Evento:" + e.getId() + e.getFecha() + e.getLugar() 
//                + e.getTipo() + e.getDescripcion() + e.getOrganizador() 
//                + e.getNumeroMaxAsistentes();
//        return tmp;
//    }
//    @Override
//    public void mostrarEventos() {
//        for (int i = 0; i < eventos.size(); i++) {
//            System.out.println(eventoString(eventos.get(i)));
//        }
//    }
}
