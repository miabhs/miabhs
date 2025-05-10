/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.CatatanAbsensiHalaqoh;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class CatatanAbsensiHalaqohFacade extends AbstractSqlFacade<CatatanAbsensiHalaqoh> {

    private static final String CATATAN_ABSENSI_QUERY
            = """
            SELECT PJ4.SN_NUM, PJ4.KPP_NUM,
                   A5.BDAS_MERAH, A5.BDAS_KUNING, A5.BDAS_WARNING, A5.NON_BDAS_MERAH, A5.NON_BDAS_KUNING,
                   PJ4.SANTRI_ID
            FROM (    
                SELECT PJ2.SN_NUM, PJ3.KPP_NUM, PJ2.SANTRI_ID
                FROM (   
                    SELECT ROW_NUMBER() OVER() AS SN_NUM, PJ1.SANTRI_ID, PJ1.KELOMPOKPENGASUHAN_ID
                    FROM (    
                        SELECT PJ0.SANTRI_ID, PS0.KELOMPOKPENGASUHAN_ID
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
                            WHERE PJX.HALAQOHPENGAJARAN_ID = ?
                            AND PJX.FROMDATE <= ? AND (PJX.THRUDATE IS NULL OR PJX.THRUDATE <= ?)
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
                    ) AS PJ1
                ) AS PJ2
                LEFT JOIN (
                    SELECT PPS3.KPP_NUM, PPS2.KELOMPOKPENGASUHAN_ID
                    FROM (
                        SELECT PPS1.USTADZ_ID, PPS1.KELOMPOKPENGASUHAN_ID
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
                                WHERE PJX.HALAQOHPENGAJARAN_ID = ?
                                AND PJX.FROMDATE <= ? AND (PJX.THRUDATE IS NULL OR PJX.THRUDATE <= ?)
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
                    ) AS PPS2
                    JOIN (
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
                                    WHERE PJX.HALAQOHPENGAJARAN_ID = ?
                                    AND PJX.FROMDATE <= ? AND (PJX.THRUDATE IS NULL OR PJX.THRUDATE <= ?)
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
                    ) AS PPS3
                    ON PPS2.USTADZ_ID = PPS3.USTADZ_ID        
                ) AS PJ3
                ON PJ2.KELOMPOKPENGASUHAN_ID = PJ3.KELOMPOKPENGASUHAN_ID
            ) AS PJ4
            JOIN (
                SELECT A4.SANTRI_ID, A4.BDAS_MERAH, A4.BDAS_KUNING, A4.BDAS_WARNING, A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING
                FROM (
                    SELECT A3.SANTRI_ID,
                       SUM(CASE WHEN BDAS = 'MERAH' THEN 1 ELSE 0 END) AS BDAS_MERAH,
                       SUM(CASE WHEN BDAS = 'KUNING' THEN 1 ELSE 0 END) AS BDAS_KUNING,
                       SUM(CASE WHEN BDAS_WARNING IS TRUE THEN 1 ELSE 0 END) AS BDAS_WARNING,
                       SUM(CASE WHEN NON_BDAS = 'MERAH' THEN 1 ELSE 0 END) AS NON_BDAS_MERAH,
                       SUM(CASE WHEN NON_BDAS = 'KUNING' THEN 1 ELSE 0 END) AS NON_BDAS_KUNING
                    FROM (
                        SELECT A2.SANTRI_ID, A2.ACTIVITYDATE,
                                (
                                 CASE WHEN (BDAS_POKOK_MERAH + BDAS_POKOK_KUNING = 0) AND (BDAS_MERAH + BDAS_KUNING <= 4)
                                      THEN 'HIJAU'
                                      WHEN (BDAS_POKOK_MERAH = 0) AND (BDAS_POKOK_KUNING >= 1 AND BDAS_POKOK_KUNING <= 4) AND (BDAS_MERAH + BDAS_KUNING <= 4)
                                      THEN 'KUNING'
                                      ELSE 'MERAH'
                                 END
                                ) AS BDAS,
                                (CASE WHEN (BDAS_POKOK_MERAH + BDAS_POKOK_KUNING = 0) AND (BDAS_MERAH + BDAS_KUNING <= 4) AND (BDAS_POKOK_MERAH + BDAS_POKOK_KUNING + BDAS_MERAH + BDAS_KUNING > 0) THEN TRUE ELSE FALSE END) AS BDAS_WARNING,
                                (
                                 CASE WHEN (NON_BDAS_MERAH > 0)
                                      THEN 'MERAH'
                                      WHEN (NON_BDAS_MERAH = 0) AND (NON_BDAS_KUNING > 0 OR NON_BDAS_ORANGE > 0)
                                      THEN 'KUNING'
                                      ELSE 'HIJAU'
                                 END
                                ) AS NON_BDAS
                        FROM (
                            SELECT A1.SANTRI_ID, A1.ACTIVITYDATE,
                                   SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK_MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_MERAH,
                                   SUM(CASE WHEN A1.JENIS = 'BDAS_POKOK_KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_POKOK_KUNING,
                                   SUM(CASE WHEN A1.JENIS = 'BDAS_MERAH' THEN FREKUENSI ELSE 0 END) AS BDAS_MERAH,
                                   SUM(CASE WHEN A1.JENIS = 'BDAS_KUNING' THEN FREKUENSI ELSE 0 END) AS BDAS_KUNING,
                                   SUM(CASE WHEN A1.JENIS = 'NON_BDAS_MERAH' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_MERAH,
                                   SUM(CASE WHEN A1.JENIS = 'NON_BDAS_KUNING' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_KUNING,
                                   SUM(CASE WHEN A1.JENIS = 'NON_BDAS_ORANGE' THEN FREKUENSI ELSE 0 END) AS NON_BDAS_ORANGE
                            FROM (
                                SELECT A0.SANTRI_ID, A0.ACTIVITYDATE, (BA0.JENIS || '_' || BA0.NILAI) AS JENIS, COUNT(BA0.NILAI) AS FREKUENSI
                                FROM MIABH_AKTIFITAS AS A0
                                JOIN MIABH_BENTUKAKTIFITAS AS BA0
                                ON A0.BENTUKAKTIFITAS_ID = BA0.ID
                                WHERE (A0.ACTIVITYDATE BETWEEN ? AND ?)
                                AND A0.SANTRI_ID IN (
                                  SELECT HQS0.TOROLE_ID AS SANTRI_ID
                                  FROM PARTY_PARTYRELATIONSHIP AS REL0
                                  JOIN MIABH_PENGAJARAN AS HQS0 
                                  ON REL0.PARTYRELATIONSHIPTYPE_ID = HQS0.PARTYRELATIONSHIPTYPE_ID
                                  AND REL0.FROMROLE_ID = HQS0.FROMROLE_ID
                                  AND REL0.TOROLE_ID = HQS0.TOROLE_ID
                                  AND REL0.FROMDATE = HQS0.FROMDATE
                                  WHERE HQS0.FROMROLE_ID = ?
                                  AND REL0.FROMDATE <= ? AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE <= ?)
                                )
                                GROUP BY A0.SANTRI_ID, A0.ACTIVITYDATE, BA0.JENIS, BA0.NILAI
                            ) AS A1
                            GROUP BY A1.SANTRI_ID, A1.ACTIVITYDATE
                        ) AS A2
                    ) AS A3
                    GROUP BY A3.SANTRI_ID
                ) AS A4
                
            ) AS A5
            ON PJ4.SANTRI_ID = A5.SANTRI_ID                         
            """;

    @Inject
    private EntityManager em;

    public CatatanAbsensiHalaqohFacade() {
        super(CatatanAbsensiHalaqoh.class);
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        HalaqohPengajaran halaqoh = (HalaqohPengajaran) parameters.get("halaqohPengajaran");
        PeriodePembelajaran currentPeriod = (PeriodePembelajaran) parameters.get("periodePembelajaran");
        PeriodePembelajaran prevPeriod = (PeriodePembelajaran) parameters.get("prevPeriodePembelajaran");
        if(prevPeriod == null) {
            prevPeriod = new PeriodePembelajaran();
            prevPeriod.setFromDate(currentPeriod.getFromDate().minus(currentPeriod.getJenisPeriode().getDuration(), currentPeriod.getJenisPeriode().getUnit()));
            prevPeriod.setThruDate(currentPeriod.getThruDate().minus(currentPeriod.getJenisPeriode().getDuration(), currentPeriod.getJenisPeriode().getUnit()));
        }

        q.setParameter(1, halaqoh != null ? halaqoh.getId() : -1);
        q.setParameter(2, currentPeriod.getFromDate());
        q.setParameter(3, currentPeriod.getThruDate());
        q.setParameter(4, currentPeriod.getFromDate());
        q.setParameter(5, currentPeriod.getThruDate());
        q.setParameter(6, currentPeriod.getFromDate());
        q.setParameter(7, currentPeriod.getFromDate());
        q.setParameter(8, halaqoh != null ? halaqoh.getId() : -1);
        q.setParameter(9, currentPeriod.getFromDate());
        q.setParameter(10, currentPeriod.getThruDate());
        q.setParameter(11, currentPeriod.getFromDate());
        q.setParameter(12, currentPeriod.getThruDate());
        q.setParameter(13, currentPeriod.getFromDate());
        q.setParameter(14, currentPeriod.getFromDate());
        q.setParameter(15, halaqoh != null ? halaqoh.getId() : -1);
        q.setParameter(16, currentPeriod.getFromDate());
        q.setParameter(17, currentPeriod.getThruDate());
        q.setParameter(18, currentPeriod.getFromDate());
        q.setParameter(19, currentPeriod.getThruDate());
        q.setParameter(20, prevPeriod.getFromDate());
        q.setParameter(21, prevPeriod.getThruDate());
        q.setParameter(22, halaqoh != null ? halaqoh.getId() : -1);
        q.setParameter(23, currentPeriod.getFromDate());
        q.setParameter(24, currentPeriod.getThruDate());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return CATATAN_ABSENSI_QUERY;
    }

}
