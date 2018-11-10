package org.ujaen.practicaDAE.Servidor.Entidades;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;

/**
 * Clase que encapsula el comportamiento de un evento
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Entity
public class Evento {

    /*
     * Enum para restringir los tipos de eventos
     */
    public enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }

    static int secuenciaID = 0;
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Usuario organizador;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String lugar;
    private Tipo tipo;
    private String descripcion;
    private int numeroMaxAsistentes;

    @Version
    int version;

    @ManyToMany
    protected Set<Usuario> usuariosInscritos;

    @ManyToMany
    protected Map<Date, Usuario> listaEspera;

    /**
     * Constructor orientado a DTOs
     *
     * @param id
     * @param fecha
     * @param lugar
     * @param tipo
     * @param descripcion
     * @param numeroMaxAsistentes
     */
    public Evento(int id, Date fecha, String lugar, Tipo tipo,
            String descripcion, int numeroMaxAsistentes) {
        this.id = id;
        this.fecha = fecha;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.numeroMaxAsistentes = numeroMaxAsistentes;

    }

    public Evento() {

    }

    /**
     * Constructor por defecto
     *
     * @param organizador
     * @param fecha
     * @param lugar
     * @param tipo
     * @param descripcion
     * @param numeroMaxAsistentes
     */
    public Evento(Usuario organizador, Date fecha, String lugar, Tipo tipo,
            String descripcion, int numeroMaxAsistentes) {
//        id = secuenciaID;
//        secuenciaID++;

        this.organizador = organizador;
        this.fecha = fecha;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.numeroMaxAsistentes = numeroMaxAsistentes;

        // Inicializamos las listas de usuarios
        this.usuariosInscritos = new TreeSet<>();
        this.listaEspera = new TreeMap<>();

    }

    public Evento(EventoDTO eventoDTO) {
        this.organizador = null;
        this.fecha = eventoDTO.getFecha();
        this.lugar = eventoDTO.getLugar();
        this.tipo = eventoDTO.getTipo();
        this.descripcion = eventoDTO.getDescripcion();
        this.numeroMaxAsistentes = eventoDTO.getNumeroMaxAsistentes();

        // Inicializamos las listas de usuarios
        this.usuariosInscritos = new TreeSet<>();
        this.listaEspera = new TreeMap<>();
    }



    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the numeroMaxAsistentes
     */
    public int getNumeroMaxAsistentes() {
        return numeroMaxAsistentes;
    }

    /**
     * @param numeroMaxAsistentes the numeroMaxAsistentes to set
     */
    public void setNumeroMaxAsistentes(int numeroMaxAsistentes) {
        this.numeroMaxAsistentes = numeroMaxAsistentes;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Genera un DTO a partir del evento
     *
     * @return
     */
    public EventoDTO toDTO() {
        return new EventoDTO(id, fecha, lugar, tipo,
                descripcion, numeroMaxAsistentes);
    }

    /**
     * @return the organizador
     */
    public Usuario getOrganizador() {
        return organizador;
    }

    /**
     * @return the usuariosInscritos
     */
    public Set<Usuario> getUsuariosInscritos() {

        return usuariosInscritos;
    }

    /**
     * @return the listaEspera
     */
    public Map<Date, Usuario> getListaEspera() {
        return listaEspera;
    }

}
