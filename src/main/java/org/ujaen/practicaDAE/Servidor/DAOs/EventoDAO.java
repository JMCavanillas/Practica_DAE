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
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.ujaen.practicaDAE.Servidor.Entidades.Evento;
import org.ujaen.practicaDAE.Servidor.Entidades.Usuario;

/**
 * Clase para la interacción entre la base de datos y la entidad Evento
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Repository
@Transactional
public class EventoDAO {

    @PersistenceContext
    EntityManager em;

    private static final String buscarEventoTipoQuery = "select e from Evento e where e.tipo= :tipo";
    private static final String buscarEventoPalabra = "select e from Evento e where e.descripcion ";

    public List<Evento> buscarEventoTipo(Evento.Tipo tipo) {
        List<Evento> eventos = em.createQuery(buscarEventoTipoQuery, Evento.class).setParameter("tipo", tipo).getResultList();

        return eventos;

    }

    public List<Evento> buscarEventoPalabraClave(List<String> palabra) {

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

    public void crearEvento(Date fecha, String lugar, Evento.Tipo tipo, String descripcion, int numeroMaxAsistentes, String nombre) {

        Usuario usuario = em.find(Usuario.class, nombre);                                         //Usuario gestionado por la BD
        Evento evento = usuario.creaEvento(fecha, lugar, tipo, descripcion, numeroMaxAsistentes); //Añadimos el evento a la lista de eventos creados del usuario

        em.persist(evento);                                                                     //Almacenamos el evento en la BD
    }

    public void borraEvento(Evento evento) {

        Evento e = em.find(Evento.class, evento.getId());

        em.remove(e);
    }

    public Evento buscarEventoID(int id) {
        Evento evento = em.find(Evento.class, id);
        return evento;
    }

    public void actualizarEvento(Evento evento) {
        em.merge(evento);
    }

    public boolean cancelarInscripcion(Evento evento, Usuario usuario, Usuario usuarioEnviarCorreo) {

        //System.out.println("Hola");
        
        em.merge(usuario);
        if (usuario.cancelarInscripcion(evento)) {

            em.merge(evento);

            if (!evento.getListaEspera().isEmpty()) {

               // em.lock(evento, LockModeType.OPTIMISTIC);
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

            return true;

        }

        return false;
    }

}
