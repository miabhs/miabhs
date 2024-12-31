/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.MusyrifFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.SantriSingleChooserPage;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "musyrifEditorAltPage")
@ConversationScoped
public class MusyrifEditorAltPage extends FormPage<Musyrif> {

    private static final int CHOOSE_SANTRI = 1;
    
    @Inject
    private MusyrifFacade dao;

//    @Inject
//    private PartyFacade partyDao;

    @Inject
    private SantriFacade santriDao;

    @Bookmarked
    private Santri santri;

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
    protected Musyrif newEntity() {
        return Musyrif.builder()
                .get();
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    @Override
    protected Result<String> save(Musyrif entity) {
        Person person = santri.getPerson();
        entity.setPerson(person);
        return dao.create(entity);
//        person.getRoles().add(entity);
//        partyDao.edit(person);
    }

    @Override
    protected Result<String> edit(Musyrif entity) {
//        Person person = santri.getPerson();
//        partyDao.edit(person);
        return dao.edit(entity);
    }

    public void openSantriChooser() {
        gotoChild(SantriSingleChooserPage.class)
                .addParam("what")
                .withValues(CHOOSE_SANTRI)
                .addParam("sm")
                .withValues(Selector.SINGLE)
                .addParam("sk")
                .withValues(StatusKesantrian.GRADUATED)
                .open();
    }

    @Override
    public Page onReturns(int what, Object returns) {
        if (returns != null) {
            switch (what) {
                case CHOOSE_SANTRI:
                    santri = santriDao.find(Long.valueOf(returns.toString()));
                    break;
                default:
                    break;
            }
        }
        return super.onReturns(what, returns);
    }

}
