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
    private String correo;

    /**
     * Constructor
     *
     * @param nombre
     * @param contraseña
     * @param correo
     */
    public UsuarioDTO(String nombre, String contraseña, String correo) {
        this.nombre = nombre;
        this.clave = contraseña;
        this.correo = correo;
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
