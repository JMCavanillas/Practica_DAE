package org.ujaen.practicaDAE.Cliente;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.ujaen.practicaDAE.Servidor.DTOs.EventoDTO;
import org.ujaen.practicaDAE.Servidor.Evento;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosEvento;
import org.ujaen.practicaDAE.Servidor.Interfaces.ServiciosUsuario;

/**
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
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

        while (select != 0) {
            try {
                System.out.print("Elige opción:\n"
                        + "0.- Salir \n"
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
                        + "13.- Ver eventos en lista de espera\n"
                        + "\n: ");

                select = Integer.parseInt(scanner.nextLine());
                Scanner sc = new Scanner(System.in);

                switch (select) {
                    case 1:

                        System.out.println("Introduzca un nombre de usuario:");
                        String nombre = sc.nextLine();
                        System.out.println("Introduzca una contraseña");
                        String pwd = sc.nextLine();

                        if (servicioUsuario.registro(nombre, pwd)) {
                            System.out.println("Usuario creado correctamente");
                        }
                        break;
                    case 2:

                        System.out.println("Introduzca un nombre de usuario:");
                        String nombrelogin = sc.nextLine();
                        System.out.println("Introduzca una contraseña");
                        String pwdlogin = sc.nextLine();
                        token = servicioUsuario.login(nombrelogin, pwdlogin);

                        if (token == -1 || token == -2) {
                            System.out.println("Login fallido");
                        } else {
                            System.out.println("Login correcto");

                        }

                        break;
                    case 3:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            System.out.println("Introduzca el año:");
                            int y = Integer.parseInt(sc.nextLine());
                            System.out.println("Introduzca el mes:");
                            int m = Integer.parseInt(sc.nextLine());
                            System.out.println("Introduzca el día:");
                            int d = Integer.parseInt(sc.nextLine());
                            System.out.println("Introduzca la hora:");
                            int h = Integer.parseInt(sc.nextLine());
                            System.out.println("Introduzca los minutos:");
                            int min = Integer.parseInt(sc.nextLine());
                            Calendar calendario = new GregorianCalendar();
                            calendario.setLenient(false);
                            calendario = new GregorianCalendar(y, m, d, h, min);
                           

                            Date fecha = calendario.getTime();
                            

                            System.out.println("Introduzca el lugar");
                            String lugar = sc.nextLine();
                            System.out.println("Introduzca una descripción");
                            String descripcion = sc.nextLine();
                            System.out.println("Introduzca el número máximo de asistentes");
                            int numMaxAsistentes = sc.nextInt();

                            Evento.Tipo tipo = seleccionarTipo();

                            EventoDTO evento = new EventoDTO(fecha, lugar, tipo, descripcion, numMaxAsistentes);
                            servicioEvento.crearEvento(evento, token);
                            System.out.println("Evento creado");
                        }
                        break;
                    case 4:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            List<EventoDTO> eventos = verEventosOrganizadosFuturos(servicioEvento, token);
                            if (eventos.isEmpty()) {
                                System.out.println("No hay eventos");
                            } else {
                                System.out.println("Seleccione la posición del evento que quiere borrar");
                                int pos = sc.nextInt();

                                servicioEvento.cancelarEvento(eventos.get(pos), token);
                            }
                        }
                        break;
                    case 5:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {

                            System.out.print("Elija un tipo de búsqueda: \n"
                                    + "1- Tipo \n"
                                    + "2- Palabras \n");
                            int seleccionarBusqueda = Integer.parseInt(scanner.nextLine());
                            switch (seleccionarBusqueda) {
                                case 1:
                                    Evento.Tipo tmp = seleccionarTipo();
                                    List<EventoDTO> listaEventosTipo = servicioEvento.buscarEventoTipo(tmp);
                                    for (int i = 0; i < listaEventosTipo.size(); i++) {
                                        eventoDTOString(listaEventosTipo.get(i));
                                    }
                                    if (listaEventosTipo.isEmpty()) {
                                        System.out.println("No hay eventos");
                                    } else {
                                        System.out.println("Seleccione la posición del evento en el que quiere inscribirse");
                                        int pos = sc.nextInt();

                                        servicioEvento.inscribirseEvento(listaEventosTipo.get(pos), token);
                                        System.out.println("Inscripción realizada con éxito");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Introduzca las palabras utilizando - para separarlas");
                                    String palabrasBuscadas = sc.nextLine();
                                    String[] partes = palabrasBuscadas.split("\\s*-\\s*");
                                    List<EventoDTO> listaEventosPalabrasClave = servicioEvento.buscarEventoPalabrasClave(partes);
                                    for (int i = 0; i < listaEventosPalabrasClave.size(); i++) {
                                        eventoDTOString(listaEventosPalabrasClave.get(i));
                                    }
                                    if (listaEventosPalabrasClave.isEmpty()) {
                                        System.out.println("No hay eventos");
                                    } else {
                                        System.out.println("Seleccione la posición del evento en el que quiere inscribirse");
                                        int pos2 = sc.nextInt();

                                        servicioEvento.inscribirseEvento(listaEventosPalabrasClave.get(pos2), token);
                                        System.out.println("Inscripción realizada con éxito");
                                    }
                                    break;
                            }
                        }
                        break;

                    case 6:

                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            List<EventoDTO> eventos = verEventosInscritosFuturos(servicioEvento, token);
                            if (eventos.isEmpty()) {
                                System.out.println("No hay eventos");
                            } else {
                                System.out.println("Seleccione la posición del evento que quiere cancelar la inscripción");
                                int pos = sc.nextInt();

                                servicioEvento.cancelarEvento(eventos.get(pos), token);
                            }
                        }
                        break;
                    case 7:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            List<EventoDTO> evtosOrgCeleb = servicioEvento.verEventosOrganizados(token);
                            if (evtosOrgCeleb.isEmpty()) {
                                System.out.println("No hay eventos");
                            } else {
                                for (int i = 0; i < evtosOrgCeleb.size(); i++) {
                                    eventoDTOString(evtosOrgCeleb.get(i));
                                }
                            }
                        }
                        break;
                    case 8:

                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            verEventosOrganizadosFuturos(servicioEvento, token);

                        }
                        break;
                    case 9:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            List<EventoDTO> evtosInscritosCelebrados = servicioEvento.verEventosInscritosCelebrados(token);
                            for (int i = 0; i < evtosInscritosCelebrados.size(); i++) {
                                eventoDTOString(evtosInscritosCelebrados.get(i));
                            }
                        }
                        break;
                    case 10:
                        if (token == -1 || token == -2) {
                            System.out.println("Debe estar logueado en el sistema para acceder a esta funcionalidad");
                        } else {
                            List<EventoDTO> evtosInscritosFuturos = servicioEvento.verEventosInscritosFuturos(token);
                            for (int i = 0; i < evtosInscritosFuturos.size(); i++) {
                                eventoDTOString(evtosInscritosFuturos.get(i));
                            }
                        }
                        break;
                    case 11:
                        Evento.Tipo tmp = seleccionarTipo();
                        List<EventoDTO> listaEventosTipo = servicioEvento.buscarEventoTipo(tmp);
                        for (int i = 0; i < listaEventosTipo.size(); i++) {
                            eventoDTOString(listaEventosTipo.get(i));
                        }

                        break;
                    case 12:

                        List<EventoDTO> listaEventosPalabrasClave = busquedaPalabras(servicioEvento, sc);
                        for (int i = 0; i < listaEventosPalabrasClave.size(); i++) {
                            eventoDTOString(listaEventosPalabrasClave.get(i));
                        }
                        break;

                }
            } catch (RuntimeException e) {
                System.out.println("Uoop! Error! " + e.toString());
            }

        }

    }

    void eventoDTOString(EventoDTO e
    ) {
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

    List<EventoDTO> verEventosOrganizadosFuturos(ServiciosEvento servicioEvento, int token
    ) {

        try {
            List<EventoDTO> evtosOrgFuturos = servicioEvento.verEventosOrganizadosFuturos(token);
            if (evtosOrgFuturos.isEmpty()) {
                System.out.println("No hay eventos");

            } else {
                for (int i = 0; i < evtosOrgFuturos.size(); i++) {
                    eventoDTOString(evtosOrgFuturos.get(i));
                }
            }
            return evtosOrgFuturos;
        } catch (Exception e) {
            System.out.println("Uoop! Error! " + e.toString());
        }
        return null;
    }

    List<EventoDTO> verEventosInscritosFuturos(ServiciosEvento servicioEvento, int token
    ) {
        try {
            List<EventoDTO> evtosInscritosFuturos = servicioEvento.verEventosInscritosFuturos(token);
            for (int i = 0; i < evtosInscritosFuturos.size(); i++) {
                eventoDTOString(evtosInscritosFuturos.get(i));
            }
        } catch (Exception e) {
            System.out.println("Uoop! Error! " + e.toString());
        }
        return null;
    }

    List<EventoDTO> busquedaPalabras(ServiciosEvento servicioEvento, Scanner sc) {
        System.out.println("Introduzca las palabras utilizando - para separarlas");
        String palabrasBuscadas = sc.nextLine();
        String[] partes = palabrasBuscadas.split("\\s*-\\s*");
        List<EventoDTO> listaEventosPalabrasClave = servicioEvento.buscarEventoPalabrasClave(partes);
        return listaEventosPalabrasClave;
    }


}
