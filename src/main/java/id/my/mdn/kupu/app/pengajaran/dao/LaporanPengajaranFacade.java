/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaranId;
import id.my.mdn.kupu.app.pengajaran.entity.LaporanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianBelajar;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
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
public class LaporanPengajaranFacade {

    private static String SQL
            = """                           
            SELECT PB9.JENISPENGAJARANID, JP.KODE AS JENISPENGAJARANKODE, JP.NAMA AS JENISPENGAJARANNAMA,
                   PB9.JENISKITABID, PB9.JENISKITABJUDUL, PB9.CAPAIAN
            FROM (
                SELECT JK0.JENISPENGAJARAN_ID AS JENISPENGAJARANID,
                    JK0.ID AS JENISKITABID, JK0.JUDUL AS JENISKITABJUDUL, PB8.CAPAIAN
                FROM MIABH_JENISKITAB AS JK0
                LEFT JOIN (
                    SELECT PB6.KITAB_ID, PB6.PENGAJARANDATE, PB6.BEGINAT, PB7.SETORAN, PB7.CAPAIAN
                    FROM (
                        SELECT PB4.KITAB_ID, PB4.PENGAJARANDATE, MAX(PB5.BEGINAT) AS BEGINAT
                        FROM (
                            SELECT PB3.KITAB_ID, MAX(PB3.PENGAJARANDATE) AS PENGAJARANDATE
                            FROM (
                                SELECT (CASE WHEN JK0.PARENT_ID IS NOT NULL THEN JK0.PARENT_ID ELSE JK0.ID END) AS KITAB_ID, PB2.PENGAJARANDATE
                                FROM (
                                    SELECT PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT
                                    FROM (
                                        SELECT PB0.ID, PB0.JENISKITAB_ID, PB0.PENGAJARANDATE
                                        FROM MIABH_PENCAPAIANBELAJAR AS PB0
                                        WHERE PB0.SANTRI_ID = ?
                                        AND PB0.PENGAJARANDATE >= ? AND PB0.PENGAJARANDATE <= ?
                                    ) AS PB1
                                    JOIN (
                                        SELECT HQ1.BEGINAT, SS0.PENCAPAIAN_ID
                                        FROM MIABH_SETORANSANTRI AS SS0
                                        JOIN (
                                            SELECT HQ0.ID, KH0.BEGINAT
                                            FROM MIABH_HALAQOHPENGAJARAN AS HQ0
                                            JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KH0
                                            ON HQ0.KELOMPOK_ID = KH0.ID
                                        ) AS HQ1
                                        ON SS0.PENGAJARAN_FROMROLE_ID = HQ1.ID
                                    ) AS SS1
                                    ON PB1.ID = SS1.PENCAPAIAN_ID
                                    GROUP BY PB1.ID, PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT, PB1.SETORAN
                                ) AS PB2
                                JOIN MIABH_JENISKITAB AS JK0
                                ON PB2.JENISKITAB_ID = JK0.ID
                            ) AS PB3
                            GROUP BY PB3.KITAB_ID
                        ) AS PB4
                        JOIN (
                            SELECT (CASE WHEN JK0.PARENT_ID IS NOT NULL THEN JK0.PARENT_ID ELSE JK0.ID END) AS KITAB_ID, PB2.PENGAJARANDATE, PB2.BEGINAT
                            FROM (
                                SELECT PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT
                                FROM (
                                    SELECT PB0.ID, PB0.JENISKITAB_ID, PB0.PENGAJARANDATE
                                    FROM MIABH_PENCAPAIANBELAJAR AS PB0
                                    JOIN MIABH_ATRIBUTSETORANPEMBELAJARAN AS AS0
                                    ON PB0.ID = AS0.PENCAPAIANBELAJAR_ID
                                    WHERE PB0.SANTRI_ID = ?
                                    AND PB0.PENGAJARANDATE >= ? AND PB0.PENGAJARANDATE <= ?
                                ) AS PB1
                                JOIN (
                                    SELECT HQ1.BEGINAT, SS0.PENCAPAIAN_ID
                                    FROM MIABH_SETORANSANTRI AS SS0
                                    JOIN (
                                        SELECT HQ0.ID, KH0.BEGINAT
                                        FROM MIABH_HALAQOHPENGAJARAN AS HQ0
                                        JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KH0
                                        ON HQ0.KELOMPOK_ID = KH0.ID
                                    ) AS HQ1
                                    ON SS0.PENGAJARAN_FROMROLE_ID = HQ1.ID
                                ) AS SS1
                                ON PB1.ID = SS1.PENCAPAIAN_ID
                                GROUP BY PB1.ID, PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT, PB1.SETORAN
                            ) AS PB2
                            JOIN MIABH_JENISKITAB AS JK0
                            ON PB2.JENISKITAB_ID = JK0.ID
                        ) AS PB5
                        ON PB4.KITAB_ID = PB5.KITAB_ID AND PB4.PENGAJARANDATE = PB5.PENGAJARANDATE
                        GROUP BY PB4.KITAB_ID, PB4.PENGAJARANDATE
                    ) AS PB6
                    JOIN (
                        SELECT (CASE WHEN JK0.PARENT_ID IS NOT NULL THEN JK0.PARENT_ID ELSE JK0.ID END) AS KITAB_ID, 
                               PB2.PENGAJARANDATE, PB2.BEGINAT, PB2.SETORAN, 
                               (CASE WHEN JK0.PARENT_ID IS NOT NULL THEN CONCAT_WS(' - ', JK0.SUBJUDUL, PB2.CAPAIAN) ELSE PB2.CAPAIAN END) AS CAPAIAN
                        FROM (
                            SELECT PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT, PB1.SETORAN, STRING_AGG(PB1.CAPAIAN, ': ') AS CAPAIAN
                            FROM (
                                SELECT PB0.ID, PB0.JENISKITAB_ID, PB0.PENGAJARANDATE, PB0.SETORAN, CONCAT_WS('.', AS0.NAME || ' ' || AS0.MARKER, AS0.DESKRIPSI) AS CAPAIAN
                                FROM MIABH_PENCAPAIANBELAJAR AS PB0
                                JOIN MIABH_ATRIBUTSETORANPEMBELAJARAN AS AS0
                                ON PB0.ID = AS0.PENCAPAIANBELAJAR_ID
                                WHERE PB0.SANTRI_ID = ?
                                AND PB0.PENGAJARANDATE >= ? AND PB0.PENGAJARANDATE <= ?
                            ) AS PB1
                            JOIN (
                                SELECT HQ1.BEGINAT, SS0.PENCAPAIAN_ID
                                FROM MIABH_SETORANSANTRI AS SS0
                                JOIN (
                                    SELECT HQ0.ID, KH0.BEGINAT
                                    FROM MIABH_HALAQOHPENGAJARAN AS HQ0
                                    JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KH0
                                    ON HQ0.KELOMPOK_ID = KH0.ID
                                ) AS HQ1
                                ON SS0.PENGAJARAN_FROMROLE_ID = HQ1.ID
                            ) AS SS1
                            ON PB1.ID = SS1.PENCAPAIAN_ID
                            GROUP BY PB1.ID, PB1.JENISKITAB_ID, PB1.PENGAJARANDATE, SS1.BEGINAT, PB1.SETORAN
                        ) AS PB2
                        JOIN MIABH_JENISKITAB AS JK0
                        ON PB2.JENISKITAB_ID = JK0.ID
                    ) AS PB7
                    ON PB6.KITAB_ID = PB7.KITAB_ID AND PB6.PENGAJARANDATE = PB7.PENGAJARANDATE AND PB6.BEGINAT = PB7.BEGINAT
                ) AS PB8
                ON JK0.ID = PB8.KITAB_ID
                WHERE JK0.PARENT_ID IS NULL
            ) AS PB9
            JOIN MIABH_JENISPENGAJARAN AS JP
            ON PB9.JENISPENGAJARANID = JP.ID
            """;

    @Inject
    private EntityManager em;

    @Inject
    private CatatanPengajaranFacade catatanDao;

    public LaporanPengajaran getLaporan(Santri santri, PeriodePembelajaran periode) {
        List<PencapaianBelajar> listPencapaianBelajar = getPencapaianBelajar(santri, periode);
        CatatanPengajaran catatanPengajaran = getCatatanPengajaran(santri, periode);
        return new LaporanPengajaran(santri, periode, listPencapaianBelajar, catatanPengajaran);
    }

    public List<PencapaianBelajar> getPencapaianBelajar(Santri santri, PeriodePembelajaran periode) {
        Query q = em.createNativeQuery(SQL, "PencapaianBelajar");
        q.setParameter(1, santri.getId());
        q.setParameter(2, periode.getFromDate());
        q.setParameter(3, periode.getThruDate());
        q.setParameter(4, santri.getId());
        q.setParameter(5, periode.getFromDate());
        q.setParameter(6, periode.getThruDate());
        q.setParameter(7, santri.getId());
        q.setParameter(8, periode.getFromDate());
        q.setParameter(9, periode.getThruDate());

        return q.getResultList();
    }

    public CatatanPengajaran getCatatanPengajaran(Santri santri, PeriodePembelajaran periode) {
        return catatanDao.find(
                new CatatanPengajaranId(santri.getId(), periode.getId())
        );
    }
}
