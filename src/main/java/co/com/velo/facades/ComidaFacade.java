/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.velo.facades;

import co.com.velo.entidades.Comida;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
@Stateless
public class ComidaFacade extends AbstractFacade<Comida> {

    @PersistenceContext(unitName = "co.com.velo_velo_war_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComidaFacade() {
        super(Comida.class);
    }
    
}
