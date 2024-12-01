package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.FechasRestringidas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
@Stateless
public class FechasRestringidasFacade extends AbstractFacade<FechasRestringidas> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FechasRestringidasFacade() {
        super(FechasRestringidas.class);
    }
}
