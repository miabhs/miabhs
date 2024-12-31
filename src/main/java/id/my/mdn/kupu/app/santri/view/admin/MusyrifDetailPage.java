/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.MusyrifFacade;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
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
@Named(value = "musyrifDetailPage")
@ViewScoped
public class MusyrifDetailPage extends ChildPage implements Serializable {
    
    private static final Logger Log = Logger.getLogger(MusyrifDetailPage.class.getCanonicalName());
    
    @Bookmarked
    private Musyrif musyrif;
    
    @Inject
    @Bookmarked
    private PersonDetailPage partyDetailPage;
    
    @Inject
    private MusyrifFacade musyrifFacade;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        partyDetailPage.init();
    }
    
    @Override
    public void load() {
        partyDetailPage.setParty((Person) musyrif.getParty());
        partyDetailPage.setContextSupplier(() -> this);
        partyDetailPage.setUpdateListener(person -> musyrifFacade.edit(musyrif));
        partyDetailPage.load();
    }

    public PersonDetailPage getPartyDetailPage() {
        return partyDetailPage;
    }

    public Musyrif getMusyrif() {
        return musyrif;
    }

    public void setMusyrif(Musyrif musyrif) {
        this.musyrif = musyrif;
    }
    
}
