/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Interfaces;

import java.util.Date;
import java.util.List;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Evento;
import org.ujaen.practicaDAE.Servidor.Evento.Tipo;
import org.ujaen.practicaDAE.Servidor.Usuario;

/**
 *
 * @author jabm9
 */
public interface ServiciosEvento {

    List<EventoDTO> buscarEventoTipo(Tipo tipo);

    List<EventoDTO> buscarEventoPalabrasClave(List<String> palabras);

    EventoDTO crearEvento(String lugar, Date fecha, Evento.Tipo tipo, String descripcion, int numeroMaxAsistentes, UsuarioDTO usuario);

    boolean cancelarEvento(EventoDTO evento, UsuarioDTO usuario);

    boolean inscribirseEvento(EventoDTO evento, UsuarioDTO usuario);

    boolean cancelarInscripcionEvento(EventoDTO evento, UsuarioDTO usuario);

    List<EventoDTO> verEventosInscritosCelebrados(UsuarioDTO usuario);

    List<EventoDTO> verEventosInscritosFuturos(UsuarioDTO usuario);

    List<EventoDTO> verEventosOrganizados(UsuarioDTO usuario);

    List<EventoDTO> verEventosOrganizadosFuturos(UsuarioDTO usuario);

}