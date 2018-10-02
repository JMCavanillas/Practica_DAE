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

/**
 *
 * @author macosx
 */
public class Evento {

    /*
     * Enum para restringir los tipos de eventos
     */
    public enum Tipo {
        CHARLA, CURSO, ACTIVIDAD_DEPORTIVA, VISITA_CULTURAL
    }

    private static int id = 0;
    private Date fecha;
    private String lugar;
    private Tipo tipo;
    private String descripcion;
    private int numeroMaxAsistentes;

    private List<Usuario> usuariosInscritos = new ArrayList<>();
    private List<Usuario> listaEspera = new ArrayList<>();

    public Evento(Date fecha, String lugar, Tipo tipo, String descripcion, int numeroMaxAsistentes) {
        id++;
        this.fecha = fecha;
        this.lugar = lugar;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.numeroMaxAsistentes = numeroMaxAsistentes;

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
     * @return the usuariosInscritos
     */
    public List<Usuario> getUsuariosInscritos() {
        return usuariosInscritos;
    }

    /**
     * @param usuariosInscritos the usuariosInscritos to set
     */
    public void setUsuariosInscritos(List<Usuario> usuariosInscritos) {
        this.usuariosInscritos = usuariosInscritos;
    }

    /**
     * @return the listaEspera
     */
    public List<Usuario> getListaEspera() {
        return listaEspera;
    }

    /**
     * @param listaEspera the listaEspera to set
     */
    public void setListaEspera(List<Usuario> listaEspera) {
        this.listaEspera = listaEspera;
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
    public static int getId() {
        return id;
    }

    public EventoDTO toDTO() {
        return new EventoDTO(fecha, lugar, tipo,
                descripcion, numeroMaxAsistentes);
    }

}
