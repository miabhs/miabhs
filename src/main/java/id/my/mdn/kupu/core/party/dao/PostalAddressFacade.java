/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.core.party.dao;

import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PostalAddress;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class PostalAddressFacade extends AbstractFacade<PostalAddress> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostalAddressFacade() {
        super(PostalAddress.class);
    }

//    @Override
//    public PostalAddress strToObj(String str) {
//        return find(Long.parseLong(str));
//    }

}
