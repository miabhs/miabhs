/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.CatatanKepengasuhanFacade;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.service.PeriodePembelajaranService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanPengasuhanScriptletHelper")
@Singleton
public class LaporanPengasuhanScriptletHelper {

    @Inject
    private PeriodePembelajaranService periodePembelajaranService;
    
    @Inject
    private CatatanKepengasuhanFacade catatanLaporanFacade;
    
    @Inject
    private AktifitasFacade aktifitasFacade;
    
    @Inject
    private PengasuhanService pengasuhanService;

    public PeriodePembelajaranService getPeriodePembelajaranService() {
        return periodePembelajaranService;
    }

    public CatatanKepengasuhanFacade getCatatanLaporanFacade() {
        return catatanLaporanFacade;
    }

    public PengasuhanService getPengasuhanService() {
        return pengasuhanService;
    }

    public AktifitasFacade getAktifitasFacade() {
        return aktifitasFacade;
    }
    
}
