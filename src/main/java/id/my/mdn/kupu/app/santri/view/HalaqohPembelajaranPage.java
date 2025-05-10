/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.pengajaran.dao.PelaksanaanHalaqohFacade;
import id.my.mdn.kupu.app.pengajaran.dao.PenasehatanSantriFacade;
import id.my.mdn.kupu.app.pengajaran.dao.PresensiSantriFacade;
import id.my.mdn.kupu.app.pengajaran.dao.SetoranSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaranValue;
import id.my.mdn.kupu.app.pengajaran.entity.PelaksanaanHalaqoh;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import id.my.mdn.kupu.app.pengajaran.entity.SetoranSantri;
import id.my.mdn.kupu.app.pengajaran.view.AbsensiHalaqohPage;
import id.my.mdn.kupu.app.santri.dao.JenisKitabFacade;
import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.RangkumanPengajaran;
import id.my.mdn.kupu.app.santri.view.admin.HalaqohPembelajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.admin.PengajaranEditorPage;
import id.my.mdn.kupu.app.santri.view.admin.PengampuHalaqohEditorPage;
import id.my.mdn.kupu.app.santri.view.widget.HalaqohPengajaranList;
import id.my.mdn.kupu.app.santri.view.widget.PengajaranFilter;
import id.my.mdn.kupu.app.santri.view.widget.PengajaranList;
import id.my.mdn.kupu.core.base.view.Page;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import id.my.mdn.kupu.core.base.view.annotation.Creator;
import id.my.mdn.kupu.core.base.view.annotation.Deleter;
import id.my.mdn.kupu.core.base.view.annotation.Editor;
import id.my.mdn.kupu.core.base.view.widget.Selector;
import jakarta.annotation.PostConstruct;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohPembelajaranPage")
@ViewScoped
public class HalaqohPembelajaranPage extends Page implements Serializable {

    @Inject
    @Bookmarked
    private HalaqohPengajaranList masterDataView;

    @Inject
    @Bookmarked
    private PengajaranList detailDataView;
    
    @Inject
    private JenisKitabFacade jenisKitabFacade;
    
    @Inject
    private PelaksanaanHalaqohFacade pelaksanaanHalaqohFacade;

    @Inject
    private PenasehatanSantriFacade penasehatanSantriFacade;

    @Inject
    private PresensiSantriFacade presensiSantriFacade;

    @Inject
    private SetoranSantriFacade setoranSantriFacade;

    private List<PelaksanaanHalaqoh> listPelaksanaanHalaqoh;

    private PelaksanaanHalaqoh pelaksanaanHalaqoh;

    private final Map<String, List<AtributPembelajaranValue>> atributOptions = new HashMap<>();

    private String cId = "";    
        
    private PresensiSantri presensiSantri;
    
    private SetoranSantri setoranSantri;
    
    @Inject
    private TahunPembelajaranFacade tahunPembelajaranFacade;
    
    @Inject
    private PeriodePembelajaranFacade periodePembelajaranFacade;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        
        masterDataView.setSelectionMode(() -> Selector.SINGLE);
        masterDataView.setName("halaqohTbl");
        masterDataView.getSelector().setSelectionsLabel("ms");
        masterDataView.getFilter().setName("masterFilter");
        masterDataView.getPager().setPageSizeLabel("mpz");
        masterDataView.getPager().setOffsetLabel("mpo");
        
        PengajaranFilter pengajaranFilter = detailDataView.getFilter().<PengajaranFilter>getContent();
        
        masterDataView.addSelectListener(s -> {
            pengajaranFilter.setHalaqohPengajaran(masterDataView.getSelected());
            if (pengajaranFilter.getTahunPembelajaran() == null) {
                pengajaranFilter.setTahunPembelajaran(tahunPembelajaranFacade.getCurrentTahunPembelajaran());
            }
            if (pengajaranFilter.getBulanPembelajaran() == null) {
                pengajaranFilter.setBulanPembelajaran(periodePembelajaranFacade.getPeriodePembelajaranMin(LocalDate.now(), JenisPeriodePembelajaran.BULANAN));
            }
            if (pengajaranFilter.getPeriodePembelajaran() == null) {
                pengajaranFilter.setPeriodePembelajaran(periodePembelajaranFacade.getPeriodePembelajaranMin(LocalDate.now(), JenisPeriodePembelajaran.PEKANAN));
            }
            listPelaksanaanHalaqoh = null;

        });

        detailDataView.setSelectionMode(() -> Selector.SINGLE);
        detailDataView.setName("santriTbl");
        detailDataView.getSelector().setSelectionsLabel("ds");
        detailDataView.getPager().setPageSizeLabel("dpz");
        detailDataView.getPager().setOffsetLabel("dpo");
        detailDataView.setParameters(this::parametersPengajaran);
        detailDataView.setCached(false);
        detailDataView.getFilter().addListener((obj) -> listPelaksanaanHalaqoh = null);
            
        detailDataView.setDefaultChecker(() -> {
            return masterDataView.getSelected() == null
                    || pengajaranFilter.getTahunPembelajaran() == null
                    || pengajaranFilter.getBulanPembelajaran() == null
                    || pengajaranFilter.getPeriodePembelajaran() == null;
        });

    }

    private Map<String, Object> parametersPengajaran() {
        PengajaranFilter filter = detailDataView.getFilter()
                .<PengajaranFilter>getContent();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("hp", filter.getHalaqohPengajaran());

        parameters.put("pp", filter.getPeriodePembelajaran());
        return parameters;
    }

    public List<PelaksanaanHalaqoh> getListPelaksanaanHalaqoh() {
        if (listPelaksanaanHalaqoh == null) {
            PeriodePembelajaran periodePembelajaran = detailDataView.getFilter()
                    .<PengajaranFilter>getContent().getPeriodePembelajaran();

            listPelaksanaanHalaqoh = pelaksanaanHalaqohFacade.getListDate(
                    masterDataView.getSelected(),
                    periodePembelajaran,
                    0, 1, 2, 3, 4, 5
            );
        }

        return listPelaksanaanHalaqoh;
    }

    @Creator(of = "masterDataView")
    public void addHalaqoh() {
        gotoChild(HalaqohPembelajaranEditorPage.class)
                .open();
    }

    @Editor(of = "masterDataView")
    public void editHalaqoh() {
        gotoChild(HalaqohPembelajaranEditorPage.class)
                .addParam("entity")
                .withValues(masterDataView.getSelected())
                .open();
    }

    @Deleter(of = "masterDataView")
    public void removeHalaqoh() {
        masterDataView.deleteSelected();
    }

    @Creator(of = "detailDataView")
    public void changePengampu() {
        gotoChild(PengampuHalaqohEditorPage.class)
                .addParam("toRole")
                .withValues(masterDataView.getSelected())
                .open();
    }

    @Creator(of = "detailDataView")
    public void addSantri() {
        gotoChild(PengajaranEditorPage.class)
                .addParam("fromRole")
                .withValues(masterDataView.getSelected())
                .open();
    }

    @Editor(of = "detailDataView")
    public void editSantri() {

    }

    @Deleter(of = "detailDataView")
    public void removeSantri() {
        detailDataView.deleteSelected();
    }

    public void printAbsensiHalaqoh(ActionEvent evt) {
        PengajaranFilter filter = detailDataView.getFilter()
                .<PengajaranFilter>getContent();
        gotoChild(AbsensiHalaqohPage.class)
                .addParam("hp")
                .withValues(masterDataView.getSelected())
                .addParam("tp")
                .withValues(filter.getTahunPembelajaran())
                .addParam("mp")
                .withValues(filter.getBulanPembelajaran())
                .addParam("pp")
                .withValues(filter.getPeriodePembelajaran())
                .open();
    }

    public void onEditInfoHalaqoh(ActionEvent evt) {
        Map<String, Object> attributes = evt.getComponent().getAttributes();
        cId = (String) attributes.get("cId");
        pelaksanaanHalaqoh = (PelaksanaanHalaqoh) attributes.get("pelaksanaanHalaqoh");
    }

    public void onCustomizeTime(AjaxBehaviorEvent evt) {
        boolean customTime = pelaksanaanHalaqoh.isCustomTime();
        if (customTime) {
            pelaksanaanHalaqoh.setBeginTime(LocalTime.MIN);
            pelaksanaanHalaqoh.setEndTime(LocalTime.MIN);
        } else {
            pelaksanaanHalaqoh.setCustomTime(false);
            pelaksanaanHalaqoh.setBeginTime(null);
            pelaksanaanHalaqoh.setEndTime(null);
        }
    }

    public void onSaveInfoHalaqoh(ActionEvent evt) {
        pelaksanaanHalaqoh.getId().setHalaqohPengajaran(masterDataView.getSelected().getId());
        pelaksanaanHalaqoh.setHalaqohPengajaran(masterDataView.getSelected());
        pelaksanaanHalaqohFacade.createOrUpdate(pelaksanaanHalaqoh);
    }

    public void resetInfo(ActionEvent evt) {
        pelaksanaanHalaqoh = null;
    }

    public void updateNasehat(ValueChangeEvent evt) {
        RangkumanPengajaran pengajaran = (RangkumanPengajaran) evt.getComponent().getAttributes().get("pengajaran");
        LocalDate pengajaranDate = (LocalDate) evt.getComponent().getAttributes().get("pengajaranDate");
        HalaqohPengajaran halaqohPengajaran = masterDataView.getSelected();
        penasehatanSantriFacade.saveNasehat(pengajaran.getId(), pengajaranDate, halaqohPengajaran);
    }

    public void editPresensi(ActionEvent evt) {
        RangkumanPengajaran pengajaran = (RangkumanPengajaran) evt.getComponent().getAttributes().get("pengajaran");
        LocalDate pengajaranDate = (LocalDate) evt.getComponent().getAttributes().get("pengajaranDate");
        
        cId = (String) evt.getComponent().getAttributes().get("cId");

        presensiSantri = presensiSantriFacade.getPresensi(pengajaran.getId(), pengajaranDate);
    }
    
    public void editSetoran(ActionEvent evt) {        
        RangkumanPengajaran pengajaran = (RangkumanPengajaran) evt.getComponent().getAttributes().get("pengajaran");
        LocalDate pengajaranDate = (LocalDate) evt.getComponent().getAttributes().get("pengajaranDate");
        
        cId = (String) evt.getComponent().getAttributes().get("cId");
                
        if(setoranSantri != null && !setoranSantri.getKitabId().equals(pengajaran.getKitabId())) {
            atributOptions.clear();
        }
        
        setoranSantri = setoranSantriFacade.getSetoran(pengajaran.getId(), pengajaran.getKitabId(), pengajaranDate);
        setoranSantri.setKitabId(pengajaran.getKitabId());
    }

    public void updatePresensi(ActionEvent evt) {
        presensiSantriFacade.updatePresensi(presensiSantri);
    }

    public void updateSetoran(ActionEvent evt) {
        setoranSantriFacade.updateSetoran(setoranSantri);
    }

    public List<AtributPembelajaranValue> getAtributOptions(String atributName) {
        if (atributOptions.get(atributName) == null) {
            List<AtributPembelajaranValue> options = jenisKitabFacade.getAtributOptions(
                    setoranSantri.getKitabId(),
                    atributName
            );
            atributOptions.put(atributName, options);
        }
        return atributOptions.get(atributName);
    }

    public PresensiSantri getPresensiSantri() {
        return presensiSantri;
    }

    public SetoranSantri getSetoranSantri() {
        return setoranSantri;
    }

    public HalaqohPengajaranList getMasterDataView() {
        return masterDataView;
    }

    public PengajaranList getDetailDataView() {
        return detailDataView;
    }

    public String getcId() {
        return cId;
    }

    public PelaksanaanHalaqoh getPelaksanaanHalaqoh() {
        return pelaksanaanHalaqoh;
    }

    public void setPelaksanaanHalaqoh(PelaksanaanHalaqoh pelaksanaanHalaqoh) {
        this.pelaksanaanHalaqoh = pelaksanaanHalaqoh;
    }

}
