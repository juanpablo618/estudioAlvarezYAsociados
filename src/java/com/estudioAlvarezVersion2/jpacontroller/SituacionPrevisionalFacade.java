package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.SituacionPrevisional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juanpablo618@hotmail.com
 */
@Stateless
public class SituacionPrevisionalFacade extends AbstractFacade<SituacionPrevisional> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SituacionPrevisionalFacade() {
        super(SituacionPrevisional.class);
    }
    
}
