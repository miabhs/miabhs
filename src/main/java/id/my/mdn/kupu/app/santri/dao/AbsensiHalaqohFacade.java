/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.AbsensiHalaqoh;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class AbsensiHalaqohFacade extends AbstractSqlFacade<AbsensiHalaqoh> {

    private static final String ABSENSI_QUERY
            = """
            SELECT PG5.SANTRI_ID, PG5.FIRSTNAME, PG5.LASTNAME, PG5.NIS, PG5.TAHUNMASUK_NAME, PG5.TAHUNMASUK_FROMDATE,
                   A4.BDAS_MERAH, A4.BDAS_KUNING, A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING
            FROM (
                SELECT PG4.PARTY_ID, PG4.SANTRI_ID, PG4.NIS, P0.FIRSTNAME, P0.LASTNAME, PG4.TAHUNMASUK_NAME, PG4.TAHUNMASUK_FROMDATE
                FROM ( 
                    SELECT ROL0.PARTY_ID, PG3.SANTRI_ID, PG3.NIS, PG3.TAHUNMASUK_NAME, PG3.TAHUNMASUK_FROMDATE
                    FROM (
                        SELECT PG2.SANTRI_ID, PG2.NIS, TH0.NAME AS TAHUNMASUK_NAME, TH0.FROMDATE AS TAHUNMASUK_FROMDATE
                        FROM (
                            SELECT PG1.TOROLE_ID AS SANTRI_ID, S0.NIS, S0.TAHUNMASUK_ID
                            FROM (
                                SELECT PG0.TOROLE_ID
                                FROM MIABH_PENGAJARAN AS PG0
                                JOIN PARTY_PARTYRELATIONSHIP AS REL0
                                ON PG0.PARTYRELATIONSHIPTYPE_ID = REL0.PARTYRELATIONSHIPTYPE_ID
                                AND PG0.FROMROLE_ID = REL0.FROMROLE_ID AND PG0.TOROLE_ID = REL0.TOROLE_ID
                                AND PG0.FROMDATE = REL0.FROMDATE 
                                AND PG0.FROMROLE_ID = ?
                                AND REL0.FROMDATE <= ? AND (REL0.THRUDATE IS NULL OR REL0.THRUDATE >= ?)
                            ) AS PG1
                            JOIN MIABH_SANTRI S0
                            ON PG1.TOROLE_ID = S0.ID
                        ) AS PG2
                        JOIN MIABH_TAHUNPEMBELAJARAN AS TH0
                        ON PG2.TAHUNMASUK_ID = TH0.ID
                    ) AS PG3
                    JOIN PARTY_PARTYROLE AS ROL0
                    ON PG3.SANTRI_ID = ROL0.ID
                ) AS PG4
                JOIN PARTY_PARTY AS P0
                ON PG4.PARTY_ID = P0.ID
            ) AS PG5
            LEFT JOIN (
                SELECT A3.SANTRI_ID,
                   SUM(CASE WHEN BDAS = 'MERAH' THEN 1 ELSE 0 END) AS BDAS_MERAH,
                   SUM(CASE WHEN BDAS = 'KUNING' THEN 1 ELSE 0 END) AS BDAS_KUNING,
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
                              FROM MIABH_PENGAJARAN AS HQS0
                              WHERE HQS0.FROMROLE_ID = ?
                            )
                            GROUP BY A0.SANTRI_ID, A0.ACTIVITYDATE, BA0.JENIS, BA0.NILAI
                        ) AS A1
                        GROUP BY A1.SANTRI_ID, A1.ACTIVITYDATE
                    ) AS A2
                ) AS A3
                GROUP BY A3.SANTRI_ID
            ) AS A4
            ON PG5.SANTRI_ID = A4.SANTRI_ID            
            """;

    @Inject
    private EntityManager em;

    public AbsensiHalaqohFacade() {
        super(AbsensiHalaqoh.class);
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        HalaqohPengajaran halaqoh = (HalaqohPengajaran) parameters.get("halaqohPengajaran");
        PeriodePembelajaran currentPeriod = (PeriodePembelajaran) parameters.get("periodePembelajaran");
        PeriodePembelajaran prevPeriod = (PeriodePembelajaran) parameters.get("prevPeriodePembelajaran");
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);
        
        String currentPeriodFromDate = dateFormatter.format(currentPeriod.getFromDate());
        String currentPeriodThruDate = dateFormatter.format(currentPeriod.getThruDate());
        
        String prevPeriodFromDate = dateFormatter.format(prevPeriod.getFromDate());
        String prevPeriodThruDate = dateFormatter.format(prevPeriod.getThruDate());
        
        q.setParameter(1, halaqoh != null ? halaqoh.getId() : -1);
        q.setParameter(2, currentPeriodFromDate);
        q.setParameter(3, currentPeriodThruDate);
        q.setParameter(4, prevPeriodFromDate);
        q.setParameter(5, prevPeriodThruDate);
        q.setParameter(6, halaqoh != null ? halaqoh.getId() : -1);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return ABSENSI_QUERY;
    }

}
