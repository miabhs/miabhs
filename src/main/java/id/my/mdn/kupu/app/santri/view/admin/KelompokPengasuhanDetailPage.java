/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.view.widget.KakakKepengasuhanList;
import id.my.mdn.kupu.app.santri.view.widget.PembantuPelaksanaKepengasuhanList;
import id.my.mdn.kupu.app.santri.view.widget.PembinaKepengasuhanList;
import id.my.mdn.kupu.app.santri.view.widget.PengasuhanList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.ChildPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.view.OrganizationDetailPage;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private PembinaKepengasuhanList pembinaKepengasuhanList;

    @Bookmarked
    @Inject
    private KakakKepengasuhanList kakakKepengasuhanList;

    @Bookmarked
    @Inject
    private PembantuPelaksanaKepengasuhanList pembantuPelaksanaKepengasuhanList;
    
    @Bookmarked
    @Inject
    private PengasuhanList pengasuhanList;

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

        pembinaKepengasuhanList.setName("pembinaKepengasuhanTbl");
        pembinaKepengasuhanList.getFilter().setStaticFilter(this::filterToRole);
        pembinaKepengasuhanList.getSelector().setSelectionsLabel("pks");
        pembinaKepengasuhanList.getPager().setPageSizeLabel("pkp");
        pembinaKepengasuhanList.getPager().setOffsetLabel("pko");

        pembantuPelaksanaKepengasuhanList.setName("pembantuPelaksanaKepengasuhanTbl");
        pembantuPelaksanaKepengasuhanList.getFilter().setStaticFilter(this::filterToRole);
        pembantuPelaksanaKepengasuhanList.getSelector().setSelectionsLabel("bks");
        pembantuPelaksanaKepengasuhanList.getPager().setPageSizeLabel("bkp");
        pembantuPelaksanaKepengasuhanList.getPager().setOffsetLabel("bko");

        kakakKepengasuhanList.setName("kakakKepengasuhanTbl");
        kakakKepengasuhanList.getFilter().setStaticFilter(this::filterToRole);
        kakakKepengasuhanList.getSelector().setSelectionsLabel("kks");
        kakakKepengasuhanList.getPager().setPageSizeLabel("kkp");
        kakakKepengasuhanList.getPager().setOffsetLabel("kko");

        pengasuhanList.setName("pengasuhanTbl");
        pengasuhanList.getFilter().setStaticFilter(this::filterFromRole);
        pengasuhanList.getSelector().setSelectionsLabel("ps");
        pengasuhanList.getPager().setPageSizeLabel("pp");
        pengasuhanList.getPager().setOffsetLabel("po");
    }

    @Creator(of = "pembinaKepengasuhanList")
    public void openPembinaKepengasuhanEditor() {
        gotoChild(PembinaKepengasuhanEditorPage.class)
                .addParam("toRole")
                .withValues(kelompokPengasuhan)
                .open();
    }

    @Deleter(of = "pembinaKepengasuhanList")
    public void openPembinaKepengasuhanDeleter() {
        pembinaKepengasuhanList.delete(pembinaKepengasuhanList.getSelector().getSelections());
    }

    @Creator(of = "kakakKepengasuhanList")
    public void openKakakKepengasuhanEditor() {
        gotoChild(KakakKepengasuhanEditorPage.class)
                .addParam("toRole")
                .withValues(kelompokPengasuhan)
                .open();
    }

    @Deleter(of = "kakakKepengasuhanList")
    public void openKakakKepengasuhanDeleter() {
        kakakKepengasuhanList.delete(kakakKepengasuhanList.getSelector().getSelections());
    }

    @Creator(of = "pembantuPelaksanaKepengasuhanList")
    public void openPembantuPelaksanaKepengasuhanEditor() {
        gotoChild(PembantuPelaksanaKepengasuhanEditorPage.class)
                .addParam("toRole")
                .withValues(kelompokPengasuhan)
                .open();
    }

    @Deleter(of = "pembantuPelaksanaKepengasuhanList")
    public void openPembantuPelaksanaKepengasuhanDeleter() {
        pembantuPelaksanaKepengasuhanList.delete(pembantuPelaksanaKepengasuhanList.getSelector().getSelections());
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

    public PembinaKepengasuhanList getPembinaKepengasuhanList() {
        return pembinaKepengasuhanList;
    }

    public KakakKepengasuhanList getKakakKepengasuhanList() {
        return kakakKepengasuhanList;
    }

    public PembantuPelaksanaKepengasuhanList getPembantuPelaksanaKepengasuhanList() {
        return pembantuPelaksanaKepengasuhanList;
    }

    public PengasuhanList getPengasuhanList() {
        return pengasuhanList;
    }
}
