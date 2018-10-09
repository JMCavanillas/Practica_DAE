/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;

/**
 *
 * @author macosx
 */
public class Usuario {

    private String nombre;
    private String clave;
    private int token;

    private List<Evento> eventosInscritos;
    private List<Evento> eventosCreados;

    public Usuario(String nombre, String contraseña) 
    {
        this.nombre = nombre;
        this.clave = contraseña;
        
        this.eventosCreados = new ArrayList<>();
        this.eventosInscritos = new ArrayList<>();
    }

    /**
     * @return the nombre
     */
    public String getNombre() 
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    /**
     * @return the clave
     */
    public String getClave() 
    {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) 
    {
        this.clave = clave;
    }

    /**
     * @return the eventosInscritos
     */
    public List<Evento> getEventosInscritos() 
    {
        return eventosInscritos;
    }

    /**
     * @return the eventosCreados
     */
    public List<Evento> getEventosCreados() 
    {
        return eventosCreados;
    }

    /**
     * Genera un DTO a partir del usuario
     * @return 
     */
    public UsuarioDTO toDTO() 
    {
        return new UsuarioDTO(nombre);
    }

    /**
     * Crea un evento nuevo
     * @param fecha
     * @param lugar
     * @param tipo_evento
     * @param descripcion
     * @param max_asistentes
     * @return 
     */
    public Evento creaEvento(Date fecha, String lugar, Evento.Tipo tipo_evento, 
            String descripcion, int max_asistentes)
    {
        Evento evento = new Evento(this, fecha, lugar, tipo_evento, descripcion,
                max_asistentes);
        eventosCreados.add(evento);
        
        return evento;
    }
    
    /**
     * Crea un nuevo evento a partir de un DTO
     * @param evento 
     * @return  
     */
    public Evento creaEvento(EventoDTO evento)
    {
        return creaEvento(evento.getFecha(), evento.getLugar(), evento.getTipo(), 
                evento.getDescripcion(), evento.getNumeroMaxAsistentes());
    }
    
    /**
     * Cancela un evento si es organizador de este
     * @param evento
     * @return el evento cancelado, null si no se puede cancelar
     */
    public Evento cancelaEvento(Evento evento)
    {
        if (evento.getOrganizador() == this)
        {
            this.eventosCreados.remove(evento);
            return evento;
        }
        
        return null;
    }
    
    /**
     * Inscribe un usuario, en un evento
     * @param evento
     * @return 
     */
    public boolean inscribirseEvento(Evento evento)
    {
        if (!eventosInscritos.contains(evento))
        {
            eventosInscritos.add(evento);
            
            if (evento.usuariosInscritos.size() < evento.getNumeroMaxAsistentes())
                evento.usuariosInscritos.add(this);
            else
                evento.listaEspera.add(this);
            
            return true;
        }
        
        return false;
    }
    
    public boolean cancelarSuscripcion(Evento evento)
    {
        if (eventosInscritos.remove(evento))
        {
            if(!evento.usuariosInscritos.remove(this))
                return evento.listaEspera.remove(this);
            
            return true;
        }
        return false;
    }

    /**
     * @return the token
     */
    public int getToken()
    {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(int token)
    {
        this.token = token;
    }
}
