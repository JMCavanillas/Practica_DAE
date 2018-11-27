package org.ujaen.practicaDAE.Servidor.Interfaces;

import java.util.List;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.Entidades.Evento.Tipo;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
public interface ServiciosEvento {

    /**
     * Devuelve una lista de Eventos que cumplan con el tipo de evento 
     * especificado
     * 
     * @param tipo Tipo de evento especificado
     * @return Lista de eventos
     */
    List<EventoDTO> buscarEventoTipo(Tipo tipo);

    /**
     * Devuelve una lista de eventos por que contengan una serie de 
     * palabras clave
     * 
     * @param palabras Conjunto de palabras clave
     * @return Lista de eventos
     */
    List<EventoDTO> buscarEventoPalabrasClave(List<String> palabras);
    
    /**
     * Crea un nuevo evento
     * 
     * @param evento Evento a crear
     * @param nombre

     * @return El evento creado en caso positivo
     */
    Boolean crearEvento(EventoDTO evento, String nombre);

    /**
     * Cancela un evento
     * 
     * @param evento Evento que cancelar
     * @param nombre
    
     */
    void cancelarEvento(EventoDTO evento, String nombre);

    /**
     * Inscribe al usuario en un evento
     * 
     * @param evento Evento al que inscribirse
     * @param nombre
     */
    void inscribirseEvento(EventoDTO evento, String nombre);

    /**
     * Cancela la inscripción en un evento
     * 
     * @param evento Evento del cual se cancelará la suscripción
     * @param nombre
     * @return True si la inscripción se ha cancelado con éxito, 
     *         False en otro caso
     */
    boolean cancelarInscripcionEvento(EventoDTO evento, String nombre);

    /**
     * Lista los eventos inscritos por el usuario ya celebrados
     * 
     * @param nombre
     * @return Lista de eventos celebrados
     */
    List<EventoDTO> verEventosInscritosCelebrados(String nombre);

    /**
     * Lista los eventos inscritos por el usuario por celebrar
     * 
     * @param nombre
     * @return Lista de proximos eventos inscritos
     */
    List<EventoDTO> verEventosInscritosFuturos(String nombre);

    /**
     * Lista los eventos organizados por el usuario ya celebrados
     * 
     * @param nombre
     * @return Lista de eventos organizados celebrados
     */
    List<EventoDTO> verEventosOrganizados(String nombre);

    /**
     * Lista los eventos organizados por el usuario por celebrar
     * 
     * @param nombre
     * @return Lista de eventos organizados por celebrar
     */
    List<EventoDTO> verEventosOrganizadosFuturos(String nombre);
    
    
    /**
     * Lista los eventos en los que el usuario está en lista de espera
     * 
     * @param nombre
     * @return  Lista de eventos organizados por celebrar
     */
    List<EventoDTO> verEventosListaEspera(String nombre);

}
