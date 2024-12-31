/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "raporKepengasuhanScriptletHelper")
@Singleton
public class RaporKepengasuhanScriptletHelper {
    
    @Inject
    private AktifitasFacade aktifitasFacade;
    
    @Inject
    private SantriFacade santriFacade;

    public AktifitasFacade getAktifitasFacade() {
        return aktifitasFacade;
    }

    public SantriFacade getSantriFacade() {
        return santriFacade;
    }
    
}
