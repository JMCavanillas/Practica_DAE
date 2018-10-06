/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.ujaen.practicaDAE.Cliente.Cliente;

/**
 *
 * @author javier
 */
@SpringBootApplication
public class ServidorPracDAE 
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication servidor=new SpringApplication(ServidorPracDAE.class);
        ApplicationContext context=servidor.run(args);
        
        Cliente cliente=new Cliente(context);
        cliente.run();
    }
}
