package org.ujaen.practicaDAE.Servidor.Correo;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Clase que gestiona el envio de correos electrónicos
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@Component
public class ServicioCorreo {

    @Autowired
    public JavaMailSender emailSender;



    public void sendSimpleMessage(String receptor, String tema, String nombreUsuario, String descripcionActividad, Date fecha, String lugar) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receptor);
        message.setSubject(tema);
        String texto="Enhorabuena!Ha sido aceptado para la actividad "+descripcionActividad+" a celebrar "+ "el "
                + "dia "+ fecha+" en "+ lugar + " .Entra en tu cuenta de "+ nombreUsuario + " para obtener más información";

        message.setText(texto);
        emailSender.send(message);

    }
}
