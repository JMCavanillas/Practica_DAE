/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.Seguridad;

import javax.sql.DataSource;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author jabm9
 */
@Configuration
public class SeguridadDAE extends WebSecurityConfigurerAdapter {

    
    //cambiar produces por consumes
    
    @Autowired
    ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(servicioDatosUsuario);

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests().antMatchers("/gestionUsuarios/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET, "/evento/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST, "/evento/**").hasRole("USUARIO");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.DELETE, "/evento/**").hasRole("USUARIO");
        httpSecurity.authorizeRequests().antMatchers("/usuario/{nombre}/evento/*").access("hasRole('USUARIO') and #nombre == principal.username");

    }

    

}
