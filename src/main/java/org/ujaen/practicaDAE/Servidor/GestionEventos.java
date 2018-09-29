/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;


/**
 *
 * @author macosx
 */
@Component
public class GestionEventos implements ServiciosEvento {

    List<Evento> eventos = new ArrayList<>();

    @Override
    public List<Evento> buscarEventoTipo(Tipo tipo) {
        List<Evento> tmp = new ArrayList<>();

        for (Evento evento : eventos) {
            if (evento.getTipo().equals(tipo)) {
                tmp.add(evento);
            }
        }

        return tmp;

    }

    @Override
    public List<Evento> buscarEventoPalabrasClave(List<String> palabras) {
        List<Evento> tmp = new ArrayList<>();

        //A lo mejor se podria cambiar para las primeras
        //posiciones de la lista la ocupen los eventos
        //que tengan mas coincidencias de palabras clave
        
        for (Evento evento : eventos) {
            for(String palabra: palabras){
                if(evento.getDescripcion().contains(palabra)){
                    tmp.add(evento);
                }
            }
            
        }

        return tmp;
    }

    @Override
    public Evento crearEvento(String lugar, Date fecha, String descripcion, int numeroMaxAsistentes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cancelarEvento(Evento evento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inscribirseEvento(Evento evento, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cancelarInscripcionEvento(Evento evento, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> verEventosInscritosCelebrados(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> verEventosInscritosFuturos(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> verEventosOrganizados(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Evento> verEventosOrganizadosFuturos(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
