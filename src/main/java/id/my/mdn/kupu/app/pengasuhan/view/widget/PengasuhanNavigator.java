/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.view.BentukAktifitasPage;
import id.my.mdn.kupu.app.pengasuhan.view.HikmahKauniyahPage;
import id.my.mdn.kupu.app.pengasuhan.view.LaporanKelompokPage;
import id.my.mdn.kupu.app.pengasuhan.view.LaporanKelompokPekananPage;
import id.my.mdn.kupu.app.pengasuhan.view.LaporanKepengasuhanEditorPage;
import id.my.mdn.kupu.app.pengasuhan.view.LaporanKepengasuhanPage;
import id.my.mdn.kupu.app.pengasuhan.view.LaporanSantriPage;
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
public class PengasuhanNavigator extends PageNavigator implements Serializable {
    
    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        switch(pageId) {
            case "HikmahKauniyah":
                return HikmahKauniyahPage.class;
            case "LaporanKelompok":
                return LaporanKelompokPage.class;
            case "LaporanKelompokPekanan":
                return LaporanKelompokPekananPage.class;
            case "LaporanSantri":
                return LaporanSantriPage.class;
            case "LaporanKepengasuhan":
                return LaporanKepengasuhanPage.class;
            case "LaporanKepengasuhanEditor":
                return LaporanKepengasuhanEditorPage.class;
            case "BentukAktifitas":
                return BentukAktifitasPage.class;
            default:
                return null;
        }
    }

    @Override
    protected String getHome() {
        return "/app/pengasuhan/index.xhtml";
    }

}
