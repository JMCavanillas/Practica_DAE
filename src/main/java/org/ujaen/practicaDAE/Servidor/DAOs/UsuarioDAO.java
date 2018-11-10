/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.DAOs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ujaen.practicaDAE.Excepciones.ExcepcionActualizarUsuario;
import org.ujaen.practicaDAE.Excepciones.ExcepcionUsuarioYaRegistrado;

import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;

/**
 * Clase para la interacción entre la base de datos y la entidad Usuario
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UsuarioDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Usuario buscarUsuario(String nombre) {
        return (em.find(Usuario.class, nombre));
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void registrarUsuario(Usuario usuario) {
        try {
            em.persist(usuario);
        } catch (RuntimeException ex) {
            throw new ExcepcionUsuarioYaRegistrado();
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void actualizarUsuario(Usuario usuario){
        try {
            em.merge(usuario);
        } catch (RuntimeException ex) {
            throw new ExcepcionActualizarUsuario();
        }
    }
}
