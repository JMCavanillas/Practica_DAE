/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosEvento;

/**
 *
 * @author macosx
 */
@Component
public class GestionEventos implements ServiciosEvento {

    @Autowired
    GestionUsuarios gestionusuarios;
    
    List<Evento> eventos = new ArrayList<>();
    
    

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
                if (evento.getDescripcion().contains(palabra)) {

                    tmp.add(evento.toDTO());
                }
            }

        }

        return tmp;
    }

    @Override
    public EventoDTO crearEvento(String lugar, Date fecha, Evento.Tipo tipo, String descripcion, int numeroMaxAsistentes, UsuarioDTO usuario) {
        Evento tmp=new Evento(fecha, lugar, tipo, descripcion, numeroMaxAsistentes);
        eventos.add(tmp);
       
       //Añade el evento creado a la lista de eventos creados del usuario
       gestionusuarios.buscarUsuario(usuario.getNombre()).añadirEventoCreado(tmp);
       

        return tmp.toDTO();
    }

    @Override
    public boolean cancelarEvento(EventoDTO evento, UsuarioDTO usuario) {
    
   
        //Añadir identificador para eventos..
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean inscribirseEvento(EventoDTO evento, UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cancelarInscripcionEvento(EventoDTO evento, UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EventoDTO> verEventosInscritosCelebrados(UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EventoDTO> verEventosInscritosFuturos(UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EventoDTO> verEventosOrganizados(UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EventoDTO> verEventosOrganizadosFuturos(UsuarioDTO usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

