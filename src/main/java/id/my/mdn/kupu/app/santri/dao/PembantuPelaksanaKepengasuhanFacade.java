/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.PembantuPelaksanaKepengasuhan;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PembantuPelaksanaKepengasuhanFacade extends PartyRelationshipFacade<PembantuPelaksanaKepengasuhan> {
    
    @Inject
    private EntityManager em;

    public PembantuPelaksanaKepengasuhanFacade() {
        super(PembantuPelaksanaKepengasuhan.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch(filterName) {
            case "fungsi":
                return cb.equal(from[0].get("fungsiPelaksanaKepengasuhan"), filterValue);
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

   
}
