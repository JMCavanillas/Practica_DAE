package org.ujaen.practicaDAE.Cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.DTOs.UsuarioDTO;
import org.ujaen.practicaDAE.Servidor.Evento;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosEvento;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosUsuario;

/**
 *
 * @author javier
 */
public class Cliente {

    ApplicationContext context;

    public Cliente(ApplicationContext context) {
        this.context = context;
    }

    public void run() {
        ServiciosUsuario servicioUsuario = (ServiciosUsuario) context.getBean("gestionUsuarios");
        ServiciosEvento servicioEvento = (ServiciosEvento) context.getBean("gestionEventos");

        System.out.println("Registro de usuarios y creación de eventos");
        UsuarioDTO usuario = new UsuarioDTO("juan", "abc");
        UsuarioDTO usuario2 = new UsuarioDTO("javi, qwer");
        servicioUsuario.registro(usuario);
        servicioUsuario.registro(usuario2);

        servicioUsuario.mostrarUsuarios();

        Calendar calendario1 = new GregorianCalendar(2014, 2, 2, 18, 0);
        Date fecha1 = calendario1.getTime();

        Calendar calendario2 = new GregorianCalendar(2020, 2, 2, 18, 0);
        Date fecha2 = calendario2.getTime();

        EventoDTO evento = new EventoDTO(fecha1, "Jaén", Evento.Tipo.CURSO, "Curso de cocina", 20);
        EventoDTO evento2 = new EventoDTO(fecha2, "Jaén", Evento.Tipo.CURSO, "Curso de cocina2", 30);

        EventoDTO evento3 = new EventoDTO(fecha1, "Jaén", Evento.Tipo.ACTIVIDAD_DEPORTIVA, "Torneo de baloncesto", 30);
        EventoDTO evento4 = new EventoDTO(fecha2, "Jaén", Evento.Tipo.ACTIVIDAD_DEPORTIVA, "Torneo de fútbol", 35);

        //Creación Eventos
        servicioEvento.crearEvento(evento, usuario);
        servicioEvento.crearEvento(evento2, usuario);
        servicioEvento.crearEvento(evento3, usuario2);
        servicioEvento.crearEvento(evento4, usuario2);
        System.out.println("Ver eventos creados");

        servicioEvento.mostrarEventos();
        //Listado de eventos creados por el usuario ya organizados
        List<EventoDTO> l = servicioEvento.verEventosOrganizados(usuario);
        for (int i = 0; i < l.size(); i++) {
            eventoDTOString(l.get(i));
        }
        ////Listado de eventos creados por el usuario por organizar en el futuro
        List<EventoDTO> l1 = servicioEvento.verEventosOrganizadosFuturos(usuario);
        for (int i = 0; i < l1.size(); i++) {
            eventoDTOString(l1.get(i));
        }

        System.out.println("Prueba de inscripción");

        servicioEvento.inscribirseEvento(evento, usuario);
        servicioEvento.inscribirseEvento(evento2, usuario);
        servicioEvento.inscribirseEvento(evento3, usuario);
        servicioEvento.inscribirseEvento(evento4, usuario);

        List<EventoDTO> l2 = servicioEvento.verEventosInscritosCelebrados(usuario);
        List<EventoDTO> l3 = servicioEvento.verEventosInscritosFuturos(usuario);
        for (int i = 0; i < l2.size(); i++) {
            System.out.println("Eventos inscritos celebrados:");
            eventoDTOString(l2.get(i));
        }

        for (int i = 0; i < l3.size(); i++) {
            System.out.println("Eventos inscritos por celebrar:");
            eventoDTOString(l3.get(i));
        }

        System.out.println("Prueba de cancelación");

        servicioEvento.cancelarEvento(evento, usuario);
        List<EventoDTO> l4 = servicioEvento.verEventosOrganizados(usuario);
        for (int i = 0; i < l4.size(); i++) {
            eventoDTOString(l4.get(i));
        }
        System.out.println("------------------");
        List<EventoDTO> l5 = servicioEvento.verEventosInscritosCelebrados(usuario);
        for (int i = 0; i < l5.size(); i++) {
            eventoDTOString(l5.get(i));
        }

        System.out.println("Prueba de búsqueda 1");
        List<String> palabras = new ArrayList<>();
        //Estandárizar para que todas las palabras que entren sean lower case
        //palabras.add("cocina");
        //palabras.add("fútbol");
        palabras.add("torneo");

        List<EventoDTO> l6 = servicioEvento.buscarEventoPalabrasClave(palabras);
        for (int i = 0; i < l6.size(); i++) {
            eventoDTOString(l6.get(i));
        }

        System.out.println("Prueba de búsqueda 2");
        List<EventoDTO> l7 = servicioEvento.buscarEventoTipo(Evento.Tipo.CURSO);
        for (int i = 0; i < l7.size(); i++) {
            eventoDTOString(l7.get(i));
        }
    }

    void eventoDTOString(EventoDTO e) {
        String tmp = "Evento:" + e.getId() + e.getFecha() + e.getLugar() + e.getTipo() + e.getDescripcion() + e.getNumeroMaxAsistentes();
        System.out.println(tmp);
    }
}
