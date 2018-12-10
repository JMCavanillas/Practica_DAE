package org.ujaen.practicaDAE.Servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
//import org.ujaen.practicaDAE.Cliente.Cliente;

/**
 * Clase principal de incio de la aplicación Spring
 *
 * @author Javier Martínez Cavanillas
 * @author Juan Antonio Béjar Martos
 */
@SpringBootApplication
@EntityScan(basePackages = "org.ujaen.practicaDAE.Servidor.Entidades")
@EnableCaching

public class ServidorPracDAE {

    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(ServidorPracDAE.class);
        ApplicationContext context = servidor.run(args);
//        
//        Cliente cliente=new Cliente(context);
//        cliente.run();
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
