/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Turno;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author developer
 */
@Stateless
public class TurnoFacade extends AbstractFacade<Turno> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TurnoFacade() {
        super(Turno.class);
    }
    
    public List<Turno> findByResponsable(String responsable) {
        
        return em.createNamedQuery("Turno.findByResponsable", Turno.class)
                 .setParameter("responsable", responsable)
                 .getResultList();
    }
    
    
    public List<Turno> findByResponsables(Set<String> responsables) {
        System.out.println("responsables: " + responsables.toString());
    return getEntityManager().createNamedQuery("Turno.findByResponsables", Turno.class)
                             .setParameter("responsables", responsables)
                             .getResultList();
}
    
}
