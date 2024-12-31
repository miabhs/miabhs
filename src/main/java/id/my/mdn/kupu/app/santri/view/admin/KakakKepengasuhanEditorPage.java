/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KakakKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.MusyrifFacade;
import id.my.mdn.kupu.app.santri.entity.KakakKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.app.santri.view.MusyrifSingleChooserPage;
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
@Named(value = "kakakKepengasuhanEditorPage")
@ConversationScoped
public class KakakKepengasuhanEditorPage extends FormPage<KakakKepengasuhan> {

    private static final int CHOOSE_MUSYRIF = 1;

    @Inject
    private KakakKepengasuhanFacade dao;
    
    @Bookmarked(name = "toRole")
    private KelompokPengasuhan kelompokPengasuhan;
    
    @Inject
    private MusyrifFacade musyrifDao;

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
    protected KakakKepengasuhan newEntity() {

        return KakakKepengasuhan.builder()
                .to(kelompokPengasuhan) 
                .get();
    }

    @Override
    protected Result<String> save(KakakKepengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(KakakKepengasuhan entity) {
        return dao.edit(entity);
    }

    public void openMusyrifChooser() {
        gotoChild(MusyrifSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_MUSYRIF)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_MUSYRIF:
                    entity.setMusyrif((Musyrif) returns);
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
