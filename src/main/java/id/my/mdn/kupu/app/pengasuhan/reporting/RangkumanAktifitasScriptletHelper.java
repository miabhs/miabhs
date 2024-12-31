/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.CatatanKepengasuhanFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "rangkumanAktifitasScriptletHelper")
@Singleton
public class RangkumanAktifitasScriptletHelper {

    @Inject
    private CatatanKepengasuhanFacade catatanLaporanFacade;

    public CatatanKepengasuhanFacade getCatatanLaporanFacade() {
        return catatanLaporanFacade;
    }
    
}
