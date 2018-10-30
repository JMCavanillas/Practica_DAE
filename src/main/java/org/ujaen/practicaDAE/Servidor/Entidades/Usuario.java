package org.ujaen.practicaDAE.Servidor.Entidades;

import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */

@Entity
public class Usuario {

    @Id
    private String nombre;
    private String clave;
    private int token;

    @ManyToMany(mappedBy="usuariosInscritos")
    private List<Evento> eventosInscritos;
    
    @ManyToMany(mappedBy="listaEspera", fetch=FetchType.LAZY)
    private List<Evento> eventosInscritosEspera;
    
    @OneToMany(mappedBy="organizador", fetch=FetchType.LAZY)
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
        if (!eventosInscritos.contains(evento) && !eventosInscritosEspera.contains(evento))
        {
            //eventosInscritos.add(evento);
            
            if (evento.getUsuariosInscritos().size() < evento.getNumeroMaxAsistentes())
            {
                eventosInscritos.add(evento);
                evento.getUsuariosInscritos().add(this);
            } else
            {    
                getEventosInscritosEspera().add(evento);
                evento.getListaEspera().add(this);
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * Cancela la inscripción a un evento
     * 
     * @param evento
     * @return True si se ha cancelado con exito
     *         False en otro caso
     */
    public boolean cancelarInscripcion(Evento evento)
    {
        if(eventosInscritos.remove(evento)){
            evento.usuariosInscritos.remove(this);
            
             return true;
            
        }else if(getEventosInscritosEspera().remove(evento)){
            evento.listaEspera.remove(this);
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

    /**
     * @return the eventosInscritosEspera
     */
    public List<Evento> getEventosInscritosEspera() {
        return eventosInscritosEspera;
    }
}
