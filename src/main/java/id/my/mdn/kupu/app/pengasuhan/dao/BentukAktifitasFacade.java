/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class BentukAktifitasFacade extends AbstractFacade<BentukAktifitas> {

    @Inject
    private EntityManager em;

    public BentukAktifitasFacade() {
        super(BentukAktifitas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "jenis":
                return cb.equal(from[0].get("jenis"), filterValue);
            case "bentuk":
                String query = (String) filterValue;
                if (query == null || query.isEmpty()) {
                    return null;
                }
                Expression<String> name = cb.literal("%" + query.toUpperCase() + "%");
                return cb.like(cb.upper(from[0].get("bentuk")), name);
            default:
                return null;
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        if (field.equals("kode")) {
            return from[0].get("kode");
        } else if (field.equals("created")) {
            return from[0].get("created");
        } else {
            return null;
        }
    }

}
