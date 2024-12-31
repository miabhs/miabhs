/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractHierarchicalFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Stateless
public class PeriodePembelajaranFacade extends AbstractHierarchicalFacade<PeriodePembelajaran> {

    @Inject
    private EntityManager em;

    @Inject
    private QueryHelper helper;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodePembelajaranFacade() {
        super(PeriodePembelajaran.class);
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... from) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "tahunPembelajaran":
                if (filterValue != null) {
                    return cb.equal(from[0].get("tahunPembelajaran"), filterValue);
                } else {
                    return cb.isNull(from[0].get("tahunPembelajaran"));
                }
            default:
                return super.applyFilter(filterName, filterValue, cq, from);
        }
    }

    public List<PeriodePembelajaran> getChildren(PeriodePembelajaran parentPeriod) {
        return findAll(Arrays.asList(new FilterData("parent", parentPeriod)));
    }

    public PeriodePembelajaran getPeriod(String periodName, LocalDateTime fromDate, LocalDateTime thruDate) {
        return helper.queryOne(em,
                "PeriodePengasuhan.getPeriod",
                PeriodePembelajaran.class,
                new QueryParameter[]{
                    new QueryParameter("fromDate", fromDate),
                    new QueryParameter("thruDate", thruDate)
                }
        );
    }

    public PeriodePembelajaran getPrevPeriodOf(PeriodePembelajaran currPeriod) {
        return helper.queryOne(em,
                "PeriodePengasuhan.getPrevPeriod",
                PeriodePembelajaran.class,
                new QueryParameter("fromDate", currPeriod.getFromDate()));
    }

    @Override
    protected Expression orderExpression(String field, From... from) {

        return switch (field) {
            case "id.fromDate" ->
                from[0].get("fromDate");
            default ->
                null;
        };
    }

}
