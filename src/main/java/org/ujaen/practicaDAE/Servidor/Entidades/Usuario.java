package org.ujaen.practicaDAE.Servidor.Entidades;

import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
    private String correo;
    private int token;

    @ManyToMany(mappedBy = "usuariosInscritos")
    private Set<Evento> eventosInscritos;

    @ManyToMany(mappedBy = "listaEspera")
    private List<Evento> eventosInscritosEspera;

    @OneToMany(mappedBy = "organizador")
    private List<Evento> eventosCreados;

    public Usuario(String nombre, String contraseña, String correo) {
        this.nombre = nombre;
        this.clave = contraseña;
        this.correo = correo;
        this.eventosCreados = new ArrayList<>();
        this.eventosInscritos = new TreeSet<>();
    }

    public Usuario() {

    }

    /**
     * Genera un DTO a partir del usuario
     *
     * @return
     */
    public UsuarioDTO toDTO() {
        return new UsuarioDTO(nombre);
    }

    /**
     * Crea un evento nuevo
     *
     * @param fecha
     * @param lugar
     * @param tipo_evento
     * @param descripcion
     * @param max_asistentes
     * @return
     */
    public Evento creaEvento(Date fecha, String lugar, Evento.Tipo tipo_evento,
            String descripcion, int max_asistentes) {
        Evento evento = new Evento(this, fecha, lugar, tipo_evento, descripcion,
                max_asistentes);
        eventosCreados.add(evento);

        return evento;
    }

    /**
     * Crea un nuevo evento a partir de un DTO
     *
     * @param evento
     * @return
     */
    public Evento creaEvento(EventoDTO evento) {
        return creaEvento(evento.getFecha(), evento.getLugar(), evento.getTipo(),
                evento.getDescripcion(), evento.getNumeroMaxAsistentes());
    }

    /**
     * Cancela un evento si es organizador de este
     *
     * @param evento
     * @return el evento cancelado, null si no se puede cancelar
     */
    public Evento cancelaEvento(Evento evento) {
        if (evento.getOrganizador().getNombre().equals(nombre)) {
            this.eventosCreados.remove(evento);
            return evento;
        }

        return null;
    }

    /**
     * Inscribe un usuario, en un evento
     *
     * @param evento
     * @return
     */
    public boolean inscribirseEvento(Evento evento) {
        if (!comprobarInscripcion(evento.getId())) {
            return false;

        } else {
            //eventosInscritos.add(evento);

            if (evento.getUsuariosInscritos().size() < evento.getNumeroMaxAsistentes()) {
                eventosInscritos.add(evento);
                evento.getUsuariosInscritos().add(this);
            } else {
                getEventosInscritosEspera().add(evento);

                evento.getListaEspera().put(new Date(), this);
            }
            return true;
        }

    }

    /**
     * Cancela la inscripción a un evento
     *
     * @param evento
     * @return True si se ha cancelado con exito False en otro caso
     */
    public boolean cancelarInscripcion(Evento evento) {
        
        
        for(Evento e: eventosInscritos){
            if(e.getId()==evento.getId()){
                 eventosInscritos.remove(e);
                
                return true;
            }
        }
        
        return false;
//        if(evento.usuariosInscritos.contains(this)){
//            evento.usuariosInscritos.remove(this);
//            return true;
//        }
//        return false;
        
        

//        Evento tmp = null;
//        for (Evento e : eventosInscritos) {
//            if (e.getId() == evento.getId()) {
//                tmp = e;
//            }
//        }
//        if (tmp != null) {
//            eventosInscritos.remove(tmp);
//            tmp.usuariosInscritos.remove(this);
//        }
//
//        for (Evento e2 : eventosInscritosEspera) {
//            if (e2.getId() == evento.getId()) {
//                tmp = e2;
//            }
//        }
//        if (tmp != null) {
//            eventosInscritosEspera.remove(tmp);
//            Map.Entry<Date,Usuario> entry=tmp.listaEspera.entrySet().iterator().next();
//            
//            tmp.listaEspera.remove(entry.getKey());
//        }

//        if (comprobarInscripcion(evento.getId())) {
//            return false;
//
//        }else{
//            if (this.eventosInscritos.remove(evento)) {
//            evento.usuariosInscritos.remove(this);
//
//            
//
//         } else if (this.eventosInscritosEspera.remove(evento)) {
//            Map.Entry<Date,Usuario> entry=evento.listaEspera.entrySet().iterator().next();
//            
//            evento.listaEspera.remove(entry.getKey());
//            
//          }
//
//        return true;
//        }
//        if (tmp == null) {
//            return false;
//        } else {
//            return true;
//        }
    }

    /**
     *
     * @param id el id del evento que se quiere comprobar
     * @return false si ya está inscrito en el evento, true en caso contrario
     */
    private boolean comprobarInscripcion(int id) {
        for (Evento e : eventosInscritos) {
            if (e.getId() == id) {
                return false;
            }
        }

        for (Evento eEspera : eventosInscritosEspera) {
            if (eEspera.getId() == id) {
                return false;
            }
        }

        return true;

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the eventosInscritos
     */
    public Set<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    /**
     * @return the eventosCreados
     */
    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }

    /**
     * @return the token
     */
    public int getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(int token) {
        this.token = token;
    }

    /**
     * @return the eventosInscritosEspera
     */
    public List<Evento> getEventosInscritosEspera() {
        return eventosInscritosEspera;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
