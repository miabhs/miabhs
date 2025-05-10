/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.PelaksanaKepengasuhan;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PelaksanaKepengasuhanFacade extends PartyRelationshipFacade<PelaksanaKepengasuhan> {

    @Inject
    private EntityManager em;

    public PelaksanaKepengasuhanFacade() {
        super(PelaksanaKepengasuhan.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "fungsi":
                In in = cb.in(from[0].get("fungsional"));
                List<FungsionalKepengasuhan> values = (List<FungsionalKepengasuhan>) filterValue;
                for (FungsionalKepengasuhan value : values) {
                    in.value(value);
                }
                return in;
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

}
