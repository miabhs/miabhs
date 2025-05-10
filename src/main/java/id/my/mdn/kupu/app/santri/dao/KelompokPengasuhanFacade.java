/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.core.party.dao.AbstractPartyRoleFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class KelompokPengasuhanFacade extends AbstractPartyRoleFacade<KelompokPengasuhan> {

    @Inject
    private EntityManager em;

    @Inject
    private PartyRoleTypeFacade roleTypeFacade;

    public KelompokPengasuhanFacade() {
        super(KelompokPengasuhan.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected PartyRoleTypeFacade getRoleTypeFacade() {
        return roleTypeFacade;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, 
            CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "name":
                return cb.like(
                        cb.upper(from[0].get("party").get("firstName")), 
                        ("%" + filterValue + "%").toUpperCase());
            case "kelompokPengasuhan":
                return cb.equal(from[0], filterValue);
            case "gender":
                GenderType gender = (GenderType) filterValue;
                return cb.equal(from[0].get("gender"), gender);
            default:
                return null;
        }
    }

}
