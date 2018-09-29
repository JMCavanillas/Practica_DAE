/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jabm9
 */
public interface ServiciosEvento {

    //Cambiar lo de Tipo para evtiar repetirlo en Evento y aqui
    /*
    Enum para restringir los tipos de eventos
     */
    public enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }

    List<Evento> buscarEventoTipo(Tipo tipo);

    List<Evento> buscarEventoPalabrasClave(List<String> palabras);

    Evento crearEvento(String lugar, Date fecha, String descripcion, int numeroMaxAsistentes);

    boolean cancelarEvento(Evento evento);

    boolean inscribirseEvento(Evento evento, Usuario usuario);

    boolean cancelarInscripcionEvento(Evento evento, Usuario usuario);

    List<Evento> verEventosInscritosCelebrados(Usuario usuario);

    List<Evento> verEventosInscritosFuturos(Usuario usuario);

    List<Evento> verEventosOrganizados(Usuario usuario);

    List<Evento> verEventosOrganizadosFuturos(Usuario usuario);

}
