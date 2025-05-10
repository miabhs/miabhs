/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.KppHalaqoh;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class HalaqohPengajaranSqlFacade extends AbstractSqlFacade<HalaqohPengajaran> {
    
    private static final String FIND_ALL = 
            """
            SELECT HP5.ID, HP5.HALAQOH_NAME, HP5.JENISKITAB_ID, HP5.JENISKITAB_JUDUL, HP5.GENDER, HP5.KELOMPOK_WAKTU, HP5.PENGAMPU_ID, P0.FIRSTNAME AS PENGAMPU_FIRSTNAME, P0.LASTNAME AS PENGAMPU_LASTNAME
            FROM (
                SELECT HP4.ID, HP4.HALAQOH_NAME, HP4.JENISKITAB_ID, HP4.JENISKITAB_JUDUL, HP4.GENDER, HP4.KELOMPOK_WAKTU, HP4.PENGAMPU_ID, ROL0.PARTY_ID AS PENGAMPU_PERSON_ID
                FROM (
                    SELECT HP3.ID, HP3.HALAQOH_NAME, HP3.JENISKITAB_ID, HP3.JENISKITAB_JUDUL, HP3.GENDER, HP3.KELOMPOK_WAKTU, PH3.FROMROLE_ID AS PENGAMPU_ID
                    FROM (
                        SELECT HP2.ID, HP2.FIRSTNAME AS HALAQOH_NAME, HP2.JENISKITAB_ID, HP2.JENISKITAB_JUDUL, HP2.GENDER, KP0.NAME AS KELOMPOK_WAKTU
                        FROM (
                            SELECT HP1.ID, PT0.FIRSTNAME, HP1.JENISKITAB_ID, HP1.JENISKITAB_JUDUL, HP1.GENDER, HP1.KELOMPOK_ID, HP1.FROMDATE, HP1.THRUDATE
                            FROM (
                                SELECT HP0.ID, HP0.JENISKITAB_ID, HP0.JENISKITAB_JUDUL, HP0.GENDER, HP0.KELOMPOK_ID, ROL0.PARTY_ID, ROL0.FROMDATE, ROL0.THRUDATE
                                FROM (
                                    SELECT HP.ID, HP.JENISKITAB_ID, JK.JUDUL AS JENISKITAB_JUDUL, HP.GENDER, HP.KELOMPOK_ID
                                    FROM MIABH_HALAQOHPENGAJARAN AS HP
                                    JOIN MIABH_JENISKITAB AS JK
                                    ON HP.JENISKITAB_ID = JK.ID
                                ) AS HP0
                                JOIN PARTY_PARTYROLE AS ROL0
                                ON HP0.ID = ROL0.ID AND ROL0.PARTYROLETYPE_ID = 'HalaqohPengajaran'
                            ) AS HP1
                            JOIN PARTY_PARTY AS PT0
                            ON HP1.PARTY_ID = PT0.ID
                        ) AS HP2
                        JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KP0
                        ON HP2.KELOMPOK_ID = KP0.ID
                    ) AS HP3
                    LEFT JOIN (
                        SELECT PH1.FROMROLE_ID, PH1.TOROLE_ID
                        FROM (
                            SELECT PH0.FROMROLE_ID, PH0.TOROLE_ID, PH0.FROMDATE
                            FROM MIABH_PENGAMPUHALAQOH AS PH0
                            JOIN PARTY_PARTYRELATIONSHIP AS REL0
                            ON PH0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                            AND PH0.FROMROLE_ID = REL0.FROMROLE_ID
                            AND PH0.TOROLE_ID = REL0.TOROLE_ID
                            AND PH0.FROMDATE = REL0.FROMDATE
                            WHERE PH0.FROMDATE <= CURRENT_DATE
                            AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE >= CURRENT_DATE)
                        ) AS PH1
                        JOIN (
                            SELECT PH0.TOROLE_ID, MAX(PH0.FROMDATE) AS FROMDATE
                            FROM MIABH_PENGAMPUHALAQOH AS PH0
                            JOIN PARTY_PARTYRELATIONSHIP AS REL0
                            ON PH0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                            AND PH0.FROMROLE_ID = REL0.FROMROLE_ID
                            AND PH0.TOROLE_ID = REL0.TOROLE_ID
                            AND PH0.FROMDATE = REL0.FROMDATE
                            WHERE PH0.FROMDATE <= CURRENT_DATE
                            AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE >= CURRENT_DATE)
                            GROUP BY PH0.TOROLE_ID, PH0.FROMDATE
                        ) AS PH2
                        ON PH1.TOROLE_ID = PH2.TOROLE_ID AND PH1.FROMDATE = PH2.FROMDATE
                    ) AS PH3
                    ON HP3.ID = PH3.TOROLE_ID
                ) AS HP4
                LEFT JOIN PARTY_PARTYROLE AS ROL0
                ON HP4.PENGAMPU_ID = ROL0.ID
            ) AS HP5
            LEFT JOIN PARTY_PARTY AS P0
            ON HP5.PENGAMPU_PERSON_ID = P0.ID          
            """;
    
    private static final String FIND
            = """
              SELECT HP5.ID, HP5.HALAQOH_NAME, HP5.JENISKITAB_ID, HP5.JENISKITAB_JUDUL, HP5.GENDER, HP5.KELOMPOK_WAKTU, HP5.PENGAMPU_ID, P0.FIRSTNAME AS PENGAMPU_FIRSTNAME, P0.LASTNAME AS PENGAMPU_LASTNAME
              FROM (    
                  SELECT HP4.ID, HP4.HALAQOH_NAME, HP4.JENISKITAB_ID, HP4.JENISKITAB_JUDUL, HP4.GENDER, HP4.KELOMPOK_WAKTU, HP4.PENGAMPU_ID, ROL0.PARTY_ID AS PENGAMPU_PERSON_ID
                  FROM (    
                      SELECT HP3.ID, HP3.HALAQOH_NAME, HP3.JENISKITAB_ID, HP3.JENISKITAB_JUDUL, HP3.GENDER, HP3.KELOMPOK_WAKTU, PH3.FROMROLE_ID AS PENGAMPU_ID
                      FROM (
                          SELECT HP2.ID, HP2.FIRSTNAME AS HALAQOH_NAME, HP2.JENISKITAB_ID, HP2.JENISKITAB_JUDUL, HP2.GENDER, KP0.NAME AS KELOMPOK_WAKTU
                          FROM (
                              SELECT HP1.ID, PT0.FIRSTNAME, HP1.JENISKITAB_ID, HP1.JENISKITAB_JUDUL, HP1.GENDER, HP1.KELOMPOK_ID, HP1.FROMDATE, HP1.THRUDATE
                              FROM (
                                  SELECT HP0.ID, HP0.JENISKITAB_ID, HP0.JENISKITAB_JUDUL, HP0.GENDER, HP0.KELOMPOK_ID, ROL0.PARTY_ID, ROL0.FROMDATE, ROL0.THRUDATE
                                  FROM (
                                      SELECT HP.ID, HP.JENISKITAB_ID, HP.JENISKITAB_JUDUL, HP.GENDER, HP.KELOMPOK_ID
                                      FROM (
                                        SELECT HPX.ID, HPX.JENISKITAB_ID, JK.JUDUL AS JENISKITAB_JUDUL, HPX.GENDER, HPX.KELOMPOK_ID
                                        FROM MIABH_HALAQOHPENGAJARAN AS HPX
                                        JOIN MIABH_JENISKITAB AS JK
                                        ON HPX.JENISKITAB_ID = JK.ID
                                      ) AS HP
                                      WHERE HP.ID = ?
                                  ) AS HP0
                                  JOIN PARTY_PARTYROLE AS ROL0
                                  ON HP0.ID = ROL0.ID AND ROL0.PARTYROLETYPE_ID = 'HalaqohPengajaran'
                              ) AS HP1
                              JOIN PARTY_PARTY AS PT0
                              ON HP1.PARTY_ID = PT0.ID
                          ) AS HP2
                          JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KP0
                          ON HP2.KELOMPOK_ID = KP0.ID
                      ) AS HP3 
                      LEFT JOIN (
                          SELECT PH1.FROMROLE_ID, PH1.TOROLE_ID
                          FROM (
                              SELECT PH0.FROMROLE_ID, PH0.TOROLE_ID, PH0.FROMDATE
                              FROM MIABH_PENGAMPUHALAQOH AS PH0
                              JOIN PARTY_PARTYRELATIONSHIP AS REL0
                              ON PH0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                              AND PH0.FROMROLE_ID = REL0.FROMROLE_ID
                              AND PH0.TOROLE_ID = REL0.TOROLE_ID
                              AND PH0.FROMDATE = REL0.FROMDATE
                              WHERE PH0.FROMDATE <= CURRENT_DATE
                              AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE >= CURRENT_DATE)
                          ) AS PH1
                          JOIN (
                              SELECT PH0.TOROLE_ID, MAX(PH0.FROMDATE) AS FROMDATE
                              FROM MIABH_PENGAMPUHALAQOH AS PH0
                              JOIN PARTY_PARTYRELATIONSHIP AS REL0
                              ON PH0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                              AND PH0.FROMROLE_ID = REL0.FROMROLE_ID
                              AND PH0.TOROLE_ID = REL0.TOROLE_ID
                              AND PH0.FROMDATE = REL0.FROMDATE
                              WHERE PH0.FROMDATE <= CURRENT_DATE
                              AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE >= CURRENT_DATE)
                              GROUP BY PH0.TOROLE_ID, PH0.FROMDATE
                          ) AS PH2
                          ON PH1.TOROLE_ID = PH2.TOROLE_ID AND PH1.FROMDATE = PH2.FROMDATE
                      ) AS PH3
                      ON HP3.ID = PH3.TOROLE_ID
                  ) AS HP4
                  LEFT JOIN PARTY_PARTYROLE AS ROL0
                  ON HP4.PENGAMPU_ID = ROL0.ID
              ) AS HP5
              LEFT JOIN PARTY_PARTY AS P0
              ON HP5.PENGAMPU_PERSON_ID = P0.ID
              """;

    private static final String KPP_QUERY
            = """
            SELECT PPS0.KPP_NUM, P0.FIRSTNAME, P0.LASTNAME
            FROM (
                SELECT PPS.KPP_NUM, ROL.PARTY_ID
                FROM (    
                    SELECT ROW_NUMBER() OVER () AS KPP_NUM, PPSX.USTADZ_ID
                    FROM (    
                        SELECT PPS1.USTADZ_ID
                        FROM (
                            SELECT PPS0.FROMROLE_ID AS USTADZ_ID, PPS0.TOROLE_ID AS KELOMPOKPENGASUHAN_ID,
                                   PPS0.FUNGSIONAL, REL0.FROMDATE, REL0.THRUDATE
                            FROM MIABH_PELAKSANAKEPENGASUHAN AS PPS0
                            JOIN PARTY_PARTYRELATIONSHIP AS REL0
                            ON PPS0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                            AND PPS0.FROMROLE_ID = REL0.FROMROLE_ID
                            AND PPS0.TOROLE_ID = REL0.TOROLE_ID
                            AND PPS0.FROMDATE = REL0.FROMDATE
                        ) AS PPS1
                        WHERE PPS1.FROMDATE <= ? AND (PPS1.THRUDATE IS NULL OR PPS1.THRUDATE >= ?)
                        AND PPS1.FUNGSIONAL = 'PELAKSANA_KEPENGASUHAN'
                        AND PPS1.KELOMPOKPENGASUHAN_ID IN (    
                            SELECT PS0.KELOMPOKPENGASUHAN_ID
                            FROM (
                                SELECT PJX.HALAQOHPENGAJARAN_ID, PJX.SANTRI_ID
                                FROM (
                                    SELECT PJ.FROMROLE_ID AS HALAQOHPENGAJARAN_ID, PJ.TOROLE_ID AS SANTRI_ID,
                                           REL.FROMDATE, REL.THRUDATE
                                    FROM MIABH_PENGAJARAN AS PJ
                                    JOIN PARTY_PARTYRELATIONSHIP AS REL
                                    ON PJ.PARTYRELATIONSHIPTYPE_ID = REL.PARTYRELATIONSHIPTYPE_ID
                                    AND PJ.FROMROLE_ID = REL.FROMROLE_ID AND PJ.TOROLE_ID = REL.TOROLE_ID
                                    AND PJ.FROMDATE = REL.FROMDATE
                                ) AS PJX
                                WHERE PJX.FROMDATE <= ? AND (PJX.THRUDATE IS NULL OR PJX.THRUDATE <= ?)
                                AND PJX.HALAQOHPENGAJARAN_ID = ?
                                ORDER BY PJX.FROMDATE ASC
                            ) AS PJ0 
                            LEFT JOIN (
                                SELECT PSX.KELOMPOKPENGASUHAN_ID, PSX.SANTRI_ID
                                FROM (
                                    SELECT PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PS.TOROLE_ID AS SANTRI_ID,
                                           REL.FROMDATE, REL.THRUDATE
                                    FROM MIABH_PENGASUHAN AS PS
                                    JOIN PARTY_PARTYRELATIONSHIP AS REL
                                    ON PS.PARTYRELATIONSHIPTYPE_ID = REL.PARTYRELATIONSHIPTYPE_ID
                                    AND PS.FROMROLE_ID = REL.FROMROLE_ID AND PS.TOROLE_ID = REL.TOROLE_ID
                                    AND PS.FROMDATE = REL.FROMDATE
                                ) AS PSX
                                WHERE PSX.FROMDATE <= ? AND (PSX.THRUDATE IS NULL OR PSX.THRUDATE <= ?)
                            ) AS PS0
                            ON PJ0.SANTRI_ID = PS0.SANTRI_ID
                        )
                        GROUP BY PPS1.USTADZ_ID
                    ) AS PPSX
                ) AS PPS
                JOIN PARTY_PARTYROLE AS ROL
                ON PPS.USTADZ_ID = ROL.ID
            ) AS PPS0
            JOIN PARTY_PARTY AS P0
            ON PPS0.PARTY_ID = P0.ID
            """;

    @Inject
    private EntityManager em;

    @Inject
    private HalaqohPengajaranFacade halaqohPengajaranFacade;
    
    public HalaqohPengajaranSqlFacade() {
        super(HalaqohPengajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Result<String> create(HalaqohPengajaran entity) {
        return halaqohPengajaranFacade.create(entity);
    }

    @Override
    public Result<String> edit(HalaqohPengajaran entity) {
        return halaqohPengajaranFacade.edit(entity);
    }

    @Override
    public Result<String> remove(HalaqohPengajaran entity) {
        return halaqohPengajaranFacade.remove(entity);
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected String getFindQuery() {
        return FIND;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch(filterData.name) {
            case "id":
                return "HP5.ID = " + filterData.value;
            default:
                return super.applyFilter(filterData);
        }
    }
    
    public List<KppHalaqoh> getListKpp(PeriodePembelajaran currentPeriod, HalaqohPengajaran halaqoh) {
        Query q = em.createNativeQuery(KPP_QUERY, "KppHalaqoh");
        q.setParameter(1, currentPeriod.getFromDate());
        q.setParameter(2, currentPeriod.getFromDate());
        q.setParameter(3, currentPeriod.getFromDate());
        q.setParameter(4, currentPeriod.getThruDate());

        Long halaqohId = halaqoh != null ? halaqoh.getId() : -1L;
        q.setParameter(5, halaqohId);

        q.setParameter(6, currentPeriod.getFromDate());
        q.setParameter(7, currentPeriod.getThruDate());

        return q.getResultList();
    }


}
