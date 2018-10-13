package org.ujaen.practicaDAE.Servidor.DTOs;


/*
 * Por las características del diseño actual, en estos momentos esta clase no 
 * está siendo utilizada, sin embargo, hemos decidido mantenerla hasta estar
 * seguros de que no la necesitaremos en etapas más avanzadas del proyecto.
 */

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
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
