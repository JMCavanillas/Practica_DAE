/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.DAOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ujaen.practicaDAE.Excepciones.ExcepcionActualizarEvento;
import org.ujaen.practicaDAE.Excepciones.ExcepcionCancelarEventoNoOrganizado;
import org.ujaen.practicaDAE.Excepciones.ExcepcionCancelarInscripcion;
import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;

/**
 * Clase para la interacción entre la base de datos y la entidad Evento
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class EventoDAO {

    @PersistenceContext
    EntityManager em;

    private static final String buscarEventoTipoQuery = "select e from Evento e where e.tipo= :tipo";
    private static final String buscarEventoPalabra = "select e from Evento e where e.descripcion ";

    @Cacheable(value = "eventos")
    public List<Evento> buscarEventoTipo(Evento.Tipo tipo) {
//        slowQuery(5000);
//        System.out.println("HOLA; NO SOY DE CACHE");
        List<Evento> eventos = em.createQuery(buscarEventoTipoQuery, Evento.class).setParameter("tipo", tipo).getResultList();

        return eventos;
    }
    
//    private void slowQuery(long seconds){
//        try {
//            Thread.sleep(seconds);
//        } catch (InterruptedException e) {
//            throw new IllegalStateException(e);
//        }
//    }

    @Cacheable(value = "eventos")
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<Evento> buscarEventoPalabraClave(List<String> palabra) {
//        slowQuery(5000);

        String query = buscarEventoPalabra;
        List<Evento> eventos = new ArrayList<>();

        for (int i = 0; i < palabra.size(); i++) {
            if (i == 0) {
                query += "LIKE '%" + palabra.get(i) + "%'";
            } else {
                query += " OR e.descripcion LIKE '%" + palabra.get(i) + "%'";
            }

        }

        eventos = em.createQuery(query, Evento.class).getResultList();

        return eventos;

    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void crearEvento(Date fecha, String lugar, Evento.Tipo tipo, String descripcion, int numeroMaxAsistentes, String nombre) {

        Usuario usuario = em.find(Usuario.class, nombre);                                         //Usuario gestionado por la BD
        Evento evento = usuario.creaEvento(fecha, lugar, tipo, descripcion, numeroMaxAsistentes); //Añadimos el evento a la lista de eventos creados del usuario

        em.persist(evento);                                                                     //Almacenamos el evento en la BD
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void borraEvento(Evento evento) {
        
        try {
            Evento e = em.find(Evento.class, evento.getId());
            em.remove(e);
        } catch(Exception ex) {
            throw new ExcepcionCancelarEventoNoOrganizado();
        }
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Evento buscarEventoID(int id) {
        Evento evento = em.find(Evento.class, id);
        return evento;
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void actualizarEvento(Evento evento) {
        try{
            em.merge(evento);
            em.flush();
        } catch(Exception ex) {
            throw new ExcepcionActualizarEvento();
        }
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void cancelarInscripcion(Evento evento, Usuario usuario, Usuario usuarioEnviarCorreo) {
        try {
        //System.out.println("Hola");
            em.merge(usuario);
            evento = em.find(Evento.class, evento.getId());
            if (usuario.cancelarInscripcion(evento)) {

                if (!evento.getListaEspera().isEmpty()) {

                    em.lock(evento, LockModeType.OPTIMISTIC);
                    Usuario tmp = null;
                    for (Map.Entry<Date, Usuario> entry : evento.getListaEspera().entrySet()) {
                        tmp = entry.getValue();
                        evento.getListaEspera().remove(entry.getKey());
                        break;
                    }

                    tmp.cambiarLista(evento);
                    em.merge(evento);

                    usuarioEnviarCorreo.asignarDatos(tmp);
                }
            }
            em.flush();
        } catch(Exception ex) {
            throw new ExcepcionCancelarInscripcion();
        }
    }

}
