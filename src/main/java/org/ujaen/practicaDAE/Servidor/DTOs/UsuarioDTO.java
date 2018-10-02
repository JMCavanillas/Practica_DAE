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

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }
    
    private String nombre;
    private String contraseña;
    
    /**
     * Constructor
     * @param nombre
     * @param contraseña 
     */
    public UsuarioDTO(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }
    
    /**
     * Constructor solo nombre
     * @param nombre 
     */
    public UsuarioDTO(String nombre) {
        this.nombre = nombre;
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
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña)
    {
        this.contraseña = contraseña;
    }

}
