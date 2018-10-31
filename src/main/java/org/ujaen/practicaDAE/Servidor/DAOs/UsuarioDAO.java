/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.DAOs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;

/**
 * Clase para la interacción entre la base de datos y la entidad Usuario
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Repository
@Transactional
public class UsuarioDAO {

    @PersistenceContext
    EntityManager em;

    public Usuario buscarUsuario(String nombre) {
        return (em.find(Usuario.class, nombre));
    }

    public void registrarUsuario(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void actualizarUsuario(Usuario usuario){
        em.merge(usuario);
    }
}
