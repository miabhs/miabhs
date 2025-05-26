/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.reporting;

import id.my.mdn.kupu.app.pengajaran.dao.PencapaianPembelajaranFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanPengajaranScriptletHelper")
@Singleton
public class LaporanPengajaranScriptletHelper {
    
    @Inject
    private PencapaianPembelajaranFacade laporanPembelajaranFacade;
    
    @Inject
    private SantriFacade santriFacade;

    public PencapaianPembelajaranFacade getLaporanPembelajaranFacade() {
        return laporanPembelajaranFacade;
    }

    public SantriFacade getSantriFacade() {
        return santriFacade;
    }
    
}
