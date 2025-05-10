/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PengampuHalaqohFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.PengampuHalaqoh;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.app.santri.view.SantriSingleChooserPage;
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
@Named(value = "pengampuHalaqohEditorPage")
@ConversationScoped
public class PengampuHalaqohEditorPage extends FormPage<PengampuHalaqoh> {

    private static final int CHOOSE_FROMROLE = 1;
    private static final int CHOOSE_TOROLE = 2;

    @Inject
    private PengampuHalaqohFacade dao;
    
    @Bookmarked(name = "fromRole")
    private Ustadz fromRole;
    
    @Bookmarked(name = "toRole")
    private HalaqohPengajaran toRole;

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
    protected PengampuHalaqoh newEntity() {
        return PengampuHalaqoh.builder()
                .from(fromRole)
                .to(toRole)
                .get();
    }

    @Override
    protected Result<String> save(PengampuHalaqoh entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PengampuHalaqoh entity) {
        return dao.edit(entity);
    }

    public void openFromRoleChooser() {
        gotoChild(UstadzSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_FROMROLE)
                .open();
    }

    public void openToRoleChooser() {
        gotoChild(SantriSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_TOROLE)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_FROMROLE:
                    entity.setFromRole((Ustadz) returns);
                    break;
                case CHOOSE_TOROLE:
                    entity.setToRole((HalaqohPengajaran) returns);
                    break;
                default:
                    break;
            }
        }
        return super.onReturns(what, returns);
    }

    public Ustadz getFromRole() {
        return fromRole;
    }

    public void setFromRole(Ustadz fromRole) {
        this.fromRole = fromRole;
    }

    public HalaqohPengajaran getToRole() {
        System.err.printf("SELEKTRI GET HALAKOH: %s", toRole);
        return toRole;
    }

    public void setToRole(HalaqohPengajaran toRole) {
        System.err.printf("SELEKTRI SET HALAKOH: %s", toRole);
        this.toRole = toRole;
    }

}
