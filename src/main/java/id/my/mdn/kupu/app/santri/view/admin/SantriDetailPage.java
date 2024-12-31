/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.StatusSantriList;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.PersonDetailPage;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriDetailPage")
@ViewScoped
public class SantriDetailPage extends ChildPage implements Serializable {
    
    private static final Logger Log = Logger.getLogger(SantriDetailPage.class.getCanonicalName());
    
    @Bookmarked
    private Santri santri;
    
    @Inject
    @Bookmarked
    private PersonDetailPage partyDetailPage;

    @Inject
    @Bookmarked
    private StatusSantriList statusSantriList;
    
    @Inject
    private SantriFacade santriFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        partyDetailPage.init();
    }
    
    @Override
    public void load() {
        partyDetailPage.setParty((Person) santri.getParty());
        partyDetailPage.setContextSupplier(() -> this);
        partyDetailPage.setUpdateListener(person -> santriFacade.edit(santri));
        partyDetailPage.load();
        
        statusSantriList.getFilter().setStaticFilter(() -> List.of(new FilterData("santri", santri)));
        statusSantriList.getSelector().setSelectionsLabel("sts");
        statusSantriList.getPager().setPageSizeLabel("stp");
        statusSantriList.getPager().setOffsetLabel("sto");
    }

    @Creator(of = "statusSantriList")
    public void openStatusCreator() {
        gotoChild(StatusSantriEditorPage.class)
                .addParam("santri").withValues(santri)
                .open();
    }

    @Editor(of = "statusSantriList")
    public void openStatusEditor() {
        gotoChild(StatusSantriEditorPage.class)
                .addParam("santri").withValues(santri)
                .addParam("entity").withValues(statusSantriList.getSelector().getSelection())
                .open();
    }

    @Deleter(of = "statusSantriList")
    public void openStatusDeleter() {
        statusSantriList.delete(statusSantriList.getSelector().getSelections());
    }

    public PersonDetailPage getPartyDetailPage() {
        return partyDetailPage;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public StatusSantriList getStatusSantriList() {
        return statusSantriList;
    }

    public void setStatusSantriList(StatusSantriList statusSantriList) {
        this.statusSantriList = statusSantriList;
    }
    
}
