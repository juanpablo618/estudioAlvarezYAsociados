/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Consulta;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author developer
 */
@Stateless
public class ConsultaFacade extends AbstractFacade<Consulta> {

    @PersistenceContext(unitName = "EstudioAlvarezVersion2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultaFacade() {
        super(Consulta.class);
    }
    
    public List<Consulta> findAllExceptArchivedOrDismissed() {
        TypedQuery<Consulta> query = em.createNamedQuery("Consulta.findAllExceptArchivedOrDismissed", Consulta.class);
        return query.getResultList();
    }
    
    public boolean isCuitExisting(String cuit) {
        try {
            // Consulta JPQL para verificar si el CUIT ya existe en la base de datos
            Long count = em.createQuery("SELECT COUNT(c) FROM Consulta c WHERE c.cuit = :cuit", Long.class)
                            .setParameter("cuit", cuit)
                            .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
