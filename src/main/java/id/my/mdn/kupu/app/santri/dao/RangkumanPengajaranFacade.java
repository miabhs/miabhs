/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.pengajaran.dao.PengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import static id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran.PEKANAN;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.RangkumanPengajaran;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.Constants;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class RangkumanPengajaranFacade extends AbstractSqlFacade<RangkumanPengajaran> {

    private static final String FIND_ALL
            = """
            SELECT PJ5.HALAQOH_ID, PJ5.SANTRI_ID, PJ5.PENGAJARAN_FROMDATE,
                   PJ5.KITAB_ID, PJ5.KITAB_KODE,
                   PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PS.FROMDATE AS PENGASUHAN_FROMDATE,
                   CONCAT_WS(' ', PJ5.FIRSTNAME, PJ5.LASTNAME) AS NAME,
                   PJ5.NIS, PJ5.TAHUNMASUK_NAME, PJ5.TAHUNMASUK_FROMDATE,
                   PJ5.BDAS_MERAH, PJ5.BDAS_KUNING, PJ5.BDAS_WARNING, PJ5.NON_BDAS_MERAH, PJ5.NON_BDAS_KUNING,
                   PJ5.NASEHAT,
                   PJ5.D1, (CASE WHEN PJ5.K1 IS NULL THEN 'HADIR' ELSE PJ5.K1 END) AS K1 , (CASE WHEN PJ5.S1 IS NULL THEN 'E' ELSE PJ5.S1 END) AS S1, 
                   PJ5.D2, (CASE WHEN PJ5.K2 IS NULL THEN 'HADIR' ELSE PJ5.K2 END) AS K2 , (CASE WHEN PJ5.S2 IS NULL THEN 'E' ELSE PJ5.S2 END) AS S2,  
                   PJ5.D3, (CASE WHEN PJ5.K3 IS NULL THEN 'HADIR' ELSE PJ5.K3 END) AS K3 , (CASE WHEN PJ5.S3 IS NULL THEN 'E' ELSE PJ5.S3 END) AS S3,  
                   PJ5.D4, (CASE WHEN PJ5.K4 IS NULL THEN 'HADIR' ELSE PJ5.K4 END) AS K4 , (CASE WHEN PJ5.S4 IS NULL THEN 'E' ELSE PJ5.S4 END) AS S4,  
                   PJ5.D5, (CASE WHEN PJ5.K5 IS NULL THEN 'HADIR' ELSE PJ5.K5 END) AS K5 , (CASE WHEN PJ5.S5 IS NULL THEN 'E' ELSE PJ5.S5 END) AS S5,   
                   PJ5.D6, (CASE WHEN PJ5.K6 IS NULL THEN 'HADIR' ELSE PJ5.K6 END) AS K6 , (CASE WHEN PJ5.S6 IS NULL THEN 'E' ELSE PJ5.S6 END) AS S6
            FROM (
                SELECT PJ4.HALAQOH_ID, PJ4.SANTRI_ID, PJ4.PENGAJARAN_FROMDATE,
                       PJ4.FIRSTNAME, PJ4.LASTNAME,
                       PJ4.NIS, PJ4.TAHUNMASUK_NAME, PJ4.TAHUNMASUK_FROMDATE,
                       PJ4.KITAB_ID, PJ4.KITAB_KODE,
                       (CASE WHEN PJ4.BDAS_MERAH IS NULL THEN 0 ELSE PJ4.BDAS_MERAH END) AS BDAS_MERAH, 
                       (CASE WHEN PJ4.BDAS_KUNING IS NULL THEN 0 ELSE PJ4.BDAS_KUNING END) AS BDAS_KUNING,
                       (CASE WHEN PJ4.BDAS_WARNING > 0 THEN TRUE ELSE FALSE END) AS BDAS_WARNING,
                       (CASE WHEN PJ4.NON_BDAS_MERAH IS NULL THEN 0 ELSE PJ4.NON_BDAS_MERAH END) AS NON_BDAS_MERAH, 
                       (CASE WHEN PJ4.NON_BDAS_KUNING IS NULL THEN 0 ELSE PJ4.NON_BDAS_KUNING END) AS NON_BDAS_KUNING,          
                       (
                          SELECT (CASE WHEN COUNT(*) = 0 THEN FALSE ELSE TRUE END) AS NASEHAT
                          FROM MIABH_PENASEHATANSANTRI AS PS0
                          WHERE PS0.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND PS0.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND PS0.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND PS0.PENGAJARANDATE >= PJ4.D1 AND PS0.PENGAJARANDATE <= PJ4.D6
                       ) AS NASEHAT,
                       PJ4.D1,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D1
                       ) AS K1,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D1
                          )
                       ) AS S1,
                       PJ4.D2,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D2
                       ) AS K2,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D2
                          )
                       ) AS S2,
                       PJ4.D3,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D3
                       ) AS K3,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D3
                          )
                       ) AS S3,
                       PJ4.D4,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D4
                       ) AS K4,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D4
                          )
                       ) AS S4,
                       PJ4.D5,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D5
                       ) AS K5,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D5
                          )
                       ) AS S5,
                       PJ4.D6,
                       (
                          SELECT HB.PRESENSI
                          FROM MIABH_PRESENSISANTRI AS HB
                          WHERE HB.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                          AND HB.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                          AND HB.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                          AND HB.PENGAJARANDATE = PJ4.D6
                       ) AS K6,
                       (
                          SELECT PB0.SETORAN
                          FROM MIABH_PENCAPAIANBELAJAR AS PB0
                          WHERE PB0.ID = (
                            SELECT SS.PENCAPAIAN_ID
                            FROM MIABH_SETORANSANTRI AS SS
                            WHERE SS.PENGAJARAN_FROMROLE_ID = PJ4.HALAQOH_ID
                            AND SS.PENGAJARAN_TOROLE_ID = PJ4.SANTRI_ID
                            AND SS.PENGAJARAN_FROMDATE = PJ4.PENGAJARAN_FROMDATE
                            AND SS.PENGAJARANDATE = PJ4.D6
                          )
                       ) AS S6
                FROM (
                    SELECT PJ3.HALAQOH_ID, PJ3.SANTRI_ID, PJ3.PENGAJARAN_FROMDATE,
                           PJ3.FIRSTNAME, PJ3.LASTNAME,
                           PJ3.NIS, PJ3.TAHUNMASUK_NAME, PJ3.TAHUNMASUK_FROMDATE,
                           PJ3.KITAB_ID, PJ3.KITAB_KODE,
                           PJ3.BDAS_MERAH, PJ3.BDAS_KUNING, PJ3.BDAS_WARNING, PJ3.NON_BDAS_MERAH, PJ3.NON_BDAS_KUNING,
                           PB0.D1, PB0.D2, PB0.D3, PB0.D4, PB0.D5, PB0.D6
                    FROM (
                        SELECT PG5.HALAQOH_ID, PG5.SANTRI_ID, PG5.PENGAJARAN_FROMDATE,
                               PG5.FIRSTNAME, PG5.LASTNAME,
                               PG5.NIS, PG5.TAHUNMASUK_NAME, PG5.TAHUNMASUK_FROMDATE,
                               PG5.KITAB_ID, PG5.KITAB_KODE,
                               A4.BDAS_MERAH, A4.BDAS_KUNING, A4.BDAS_WARNING, A4.NON_BDAS_MERAH, A4.NON_BDAS_KUNING
                        FROM (
                            SELECT PG4.PARTY_ID, PG4.HALAQOH_ID, PG4.SANTRI_ID, PG4.PENGAJARAN_FROMDATE, PG4.NIS, P0.FIRSTNAME, P0.LASTNAME, PG4.TAHUNMASUK_NAME, PG4.TAHUNMASUK_FROMDATE,
                                   PG4.KITAB_ID, PG4.KITAB_KODE
                            FROM (
                                SELECT ROL0.PARTY_ID, PG3.HALAQOH_ID, PG3.SANTRI_ID, PG3.PENGAJARAN_FROMDATE, PG3.NIS, PG3.TAHUNMASUK_NAME, PG3.TAHUNMASUK_FROMDATE,
                                       PG3.KITAB_ID, PG3.KITAB_KODE
                                FROM (
                                    SELECT PG2.HALAQOH_ID, PG2.SANTRI_ID, PG2.PENGAJARAN_FROMDATE, PG2.NIS, TH0.NAME AS TAHUNMASUK_NAME, TH0.FROMDATE AS TAHUNMASUK_FROMDATE,
                                           PG2.KITAB_ID, PG2.KITAB_KODE
                                    FROM (
                                        SELECT PG1.FROMROLE_ID AS HALAQOH_ID, PG1.TOROLE_ID AS SANTRI_ID, PG1.PENGAJARAN_FROMDATE, S0.NIS, S0.TAHUNMASUK_ID, PG1.KITAB_ID, PG1.KITAB_KODE
                                        FROM (
                                            SELECT PG0.FROMROLE_ID, PG0.TOROLE_ID, PG0.FROMDATE AS PENGAJARAN_FROMDATE, PG0.KITAB_ID, PG0.KITAB_KODE
                                            FROM (
                                                SELECT PJ.PARTYRELATIONSHIPTYPE_ID, PJ.FROMROLE_ID, PJ.TOROLE_ID, PJ.FROMDATE,
                                                       PJ.JENISKITAB_ID AS KITAB_ID, KT.KODE AS KITAB_KODE
                                                FROM MIABH_PENGAJARAN AS PJ
                                                JOIN MIABH_JENISKITAB AS KT
                                                ON PJ.JENISKITAB_ID = KT.ID
                                                WHERE PJ.FROMROLE_ID = ? AND PJ.FROMDATE <= ?
                                            ) AS PG0
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
                    ) AS PJ3
                    CROSS JOIN (
                        SELECT PB.FROMDATE AS D1,
                               PB.FROMDATE + 1 AS D2,
                               PB.FROMDATE + 2 AS D3,
                               PB.FROMDATE + 3 AS D4,
                               PB.FROMDATE + 4 AS D5,
                               PB.FROMDATE + 5 AS D6
                        FROM MIABH_PERIODEPEMBELAJARAN AS PB
                        WHERE PB.FLAG = 'PEKANAN'
                        AND PB.FROMDATE <= ? AND PB.THRUDATE >= ?
                    ) AS PB0
                ) AS PJ4
            ) AS PJ5
            LEFT JOIN (
                SELECT PS0.FROMROLE_ID, PS0.TOROLE_ID, PS0.FROMDATE, PR1.THRUDATE
                FROM MIABH_PENGASUHAN AS PS0
                JOIN PARTY_PARTYRELATIONSHIP AS PR1
                ON PS0.PARTYRELATIONSHIPTYPE_ID = PR1.PARTYRELATIONSHIPTYPE_ID
                AND PS0.FROMROLE_ID = PR1.FROMROLE_ID
                AND PS0.TOROLE_ID = PR1.TOROLE_ID
                AND PS0.FROMDATE = PR1.FROMDATE
                WHERE PR1.FROMDATE <=  ? AND (PR1.THRUDATE IS NULL OR PR1.THRUDATE >= ?)
            ) AS PS
            ON PS.TOROLE_ID = PJ5.SANTRI_ID 
            """;

    private static final String FIND
            = """
              SELECT PJ.FROMROLE_ID AS HALAQOH_ID, PJ.TOROLE_ID AS SANTRI_ID, PJ.FROMDATE AS PENGAJARAN_FROMDATE, PJ.JENISKITAB_ID AS KITAB_ID
              FROM MIABH_PENGAJARAN AS PJ
              WHERE PJ.FROMROLE_ID = ? AND PJ.TOROLE_ID = ? AND PJ.FROMDATE <= ?
            """;

    @Inject
    private EntityManager em;

    @Inject
    private PeriodePembelajaranFacade periodePembelajaranFacade;

    public RangkumanPengajaranFacade() {
        super(RangkumanPengajaran.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        HalaqohPengajaran halaqoh = (HalaqohPengajaran) parameters.get("hp");

        PeriodePembelajaran currPeriod = (PeriodePembelajaran) parameters.get("pp");

        if (currPeriod == null) {
            LocalDate now = LocalDate.now();
            currPeriod = periodePembelajaranFacade.getPeriodePembelajaranMin(
                    now, PEKANAN);
            if (currPeriod == null) {
                int dayOfWeek = now.getDayOfWeek().getValue();
                currPeriod = new PeriodePembelajaran();
                currPeriod.setFromDate(now.minusDays(dayOfWeek - 1));
                currPeriod.setThruDate(now.plusDays(7 - dayOfWeek));
            }

        }

        PeriodePembelajaran prevPeriod = periodePembelajaranFacade.getPeriodePembelajaranMin(
                currPeriod.getFromDate().minusDays(7), PEKANAN);

        if (prevPeriod == null) {
            prevPeriod = new PeriodePembelajaran();
            prevPeriod.setFromDate(
                    currPeriod.getFromDate().minus(currPeriod.getJenisPeriode().getDuration(),
                            currPeriod.getJenisPeriode().getUnit())
            );
            prevPeriod.setThruDate(
                    currPeriod.getThruDate().minus(currPeriod.getJenisPeriode().getDuration(), 
                            currPeriod.getJenisPeriode().getUnit()));
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.KEYFORMAT_LOCALDATE_DEFAULT);

        String sCurrPeriodFromDate = dateFormatter.format(currPeriod.getFromDate());
        String sCurrPeriodThruDate = dateFormatter.format(currPeriod.getThruDate());

        String sPrevPeriodFromDate = dateFormatter.format(prevPeriod.getFromDate());
        String sPrevPeriodThruDate = dateFormatter.format(prevPeriod.getThruDate());

        Long halaqohId = halaqoh != null ? halaqoh.getId() : -1;

//        q.setParameter(1, halaqohId)
//                .setParameter(2, sCurrPeriodFromDate)
//                .setParameter(3, halaqohId)
//                .setParameter(4, sCurrPeriodFromDate)
//                .setParameter(5, sCurrPeriodThruDate)
//                .setParameter(6, sPrevPeriodFromDate)
//                .setParameter(7, sPrevPeriodThruDate)
//                .setParameter(8, halaqohId)
//                .setParameter(9, sCurrPeriodFromDate)
//                .setParameter(10, sCurrPeriodFromDate)
//                .setParameter(11, sCurrPeriodFromDate)
//                .setParameter(12, sCurrPeriodThruDate);

        q.setParameter(1, halaqohId)
                .setParameter(2, currPeriod.getFromDate())
                .setParameter(3, halaqohId)
                .setParameter(4, currPeriod.getFromDate())
                .setParameter(5, currPeriod.getThruDate())
                .setParameter(6, prevPeriod.getFromDate())
                .setParameter(7, prevPeriod.getFromDate())
                .setParameter(8, halaqohId)
                .setParameter(9, currPeriod.getFromDate())
                .setParameter(10, currPeriod.getFromDate())
                .setParameter(11, currPeriod.getFromDate())
                .setParameter(12, currPeriod.getThruDate());
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "fromDate":
                return "PJ5.PENGAJARAN_FROMDATE";
            default:
                return super.translateOrderField(fieldName);
        }
    }

    @Inject
    private PengajaranFacade pengajaranFacade;

    @Override
    public Result<String> remove(RangkumanPengajaran entity) {
        return pengajaranFacade.remove(pengajaranFacade.find(entity.getId()));
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
    protected void setIdParameters(Query q, Map<String, Object> parameters) {
        PartyRelationshipId id = (PartyRelationshipId) parameters.get("id");

        q.setParameter(1, id.getFromRole())
                .setParameter(2, id.getToRole())
                .setParameter(3, id.getFromDate());
    }
}
