/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.SantriSingleChooserPage;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pengasuhanEditorPage")
@ConversationScoped
public class PengasuhanEditorPage extends FormPage<Pengasuhan> {

    private static final int CHOOSE_SANTRI = 1;

    @Inject
    private PengasuhanFacade dao;
    
    @Bookmarked(name = "fromRole")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void load() {
        super.load();
        updateAddressBar();
    }

    @Override
    protected Pengasuhan newEntity() {

        return Pengasuhan.builder()
                .from(kelompokPengasuhan) 
                .get();
    }

    @Override
    protected Result<String> save(Pengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Pengasuhan entity) {
        return dao.edit(entity);
    }

    public void openSantriChooser() {
        gotoChild(SantriSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_SANTRI)
                .addParam("st")
                .withValues(StatusKesantrian.ACTIVE)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_SANTRI: 
                    entity.setSantri((Santri) returns);
                    break;
                default:
                    break;
            }
        }
        return super.onReturns(what, returns);
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

}
