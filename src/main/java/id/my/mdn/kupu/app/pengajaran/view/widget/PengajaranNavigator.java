/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.widget;

import id.my.mdn.kupu.app.pengajaran.view.LaporanPengajaranEditorPage;
import id.my.mdn.kupu.app.pengajaran.view.LaporanPengajaranPage;
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
public class PengajaranNavigator extends PageNavigator implements Serializable {
    
    @Override
    protected Class<? extends Page> pageMap(String pageId) {
        return switch (pageId) {
            case "LaporanPengajaran" -> LaporanPengajaranPage.class;
            case "LaporanPengajaranEditor" -> LaporanPengajaranEditorPage.class;
            default -> null;
        };
    }

    @Override
    protected String getHome() {
        return "/app/pengajaran/index.xhtml";
    }

}
