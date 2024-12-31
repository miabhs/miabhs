/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.view.HalaqohPengajaranPage;
import id.my.mdn.kupu.app.santri.view.JenisKitabPage;
import id.my.mdn.kupu.app.santri.view.JenisPengajaranPage;
import id.my.mdn.kupu.app.santri.view.KategoriKitabPage;
import id.my.mdn.kupu.app.santri.view.KelompokPengasuhanPage;
import id.my.mdn.kupu.app.santri.view.MusyrifPage;
import id.my.mdn.kupu.app.santri.view.TahunPembelajaranPage;
import id.my.mdn.kupu.app.santri.view.UstadzPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.widget.PageNavigator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named
@ApplicationScoped
public class SantriNavigator extends PageNavigator implements Serializable {
    
    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        switch(pageId) {            
            case "TahunPembelajaran":
                return TahunPembelajaranPage.class;
            case "KelompokPengasuhan":
                return KelompokPengasuhanPage.class;
            case "HalaqohPengajaran":
                return HalaqohPengajaranPage.class;
            case "JenisKitab":
                return JenisKitabPage.class;
            case "KategoriKitab":
                return KategoriKitabPage.class;
            case "JenisPengajaran":
                return JenisPengajaranPage.class;
            case "Musyrif":
                return MusyrifPage.class;
            case "Ustadz":
                return UstadzPage.class;
            default:
                return null;
        }
    }

    @Override
    protected String getHome() {
        return "/app/santri/index.xhtml";
    }

}
