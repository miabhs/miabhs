/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaran;
import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaranValue;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.core.base.dao.AbstractHierarchicalFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class JenisKitabFacade extends AbstractHierarchicalFacade<JenisKitab> {

    private static final String ATRIBUTKITAB_QUERY
            = """
              SELECT ATT.ALLOWABLEVALUES 
              FROM MIABH_ATRIBUTKITAB AS ATT
              WHERE ATT.JENISKITAB_ID = ? AND ATT.NAME = ?
            """;

    private static final String ATRIBUTKITAB_FORMAT_1
            = "^([0-9]+)(\\-{1})([0-9]+)$";

    private static final String ATRIBUTKITAB_FORMAT_2
            = "(?<A1>\\d+)(:{1})(?<A2>\\w[\\d \\w \\' \\, \\- \\( \\)]+)(;?)";

    @Inject
    private EntityManager em;

    public JenisKitabFacade() {
        super(JenisKitab.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Predicate applyFilter(String filterName, Object filterValue, CriteriaQuery cq, From... froms) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterName) {
            case "kategori":
                return filterValue != null ? cb.equal(froms[0].get("kategoriKitab"), filterValue)
                        : cb.isNull(froms[0].get("kategoriKitab"));
            case "id":
                JenisKitab jenisKitab = find(filterValue);
                if (jenisKitab.isBerjilid()) {
                    return cb.equal(froms[0].get("parent"), jenisKitab);
                } else {
                    return cb.equal(froms[0].get("id"), filterValue);
                }
            default:
                return super.applyFilter(filterName, filterValue, cq, froms);
        }
    }

    public List<AtributPembelajaran> getAtributPembelajaranKitab(Long kitabId) {
        JenisKitab kitab = find(kitabId);
        if (kitab != null) {
            return kitab.getListAtribut();
        } else {
            return new ArrayList<>();
        }
    }

    public List<AtributPembelajaranValue> getAtributOptions(Long kitabId, String attName) {
        Query q = em.createNativeQuery(ATRIBUTKITAB_QUERY)
                .setParameter(1, kitabId)
                .setParameter(2, attName);

        String result = (String) q.getSingleResult();

        List<AtributPembelajaranValue> values = new ArrayList<>();

        if (result.matches(ATRIBUTKITAB_FORMAT_1)) {
            String[] sval = result.split("-");
            int min = Integer.parseInt(sval[0]);
            int max = Integer.parseInt(sval[1]);
            for (int val = min; val <= max; val++) {
                String marker = String.valueOf(val);
                values.add(new AtributPembelajaranValue(marker, null, null));
            }
        } else if (result.matches("(" + ATRIBUTKITAB_FORMAT_2 + ")+")) {
            Matcher m = Pattern.compile(ATRIBUTKITAB_FORMAT_2).matcher(result);
            while (m.find()) {
                String marker = m.group("A1").trim();
                String deskripsi = m.group("A2").trim();
                values.add(new AtributPembelajaranValue(marker, deskripsi, null));
            }
        }

        return values;
    }
}
