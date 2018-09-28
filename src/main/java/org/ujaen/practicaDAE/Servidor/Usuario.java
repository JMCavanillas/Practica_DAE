/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macosx
 */
public class Usuario {

    private String nombre;
    private String contraseña;

    private List<Evento> eventosInscritos = new ArrayList<>();
    private List<Evento> eventosCreados = new ArrayList<>();

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
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
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * @return the eventosInscritos
     */
    public List<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    /**
     * @param eventosInscritos the eventosInscritos to set
     */
    public void setEventosInscritos(List<Evento> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }

    /**
     * @return the eventosCreados
     */
    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }

    /**
     * @param eventosCreados the eventosCreados to set
     */
    public void setEventosCreados(List<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

}
