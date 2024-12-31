/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "aktifitasSantriScriptletHelper")
@Singleton
public class AktifitasSantriScriptletHelper {

    @Inject
    private AktifitasFacade aktifitasFacade;

    public AktifitasFacade getAktifitasFacade() {
        return aktifitasFacade;
    }
    
}
