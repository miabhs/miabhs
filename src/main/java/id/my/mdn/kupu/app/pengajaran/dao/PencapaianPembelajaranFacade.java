/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaran;
import id.my.mdn.kupu.app.pengajaran.entity.CatatanPengajaranId;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianPembelajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class PencapaianPembelajaranFacade extends AbstractSqlFacade<PencapaianPembelajaran> {

    private static final String FIND_ALL
            = """
                SELECT FM1.JENISKITAB_ID, FM1.JENISKITAB_PARENT_ID, FM1.JENISKITAB_JUDUL, FM1.JENISPENGAJARAN_ID, FM1.JENISPENGAJARAN_NAMA, PC1.PENCAPAIAN
                FROM (
                    SELECT JK3.ID AS JENISKITAB_ID, JK3.PARENT_ID AS JENISKITAB_PARENT_ID, JK3.JENISPENGAJARAN_ID, JP.NAMA AS JENISPENGAJARAN_NAMA, JUDUL AS JENISKITAB_JUDUL
                    FROM (
                        SELECT FORMAT('%s%s%s', JK2.JENISPENGAJARAN_ID, JK2.PARENT_ID, JK2.ID) AS COMPOSITE_ID, 
                               JK2.JENISPENGAJARAN_ID, JK2.PARENT_ID, JK2.ID, 
                               (CASE WHEN (JK2.PARENT_ID IS NULL OR JK1.DIRINCIKAN IS FALSE) THEN JK2.JUDUL ELSE JK2.SUBJUDUL END) AS JUDUL
                        FROM (
                            SELECT JK.ID, JK.BERJILID, JK.DIRINCIKAN
                            FROM MIABH_JENISKITAB AS JK
                            WHERE JK.PARENT_ID IS NULL
                        ) AS JK1,
                        LATERAL (
                            SELECT JK.JENISPENGAJARAN_ID, JK.PARENT_ID, JK.ID, JK.JUDUL, JK.SUBJUDUL
                            FROM MIABH_JENISKITAB AS JK
                            WHERE JK.ID = JK1.ID OR JK.PARENT_ID = JK1.ID
                        ) AS JK2
                        WHERE (JK2.PARENT_ID IS NULL) OR (JK1.BERJILID IS FALSE AND JK1.DIRINCIKAN IS FALSE) OR (JK1.BERJILID IS TRUE AND JK1.DIRINCIKAN IS TRUE)
                        ORDER BY COMPOSITE_ID
                    ) AS JK3
                    JOIN MIABH_JENISPENGAJARAN AS JP
                    ON JK3.JENISPENGAJARAN_ID = JP.ID
                ) AS FM1
                LEFT JOIN (
                    SELECT JENISKITAB_ID, PENCAPAIAN
                    FROM (
                        SELECT PB1.JENISKITAB_ID, STRING_AGG(PB1.PENCAPAIAN, ':') AS PENCAPAIAN
                        FROM (    
                            SELECT (CASE WHEN (JK1.PARENT_ID IS NOT NULL AND JK2.DIRINCIKAN IS FALSE) THEN JK1.PARENT_ID ELSE SS3.JENISKITAB_ID END) AS JENISKITAB_ID,
                                   SS3.PENGAJARANDATE, SS3.PENCAPAIAN_ID, ARRAY_TO_STRING(ARRAY[FORMAT('%s %s', ATS.NAME, ATS.MARKER), ATS.DESKRIPSI::TEXT], ' ') AS PENCAPAIAN 
                            FROM (
                                SELECT SS2.JENISKITAB_ID, SS2.PENGAJARANDATE, SS2.PENCAPAIAN_ID
                                FROM (
                                    SELECT SS1.JENISKITAB_ID, SS1.PENGAJARANDATE, SS1.PENGAJARANDATETIME, 
                                           MAX(PENGAJARANDATETIME) OVER (PARTITION BY SS1.SANTRI_ID, SS1.JENISKITAB_ID) AS PENGAJARANDATETIME_LATEST, 
                                           SS1.PENCAPAIAN_ID
                                    FROM (
                                        SELECT SS.PENGAJARAN_TOROLE_ID AS SANTRI_ID, PJ.JENISKITAB_ID, SS.PENGAJARANDATE,
                                               MAKE_TIMESTAMP (
                                                    CAST(EXTRACT('YEAR' FROM SS.PENGAJARANDATE) AS INTEGER), 
                                                    CAST(EXTRACT('MONTH' FROM SS.PENGAJARANDATE) AS INTEGER), 
                                                    CAST(EXTRACT('DAY' FROM SS.PENGAJARANDATE) AS INTEGER), 
                                                    CAST(EXTRACT('HOUR' FROM KHQ.BEGINAT) AS INTEGER), 
                                                    CAST(EXTRACT('MINUTE' FROM KHQ.BEGINAT) AS INTEGER), 
                                                    CAST(EXTRACT('SECOND' FROM KHQ.BEGINAT) AS INTEGER)
                                               ) AS PENGAJARANDATETIME,
                                               SS.PENCAPAIAN_ID
                                        FROM (
                                            SELECT *
                                            FROM MIABH_SETORANSANTRI AS SSX
                                            WHERE SSX.PENGAJARAN_TOROLE_ID = ?
                                        ) AS SS
                                        JOIN MIABH_PENGAJARAN AS PJ ON SS.PENGAJARAN_FROMROLE_ID = PJ.FROMROLE_ID 
                                                                    AND SS.PENGAJARAN_TOROLE_ID = PJ.TOROLE_ID 
                                                                    AND SS.PENGAJARAN_FROMDATE = PJ.FROMDATE
                                                                    AND SS.PENGAJARANDATE <= ?
                                        JOIN MIABH_HALAQOHPENGAJARAN AS HQ ON SS.PENGAJARAN_FROMROLE_ID = HQ.ID
                                        JOIN MIABH_KELOMPOKHALAQOHPENGAJARAN AS KHQ ON HQ.KELOMPOK_ID = KHQ.ID
                                    ) AS SS1
                                ) AS SS2
                                WHERE SS2.PENGAJARANDATETIME = SS2.PENGAJARANDATETIME_LATEST
                            ) AS SS3
                            JOIN MIABH_ATRIBUTSETORANPEMBELAJARAN AS ATS ON SS3.PENCAPAIAN_ID = ATS.PENCAPAIANBELAJAR_ID
                            JOIN MIABH_JENISKITAB AS JK1 ON SS3.JENISKITAB_ID = JK1.ID
                            LEFT JOIN MIABH_JENISKITAB AS JK2 ON JK1.PARENT_ID = JK2.ID
                        ) AS PB1
                        GROUP BY PB1.JENISKITAB_ID
                    ) AS PB2
                ) AS PC1
                ON FM1.JENISKITAB_ID = PC1.JENISKITAB_ID
              """;

    @Inject
    private EntityManager em;

    @Inject
    private CatatanPengajaranFacade catatanDao;

    public PencapaianPembelajaranFacade() {
        super(PencapaianPembelajaran.class);
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        q.setParameter(1, parameters.get("santriId"));
        q.setParameter(2, parameters.get("thruDate"));
    }
    
    public List<PencapaianPembelajaran> getPencapaianPembelajaran(Santri santri, PeriodePembelajaran periode) {
        return getPencapaianPembelajaran(santri.getId(), periode.getThruDate());
    }
    
    public List<PencapaianPembelajaran> getPencapaianPembelajaran(Long santriId, LocalDate thruDate) {
        return findAll(Map.of("santriId", santriId, "thruDate", thruDate));
    }

    public CatatanPengajaran getCatatanPembelajaran(Santri santri, PeriodePembelajaran periode) {
        return catatanDao.find(
                new CatatanPengajaranId(santri.getId(), periode.getId())
        );
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
