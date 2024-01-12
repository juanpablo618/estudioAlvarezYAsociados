/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller;

import com.estudioAlvarezVersion2.jpa.Consulta;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
