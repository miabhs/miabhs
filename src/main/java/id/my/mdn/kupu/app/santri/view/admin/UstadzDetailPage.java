/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.UstadzFacade;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.PersonDetailPage;
import java.io.Serializable;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "ustadzDetailPage")
@ViewScoped
public class UstadzDetailPage extends ChildPage implements Serializable {
    
    private static final Logger Log = Logger.getLogger(UstadzDetailPage.class.getCanonicalName());
    
    @Bookmarked
    private Ustadz ustadz;
    
    @Inject
    @Bookmarked
    private PersonDetailPage partyDetailPage;
    
    @Inject
    private UstadzFacade ustadzFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        partyDetailPage.init();
    }
    
    @Override
    public void load() {
        partyDetailPage.setParty((Person) ustadz.getParty());
        partyDetailPage.setContextSupplier(() -> this);
        partyDetailPage.setUpdateListener(person -> ustadzFacade.edit(ustadz));
        partyDetailPage.load();
    }

    public PersonDetailPage getPartyDetailPage() {
        return partyDetailPage;
    }

    public Ustadz getUstadz() {
        return ustadz;
    }

    public void setUstadz(Ustadz ustadzu) {
        this.ustadz = ustadzu;
    }
    
}
