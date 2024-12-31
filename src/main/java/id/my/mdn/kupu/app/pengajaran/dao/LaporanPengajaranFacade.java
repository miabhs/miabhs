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

    private static String SQL = """                           
        SELECT JK1.JENISPENGAJARANID, JK1.JENISPENGAJARANKODE, JK1.JENISPENGAJARANNAMA, 
               JK1.JENISKITABID, JK1.JENISKITABJUDUL, PS2.NOTES
        FROM (SELECT JP0.ID AS JENISPENGAJARANID, JP0.KODE AS JENISPENGAJARANKODE,
                     JP0.NAMA AS JENISPENGAJARANNAMA, JK0.ID AS JENISKITABID, 
                     JK0.JUDUL AS JENISKITABJUDUL
              FROM MIABH_JENISPENGAJARAN AS JP0
              JOIN MIABH_JENISKITAB AS JK0
              ON JP0.ID = JK0.JENISPENGAJARAN_ID
        ) AS JK1
        LEFT JOIN (SELECT PS0.PENGAJARANDATE, PS0.SANTRI_ID, PS0.JENISKITAB_ID, PS1.NOTES
                   FROM (SELECT MAX(PS.PENGAJARANDATE) AS PENGAJARANDATE, PS.SANTRI_ID, PS.JENISKITAB_ID
                         FROM MIABH_PENGAJARANSANTRI AS PS
                         WHERE PS.PENGAJARANDATE >= ? AND PS.PENGAJARANDATE <= ?
                         GROUP BY PS.SANTRI_ID, PS.JENISKITAB_ID) AS PS0
                   JOIN MIABH_PENGAJARANSANTRI AS PS1
                   ON PS0.PENGAJARANDATE = PS1.PENGAJARANDATE AND PS0.SANTRI_ID = PS1.SANTRI_ID AND PS0.JENISKITAB_ID = PS1.JENISKITAB_ID
                   WHERE PS0.SANTRI_ID = ?
        ) AS PS2
        ON JK1.JENISKITABID = PS2.JENISKITAB_ID
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
        q.setParameter(1, periode.getFromDate());
        q.setParameter(2, periode.getThruDate());
        q.setParameter(3, santri.getId());

        return q.getResultList();
    }

    public CatatanPengajaran getCatatanPengajaran(Santri santri, PeriodePembelajaran periode) {
        return catatanDao.find(
                new CatatanPengajaranId(santri.getId(), periode.getId())
        );
    }
}
