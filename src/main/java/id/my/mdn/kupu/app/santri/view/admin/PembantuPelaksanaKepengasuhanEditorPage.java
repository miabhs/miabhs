/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PembantuPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.UstadzFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PembantuPelaksanaKepengasuhan;
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
@Named(value = "pembantuPelaksanaKepengasuhanEditorPage")
@ConversationScoped
public class PembantuPelaksanaKepengasuhanEditorPage extends FormPage<PembantuPelaksanaKepengasuhan> {

    private static final int CHOOSE_USTADZ = 1;

    @Inject
    private PembantuPelaksanaKepengasuhanFacade dao;
    
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
    protected PembantuPelaksanaKepengasuhan newEntity() {

        return PembantuPelaksanaKepengasuhan.builder()
                .to(kelompokPengasuhan) 
                .get();
    }

    @Override
    protected Result<String> save(PembantuPelaksanaKepengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PembantuPelaksanaKepengasuhan entity) {
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
                    Ustadz ustadz = ustadzDao.find(Long.valueOf(returns.toString()));
                    entity.setUstadz(ustadz);
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
