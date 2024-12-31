/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.party.dao.PersonRoleFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class UstadzFacade extends PersonRoleFacade<Ustadz> {

    @Inject
    private EntityManager em;

    public UstadzFacade() {
        super(Ustadz.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    

}
