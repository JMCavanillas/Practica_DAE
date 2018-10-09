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
import java.util.Scanner;
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
    static Scanner scanner = new Scanner(System.in);
    static int select = -1;

    int token = -1;

    public Cliente(ApplicationContext context) {
        this.context = context;
    }

    public void run() {
        ServiciosUsuario servicioUsuario = (ServiciosUsuario) context.getBean("gestionUsuarios");
        ServiciosEvento servicioEvento = (ServiciosEvento) context.getBean("gestionEventos");
        UsuarioDTO usuario = null;

        while (select != 0) {
            try {
                System.out.print("Elige opción:\n"
                        + "1.- Registro \n"
                        + "2.- Login \n"
                        + "3.- Crear evento \n"
                        + "4.- Cancelar evento \n"
                        + "5.- Inscribirse Evento \n"
                        + "6.- Cancelar inscripción evento \n "
                        + "7.- Ver eventos organizados celebrados \n"
                        + "8.- Ver eventos organizados futuros \n"
                        + "9.- Ver eventos inscritos celebrados \n"
                        + "10.- Ver eventos inscritos futuros \n"
                        + "11.- Buscar eventos por tipo \n"
                        + "12.- Buscar eventos por palabras clave \n"
                        + "\n: ");

                select = Integer.parseInt(scanner.nextLine());
                Scanner sc = new Scanner(System.in);

                switch (select) {
                    case 1:

                        System.out.println("Introduzca un nombre de usuario:");
                        String nombre = sc.nextLine();
                        System.out.println("Introduzca una contraseña");
                        String pwd = sc.nextLine();
                        UsuarioDTO usuarioReg = new UsuarioDTO(nombre, pwd);
                        if (servicioUsuario.registro(usuarioReg)) {
                            System.out.println("Usuario creado correctamente");
                        } else {
                            System.out.println("No se pudo registrar el usuario1");
                        }

                        break;
                    case 2:

                        System.out.println("Introduzca un nombre de usuario:");
                        String nombrelogin = sc.nextLine();
                        System.out.println("Introduzca una contraseña");
                        String pwdlogin = sc.nextLine();
                        token = servicioUsuario.login(nombrelogin, pwdlogin);

                        if (token == -1) {
                            System.out.println("Login fallido");
                        } else {
                            System.out.println("Login correcto");
                            usuario = servicioUsuario.buscarUsuario(nombrelogin).toDTO();
                        }

                        break;
                    case 3:
                        restringirAcceso();

                        System.out.println("Introduzca el año:");
                        int y = sc.nextInt();
                        System.out.println("Introduzca el mes:");
                        int m = sc.nextInt();
                        System.out.println("Introduzca el día:");
                        int d = sc.nextInt();
                        System.out.println("Introduzca la hora:");
                        int h = sc.nextInt();
                        System.out.println("Introduzca los minutos:");
                        int min = sc.nextInt();

                        Calendar calendario = new GregorianCalendar(y, m - 1, d, h, min);
                        Date fecha = calendario.getTime();
                        System.out.println("Introduzca el lugar");
                        String lugar = sc.nextLine();
                        System.out.println("Introduzca una descripción");
                        String descripcion = sc.nextLine();
                        System.out.println("Introduzca el número máximo de asistentes");
                        int numMaxAsistentes = sc.nextInt();

                        Evento.Tipo tipo = seleccionarTipo();

                        EventoDTO evento = new EventoDTO(fecha, lugar, tipo, descripcion, numMaxAsistentes);
                        servicioEvento.crearEvento(evento, usuario);
                        System.out.println("Evento creado");
                        break;
                    case 4:
                        restringirAcceso();
                        List<EventoDTO> eventos=verEventosOrganizadosFuturos(servicioEvento, usuario);
                        System.out.println("Seleccione la posición del evento que quiere borrar");
                        int pos = sc.nextInt();
                        servicioEvento.cancelarEvento(eventos.get(pos-1), usuario);
                        
                        
                        
                        break;
                    case 5:
                        restringirAcceso();
                        System.out.println(5);
                        break;
                    case 6:
                        restringirAcceso();
                        System.out.println(6);
                        break;
                    case 7:
                        restringirAcceso();
                        List<EventoDTO> evtosOrgCeleb = servicioEvento.verEventosOrganizados(usuario);
                        for (int i = 0; i < evtosOrgCeleb.size(); i++) {
                            eventoDTOString(evtosOrgCeleb.get(i));
                        }
                        break;
                    case 8:
                        restringirAcceso();
                        verEventosOrganizadosFuturos(servicioEvento, usuario);
                        System.out.println("Introduzca la posición del evento que quiere borrar");
                        
                        break;
                    case 9:
                        restringirAcceso();
                        List<EventoDTO> evtosInscritosCelebrados = servicioEvento.verEventosInscritosCelebrados(usuario);
                        for (int i = 0; i < evtosInscritosCelebrados.size(); i++) {
                            eventoDTOString(evtosInscritosCelebrados.get(i));
                        }
                        break;
                    case 10:
                        restringirAcceso();
                        List<EventoDTO> evtosInscritosFuturos = servicioEvento.verEventosInscritosFuturos(usuario);
                        for (int i = 0; i < evtosInscritosFuturos.size(); i++) {
                            eventoDTOString(evtosInscritosFuturos.get(i));
                        }
                        System.out.println(1);
                        break;
                    case 11:
                        Evento.Tipo tmp = seleccionarTipo();
                        List<EventoDTO> listaEventosTipo = servicioEvento.buscarEventoTipo(tmp);
                        for (int i = 0; i < listaEventosTipo.size(); i++) {
                            eventoDTOString(listaEventosTipo.get(i));
                        }

                        break;
                    case 12:

                        System.out.println("Introduzca las palabras utilizando - para separarlas");
                        String palabrasBuscadas = sc.nextLine();
                        String[] partes = palabrasBuscadas.split("\\s*-\\s*");
                        List<EventoDTO> listaEventosPalabrasClave = servicioEvento.buscarEventoPalabrasClave(partes);
                        for (int i = 0; i < listaEventosPalabrasClave.size(); i++) {
                            eventoDTOString(listaEventosPalabrasClave.get(i));
                        }
                        break;

                }
            } catch (Exception e) {
                System.out.println("Uoop! Error! " + e.toString());
            }

        }

    }

    void restringirAcceso() {
        if (token == -1) {
            System.out.println("Debe estar registrado para acceder a esta funcionalidad");
        }
    }

    void eventoDTOString(EventoDTO e) {
        String tmp = "Evento:" + e.getId() + e.getFecha() + e.getLugar() + e.getTipo() + e.getDescripcion() + e.getNumeroMaxAsistentes();
        System.out.println(tmp);
    }

    Evento.Tipo seleccionarTipo() {

        System.out.print("Seleccione un tipo de actividad: \n"
                + "1- Charla \n"
                + "2- Curso \n"
                + "3- Actividad deportiva \n"
                + "4- Visita cultural \n");
        int seleccionarTipo = Integer.parseInt(scanner.nextLine());

        Evento.Tipo tipo = null;
        switch (seleccionarTipo) {
            case 1:
                tipo = Evento.Tipo.CHARLA;
                break;
            case 2:
                tipo = Evento.Tipo.CURSO;
                break;
            case 3:
                tipo = Evento.Tipo.ACTIVIDAD_DEPORTIVA;
                break;
            case 4:
                tipo = Evento.Tipo.VISITA_CULTURAL;
                break;
        }
        return tipo;
    }

    List<EventoDTO> verEventosOrganizadosFuturos(ServiciosEvento servicioEvento, UsuarioDTO usuario) {
        List<EventoDTO> evtosOrgFuturos = servicioEvento.verEventosOrganizadosFuturos(usuario);
        for (int i = 0; i < evtosOrgFuturos.size(); i++) {
            eventoDTOString(evtosOrgFuturos.get(i));
        }
        
        return evtosOrgFuturos;
    }

}
