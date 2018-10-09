/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Interfaces;

import java.util.List;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.Evento.Tipo;

/**
 *
 * @author jabm9
 */
public interface ServiciosEvento {

    List<EventoDTO> buscarEventoTipo(Tipo tipo);

    List<EventoDTO> buscarEventoPalabrasClave(String[] palabras);

    // TODO - Tunear Excepciones
    
    EventoDTO crearEvento(EventoDTO evento, int sec_token)
            throws Exception;

    boolean cancelarEvento(EventoDTO evento, int sec_token)
            throws Exception;

    boolean inscribirseEvento(EventoDTO evento, int sec_token)
            throws Exception;

    boolean cancelarInscripcionEvento(EventoDTO evento, int sec_token)
            throws Exception;

    List<EventoDTO> verEventosInscritosCelebrados(int sec_token)
            throws Exception;

    List<EventoDTO> verEventosInscritosFuturos(int sec_token)
            throws Exception;

    List<EventoDTO> verEventosOrganizados(int sec_token)
            throws Exception;

    List<EventoDTO> verEventosOrganizadosFuturos(int sec_token)
            throws Exception;
    
//    void mostrarEventos();

}
