package org.ujaen.practicaDAE.Servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.ujaen.practicaDAE.Cliente.Cliente;

/**
 * Clase principal de incio de la aplicación Spring
 * 
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@SpringBootApplication
@EntityScan(basePackages="org.ujaen.practicaDAE.Servidor.Entidades")

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
