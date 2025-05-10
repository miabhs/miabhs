/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class TahunPembelajaranFacade extends AbstractFacade<TahunPembelajaran> {

    @Inject
    private EntityManager em;

    public TahunPembelajaranFacade() {
        super(TahunPembelajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        if(filterValue == null) return null;
        
        switch (filterName) {
            case "name":
                return cb.like(from[0].get("name"), "%" + filterValue + "%");
            case "date":
                LocalDate date = (LocalDate) filterValue;
                return cb.and(
                        cb.lessThanOrEqualTo(from[0].get("fromDate"), date),
                        cb.greaterThanOrEqualTo(from[0].get("thruDate"), date)
                );
            default:
                return null;
        }
    }

    @Override
    protected Expression orderExpression(String field, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        switch (field) {
            case "fromDate":
            default:
                return from[0].get("fromDate");
        }
    }
    
    public TahunPembelajaran getCurrentTahunPembelajaran() {        
        return findSingleByAttributes(FilterData.by("date", LocalDate.now()));
    }

}
