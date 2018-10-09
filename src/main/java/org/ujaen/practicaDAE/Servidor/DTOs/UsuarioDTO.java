/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.DTOs;

/**
 *
 * @author javier
 */
public class UsuarioDTO {

    private String nombre;
    private String clave;

    /**
     * Constructor
     *
     * @param nombre
     * @param contraseña
     */
    public UsuarioDTO(String nombre, String contraseña) {
        this.nombre = nombre;
        this.clave = contraseña;
    }

    /**
     * Constructor solo nombre
     *
     * @param nombre
     */
    public UsuarioDTO(String nombre) {
        this.nombre = nombre;
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
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

}
