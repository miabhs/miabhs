/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PelaksanaKepengasuhanFacade;
import static id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan.KAKAK_KEPENGASUHAN;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PelaksanaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.app.santri.view.MusyrifSingleChooserPage;
import id.my.mdn.kupu.app.santri.view.UstadzSingleChooserPage;
import id.my.mdn.kupu.core.base.util.RequestedView;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.party.entity.PartyRole;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pelaksanaKepengasuhanEditorPage")
@ConversationScoped
public class PelaksanaKepengasuhanEditorPage extends FormPage<PelaksanaKepengasuhan> {

    private static final int CHOOSE_FROMROLE = 1;
    private static final int CHOOSE_TOROLE = 2;

    @Inject
    private PelaksanaKepengasuhanFacade dao;

    @Bookmarked(name = "fromRole")
    private Ustadz fromRole;

    @Bookmarked(name = "toRole")
    private KelompokPengasuhan toRole;

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
    protected PelaksanaKepengasuhan newEntity() {

        return PelaksanaKepengasuhan.builder()
                .from(fromRole)
                .to(toRole)
                .get();
    }

    @Override
    protected Result<String> save(PelaksanaKepengasuhan entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PelaksanaKepengasuhan entity) {
        return dao.edit(entity);
    }

    public void openFromRoleChooser() {
        RequestedView gotoChild = entity.getFungsional() != KAKAK_KEPENGASUHAN ? 
                gotoChild(UstadzSingleChooserPage.class)
                : gotoChild(MusyrifSingleChooserPage.class);
        gotoChild.addParam("what")
                .withValues(CHOOSE_FROMROLE)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_FROMROLE:
                    entity.setFromRole((PartyRole) returns);
                    break;
                case CHOOSE_TOROLE:
                    entity.setToRole((KelompokPengasuhan) returns);
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

    public KelompokPengasuhan getToRole() {
        return toRole;
    }

    public void setToRole(KelompokPengasuhan toRole) {
        this.toRole = toRole;
    }

}
