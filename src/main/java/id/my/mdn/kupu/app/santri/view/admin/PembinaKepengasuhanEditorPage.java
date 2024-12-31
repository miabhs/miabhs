/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PembinaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.UstadzFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PembinaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.app.santri.view.UstadzSingleChooserPage;
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
@Named(value = "pembinaKepengasuhanEditorPage")
@ConversationScoped
public class PembinaKepengasuhanEditorPage extends FormPage<PembinaKepengasuhan> {

    private static final int CHOOSE_USTADZ = 1;

    @Inject
    private PembinaKepengasuhanFacade dao;
    
    @Bookmarked(name = "toRole")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @Inject
    private UstadzFacade ustadzDao;

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
    protected PembinaKepengasuhan newEntity() {
        return PembinaKepengasuhan.builder()
                .to(kelompokPengasuhan) 
                .get();
    }

    @Override
    protected Result<String> save(PembinaKepengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PembinaKepengasuhan entity) {
        return dao.edit(entity);
    }

    public void openUstadzChooser() {
        gotoChild(UstadzSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_USTADZ)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_USTADZ:
                    entity.setUstadz((Ustadz) returns);
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
