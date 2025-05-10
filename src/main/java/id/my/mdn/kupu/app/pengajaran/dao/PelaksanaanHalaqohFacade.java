/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.PelaksanaanHalaqoh;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aphasan
 */
@Stateless
public class PelaksanaanHalaqohFacade extends AbstractFacade<PelaksanaanHalaqoh> {

    @Inject
    private EntityManager em;

    public PelaksanaanHalaqohFacade() {
        super(PelaksanaanHalaqoh.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<PelaksanaanHalaqoh> getListDate(HalaqohPengajaran halaqohPengajaran, PeriodePembelajaran periodePembelajaran, Integer... indexes) {

        if (periodePembelajaran == null) {
            List<PelaksanaanHalaqoh> fallback = new ArrayList<>();

            return fallback;
        }

        List<String> selectors = new ArrayList<>();

        String selector = """
                          SELECT (PB.FROMDATE + %d) AS PENGAJARANDATE
                          FROM MIABH_PERIODEPEMBELAJARAN AS PB
                          WHERE PB.FLAG = 'PEKANAN'
                          AND PB.FROMDATE = ? AND PB.THRUDATE = ?
                          """;

        if (indexes == null || indexes.length == 0) {
            int durasiPeriodePembelajaran = periodePembelajaran.getJenisPeriode().getDuration();
            indexes = new Integer[durasiPeriodePembelajaran];
            for (int i = 1; i <= durasiPeriodePembelajaran; i++) {
                indexes[i] = i;
            }
        }

        for (Integer indexe : indexes) {
            selectors.add(selector.formatted(indexe));
        }

        StringBuilder queryBuilder = new StringBuilder(
                """
                  SELECT PB0.PENGAJARANDATE, (CASE WHEN LH0.CUSTOMTIME IS NULL THEN FALSE ELSE LH0.CUSTOMTIME END) AS CUSTOMTIME, LH0.STARTTIME, LH0.FINISHTIME
                  FROM (
                  """
        ).append(
                selectors.stream().collect(Collectors.joining("\nUNION\n"))
        ).append(
                """
              ) AS PB0
              LEFT JOIN (
                  SELECT LH.PENGAJARANDATE, LH.CUSTOMTIME,  LH.STARTTIME, LH.FINISHTIME
                  FROM MIABH_PELAKSANAANHALAQOH AS LH
                  WHERE LH.PENGAJARANDATE >= ? AND LH.PENGAJARANDATE <= ?
                  AND LH.HALAQOHPENGAJARAN_ID = ? AND LH.CUSTOMTIME IS TRUE
              ) AS LH0
              ON PB0.PENGAJARANDATE = LH0.PENGAJARANDATE
            ORDER BY PB0.PENGAJARANDATE
                """
        );

        Query q = em.createNativeQuery(queryBuilder.toString(), "PelaksanaanHalaqoh")
                .setParameter(1, periodePembelajaran.getFromDate())
                .setParameter(2, periodePembelajaran.getThruDate())
                .setParameter(3, periodePembelajaran.getFromDate())
                .setParameter(4, periodePembelajaran.getThruDate())
                .setParameter(5, periodePembelajaran.getFromDate())
                .setParameter(6, periodePembelajaran.getThruDate())
                .setParameter(7, periodePembelajaran.getFromDate())
                .setParameter(8, periodePembelajaran.getThruDate())
                .setParameter(9, periodePembelajaran.getFromDate())
                .setParameter(10, periodePembelajaran.getThruDate())
                .setParameter(11, periodePembelajaran.getFromDate())
                .setParameter(12, periodePembelajaran.getThruDate())
                .setParameter(13, periodePembelajaran.getFromDate())
                .setParameter(14, periodePembelajaran.getThruDate())
                .setParameter(15, halaqohPengajaran != null ? halaqohPengajaran.getId() : -1L);

        return q.getResultList();
    }

    public void createOrUpdate(PelaksanaanHalaqoh pelaksanaanHalaqoh) {

        PelaksanaanHalaqoh existingEntity = find(pelaksanaanHalaqoh.getId());

        if (existingEntity != null) {
            if (pelaksanaanHalaqoh.isCustomTime()) {
                edit(pelaksanaanHalaqoh);
            } else {
                remove(existingEntity);
                pelaksanaanHalaqoh.setCustomTime(false);
                pelaksanaanHalaqoh.setStartTime(null);
                pelaksanaanHalaqoh.setFinishTime(null);
                pelaksanaanHalaqoh.setHalaqohPengajaran(null);
                pelaksanaanHalaqoh.getId().setHalaqohPengajaran(null);
            }
        } else {
            create(pelaksanaanHalaqoh);
        }

    }

}
