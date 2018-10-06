package org.ujaen.practicaDAE.Cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
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

        servicioUsuario.registro("juan", "abc");

        servicioUsuario.mostrarUsuarios();

    }

}
