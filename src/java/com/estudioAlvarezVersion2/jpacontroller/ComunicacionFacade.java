package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Comunicacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Stateless
public class ComunicacionFacade extends AbstractFacade<Comunicacion> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComunicacionFacade() {
        super(Comunicacion.class);
    }
    
}
