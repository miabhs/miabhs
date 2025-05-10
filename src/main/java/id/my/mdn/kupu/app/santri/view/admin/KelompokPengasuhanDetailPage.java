/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanSelectList;
import id.my.mdn.kupu.app.santri.view.widget.PelaksanaKepengasuhanList;
import id.my.mdn.kupu.app.santri.view.widget.PengasuhanList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.OrganizationDetailPage;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "kelompokPengasuhanDetailPage")
@ViewScoped
public class KelompokPengasuhanDetailPage extends ChildPage implements Serializable {

    @Bookmarked(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;

    @Inject
    private OrganizationDetailPage partyDetailPage;

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;

    @Bookmarked
    @Inject
    private PelaksanaKepengasuhanList pelaksanaKepengasuhanList;
    
    @Bookmarked
    @Inject
    private PengasuhanList pengasuhanList;
    
    @Inject
    private KelompokPengasuhanSelectList kelompokPengasuhanList;

    @PostConstruct
    @Override
    public void init() {
        super.init();
        partyDetailPage.init();
        
    }
    
    @Override
    public void load() {
        partyDetailPage.setParty((Organization) kelompokPengasuhan.getParty());
        partyDetailPage.setContextSupplier(() -> this);
        partyDetailPage.setUpdateListener(party -> kelompokPengasuhanFacade.edit(kelompokPengasuhan));
        partyDetailPage.load();

        pelaksanaKepengasuhanList.setName("pembantuPelaksanaKepengasuhanTbl");
        pelaksanaKepengasuhanList.getFilter().setStaticFilter(this::filterToRole);
        pelaksanaKepengasuhanList.getSelector().setSelectionsLabel("bks");
        pelaksanaKepengasuhanList.getPager().setPageSizeLabel("bkp");
        pelaksanaKepengasuhanList.getPager().setOffsetLabel("bko");

        pengasuhanList.setName("pengasuhanTbl");
        pengasuhanList.getFilter().setStaticFilter(this::filterFromRole);
        pengasuhanList.getSelector().setSelectionsLabel("ps");
        pengasuhanList.getPager().setPageSizeLabel("pp");
        pengasuhanList.getPager().setOffsetLabel("po");
    }

    @Creator(of = "pelaksanaKepengasuhanList")
    public void openPelaksanaKepengasuhanEditor() {
        gotoChild(PelaksanaKepengasuhanEditorPage.class)
                .addParam("toRole")
                .withValues(kelompokPengasuhan)
                .open();
    }

    @Deleter(of = "pelaksanaKepengasuhanList")
    public void openPelaksanaKepengasuhanDeleter() {
        pelaksanaKepengasuhanList.delete(pelaksanaKepengasuhanList.getSelector().getSelections());
    }

    @Creator(of = "pengasuhanList")
    public void openPengasuhanEditor() {
        gotoChild(PengasuhanEditorPage.class)
                .addParam("fromRole")
                .withValues(kelompokPengasuhan)
                .open();
    }

    @Deleter(of = "pengasuhanList")
    public void openPengasuhanDeleter() {
        pengasuhanList.delete(pengasuhanList.getSelector().getSelections());
    }

    private List<FilterData> filterToRole() {
        List<FilterData> filter = new ArrayList<>();
        filter.add(new FilterData("toRole", kelompokPengasuhan));

        return filter;
    }

    private List<FilterData> filterFromRole() {
        List<FilterData> filter = new ArrayList<>();
        filter.add(new FilterData("fromRole", kelompokPengasuhan));

        return filter;
    }

    public OrganizationDetailPage getPartyDetailPage() {
        return partyDetailPage;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public PelaksanaKepengasuhanList getPelaksanaKepengasuhanList() {
        return pelaksanaKepengasuhanList;
    }

    public PengasuhanList getPengasuhanList() {
        return pengasuhanList;
    }
}
