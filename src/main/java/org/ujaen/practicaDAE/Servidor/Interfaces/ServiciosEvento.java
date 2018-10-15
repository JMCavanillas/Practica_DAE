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
    List<EventoDTO> buscarEventoPalabrasClave(String[] palabras);
    
    /**
     * Crea un nuevo evento
     * 
     * @param evento Evento a crear
     * @param sec_token Token de seguridad identificativo del usuario
     * @return El evento creado en caso positivo
     */
    EventoDTO crearEvento(EventoDTO evento, int sec_token);

    /**
     * Cancela un evento
     * 
     * @param evento Evento que cancelar
     * @param sec_token Token de seguridad identificativo del usuario
     */
    void cancelarEvento(EventoDTO evento, int sec_token);

    /**
     * Inscribe al usuario en un evento
     * 
     * @param evento Evento al que inscribirse
     * @param sec_token Token de seguridad identificativo del usuario
     */
    void inscribirseEvento(EventoDTO evento, int sec_token);

    /**
     * Cancela la inscripción en un evento
     * 
     * @param evento Evento del cual se cancelará la suscripción
     * @param sec_token Token de seguridad identificativo del usuario
     * @return True si la inscripción se ha cancelado con éxito, 
     *         False en otro caso
     */
    boolean cancelarInscripcionEvento(EventoDTO evento, int sec_token);

    /**
     * Lista los eventos inscritos por el usuario ya celebrados
     * 
     * @param sec_token Token de seguridad identificativo del usuario
     * @return Lista de eventos celebrados
     */
    List<EventoDTO> verEventosInscritosCelebrados(int sec_token);

    /**
     * Lista los eventos inscritos por el usuario por celebrar
     * 
     * @param sec_token Token de seguridad identificativo del usuario
     * @return Lista de proximos eventos inscritos
     */
    List<EventoDTO> verEventosInscritosFuturos(int sec_token);

    /**
     * Lista los eventos organizados por el usuario ya celebrados
     * 
     * @param sec_token Token de seguridad identificativo del usuario
     * @return Lista de eventos organizados celebrados
     */
    List<EventoDTO> verEventosOrganizados(int sec_token);

    /**
     * Lista los eventos organizados por el usuario por celebrar
     * 
     * @param sec_token Token de seguridad identificativo del usuario
     * @return Lista de eventos organizados por celebrar
     */
    List<EventoDTO> verEventosOrganizadosFuturos(int sec_token);
    
    
    /**
     * Lista los eventos en los que el usuario está en lista de espera
     * 
     * @param sec_token Token de seguridad identificativo del usuario
     * @return  Lista de eventos organizados por celebrar
     */
    List<EventoDTO> verEventosListaEspera(int sec_token);

}
