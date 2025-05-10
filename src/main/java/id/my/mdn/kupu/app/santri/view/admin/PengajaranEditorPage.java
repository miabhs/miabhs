/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.pengajaran.dao.PengajaranFacade;
import id.my.mdn.kupu.app.santri.dao.PengampuHalaqohFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.Pengajaran;
import id.my.mdn.kupu.app.santri.entity.PengampuHalaqoh;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.SantriSingleChooserPage;
import id.my.mdn.kupu.app.santri.view.widget.JenisKitabList;
import id.my.mdn.kupu.app.santri.view.widget.SantriLazyChooser;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pengajaranEditorPage")
@ConversationScoped
public class PengajaranEditorPage extends FormPage<Pengajaran> {

    private static final int CHOOSE_FROMROLE = 1;
    private static final int CHOOSE_TOROLE = 2;

    @Inject
    private PengajaranFacade dao;

    @Inject
    private PengampuHalaqohFacade pengampuHalaqohFacade;
    
    @Inject
    private JenisKitabList jenisKitabList;

    @Bookmarked(name = "fromRole")
    private HalaqohPengajaran fromRole;

    @Bookmarked(name = "toRole")
    private Santri toRole;

    @Inject
    private SantriLazyChooser santriChooser;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        jenisKitabList.getFilter().setStaticFilter(this::getJenisKitabFilter);
        
        santriChooser.setListener(this::onSelectSantri);
        santriChooser.getList().getFilter().setStaticFilter(                
               santriChooser.getList()
                       .getFilter().staticFilter.plus(this::santriFilter)
        );
    }

    public List<FilterData> santriFilter() {
        List<FilterData> filterSantri = new ArrayList<>();
        
        GenderType gender = fromRole.getGender();
        
        if(gender != null) {
            filterSantri.add(FilterData.by("gender", gender));
        }
        
        return filterSantri;
    }

    public void onSelectSantri(Santri santri) {
        entity.setSantri(santri);
    }
    
    private List<FilterData> getJenisKitabFilter() {
        List<FilterData> filters = new ArrayList<>();
        filters.add(FilterData.by("id", fromRole.getJenisKitabId()));
        return filters;
    }

    @Override
    public void load() {
        super.load();
        updateAddressBar();
    }

    @Override
    protected Pengajaran newEntity() {
        PengampuHalaqoh pengampu = pengampuHalaqohFacade.findSingleByAttributes(
                List.of(
                        FilterData.by("toRole", fromRole),
                        FilterData.by("ondate", LocalDate.now())
                )
        );
        fromRole.setPengampuName(pengampu != null ? pengampu.getUstadz().getPerson().getName() : null);
        Pengajaran pengajaran = Pengajaran.builder()
                .from(fromRole)
                .to(toRole)
                .get();
        
        return pengajaran;
    }

    @Override
    protected Result<String> save(Pengajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Pengajaran entity) {
        return dao.edit(entity);
    }

    public void openFromRoleChooser() {
        gotoChild(SantriSingleChooserPage.class)
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
                    entity.setFromRole((HalaqohPengajaran) returns);
                    break;
                case CHOOSE_TOROLE:
                    entity.setToRole((Santri) returns);
                    break;
                default:
                    break;
            }
        }
        return super.onReturns(what, returns);
    }

    public HalaqohPengajaran getFromRole() {
        return fromRole;
    }

    public void setFromRole(HalaqohPengajaran fromRole) {
        this.fromRole = fromRole;
    }

    public Santri getToRole() {
        return toRole;
    }

    public void setToRole(Santri toRole) {
        this.toRole = toRole;
    }

    public JenisKitabList getJenisKitabList() {
        return jenisKitabList;
    }

    public SantriLazyChooser getSantriChooser() {
        return santriChooser;
    }

}
