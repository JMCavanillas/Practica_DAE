/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ujaen.practicaDAE.Servidor.InterfacesREST;

import java.util.List;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author javier
 * @param <T>
 */
public class Pagina<T> {
    
    private final List<T> contenidos;
    private final int offset;
    private final int num_pag;
    private final int tam_pag;
    private int pag_totales;
    
    private final String primera_pag;
    private final String ultima_pag;
    
    private final boolean tiene_sig;
    private final String link_sig;
    
    private final boolean tiene_ant;
    private final String link_ant;
    
    
    public Pagina(List<T> elementos, int num_pag, int tam)
    {
        this.num_pag = num_pag;
        this.tam_pag = tam;
        this.offset = this.num_pag * this.tam_pag;
        this.pag_totales = elementos.size() / this.tam_pag;
        
        if(elementos.size() % this.tam_pag != 0)
            ++this.pag_totales;
        
        int inicio = offset;
        int fin = (inicio + tam_pag) > elementos.size() ? 
                elementos.size() : (inicio + tam_pag);
        contenidos = elementos.subList(inicio, fin);
        
        this.tiene_ant = (this.num_pag > 0);
        this.tiene_sig = (this.num_pag < pag_totales - 1);
        
        if (this.tiene_ant)
            this.link_ant = buildPageLink("page", this.num_pag - 1 , "size", this.tam_pag);
        else
            this.link_ant = "";
        
        if (this.tiene_sig)
            this.link_sig = buildPageLink("page", this.num_pag + 1 , "size", this.tam_pag);
        else
            this.link_sig = "";
        
        this.primera_pag = buildPageLink("page", 0 , "size", this.tam_pag);
        this.ultima_pag = buildPageLink("page", this.pag_totales - 1 , "size", this.tam_pag);
        
    }
    
    
    private String buildPageLink(String pageParam, int page, String sizeParam, int size)
    {
        String path = createBuilder()
                .queryParam(pageParam, page)
                .queryParam(sizeParam, size)
                .build()
                .toUriString();
        
        return path;
    }

    private ServletUriComponentsBuilder createBuilder()
    {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }
    
    
    /**
     * @return the contenidos
     */
    public List<T> getContenidos()
    {
        return contenidos;
    }

    /**
     * @return the offset
     */
    public int getOffset()
    {
        return offset;
    }

    /**
     * @return the num_pag
     */
    public int getNum_pag()
    {
        return num_pag;
    }

    /**
     * @return the tam_pag
     */
    public int getTam_pag()
    {
        return tam_pag;
    }

    /**
     * @return the pag_totales
     */
    public int getPag_totales()
    {
        return pag_totales;
    }

    /**
     * @return the primera_pag
     */
    public String getPrimera_pag()
    {
        return primera_pag;
    }

    /**
     * @return the ultima_pag
     */
    public String getUltima_pag()
    {
        return ultima_pag;
    }

    /**
     * @return the tiene_sig
     */
    public boolean isTiene_sig()
    {
        return tiene_sig;
    }

    /**
     * @return the link_sig
     */
    public String getLink_sig()
    {
        return link_sig;
    }

    /**
     * @return the tiene_ant
     */
    public boolean isTiene_ant()
    {
        return tiene_ant;
    }

    /**
     * @return the link_ant
     */
    public String getLink_ant()
    {
        return link_ant;
    }
}
