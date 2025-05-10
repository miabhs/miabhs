/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.widget.BentukAktifitasLazyChooser;
import id.my.mdn.kupu.app.santri.view.widget.SantriLazyChooser;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import id.my.mdn.kupu.core.common.util.Do;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "altAktifitasList")
@Dependent
public class AltAktifitasList extends AbstractMutablePagedValueList<Aktifitas> {

    @Inject
    private AktifitasFacade dao;

    @Inject
    private AktifitasHarianFilter filterContent;

    @Inject
    private SantriLazyChooser santriChooser;

    @Inject
    private BentukAktifitasLazyChooser bentukAktifitasChooser;

    public AltAktifitasList() {
        super(Aktifitas.class);
    }

    @PostConstruct
    private void init() {
        filter.setContent(filterContent);
        setParameters(this::parameters);
        
        santriChooser.setListener(this::onSelectSantri);
        santriChooser.getList().getFilter().setStaticFilter(
                santriChooser.getList()
                        .getFilter().staticFilter.plus(filterContent::santriFilter)
        );

        bentukAktifitasChooser.setListener(this::onSelectBentukAktifitas);
    }

    private Map<String, Object> parameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("fromDate", filterContent.getFromDate());
        parameters.put("thruDate", filterContent.getThruDate());

        return parameters;
    }

    public void addBlank() {
        dao.createBlank(filterContent.getFromDate());
    }

    public void onSelectSantri(Santri santri) {
        update(getSelected(), "santri", santri);
    }

    public void onSelectBentukAktifitas(BentukAktifitas bentukAktifitas) {
        update(getSelected(), "bentukAktifitas", bentukAktifitas);
    }

    @Override
    protected List<Aktifitas> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<Aktifitas> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {

        Long countAll = dao.countAll(parameters, filters);
        return countAll;
    }

    @Override
    protected void updateInternal(Aktifitas entity, String field, Object newValue) {

        switch (field) {
            case "santri" -> {
                Santri santri = (Santri) newValue;
                entity.setSantri(santri);
                if (santri != null) {
                    entity.setSantriName(santri.getPerson().getName());
                    entity.setKelompokPengasuhanName(santri.getKelompokPengasuhan().getOrganization().getName());
                }
            }
            case "bentukAktifitas" -> {
                BentukAktifitas bentukAktifitas = (BentukAktifitas) newValue;
                entity.setBentukAktifitas(bentukAktifitas);
                if (bentukAktifitas != null) {
                    entity.setBentukAktifitasBentuk(bentukAktifitas.getBentuk());
                    entity.setBentukAktifitasJenis(bentukAktifitas.getJenis());
                    entity.setBentukAktifitasNilai(bentukAktifitas.getNilai());
                }
            }
            case "activityDate" -> {
                entity.setActivityDate((LocalDate) newValue);
            }
            case "notes" ->
                entity.setNotes((String) newValue);
            default ->
                Do.nothing();
        }

        entity.setConfirmed(
                entity.getSantri() != null && entity.getBentukAktifitas() != null
        );

    }

    @Override
    protected void createInternal(Aktifitas entity) {
        dao.create(entity);
    }

    @Override
    public void edit(Aktifitas entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(Aktifitas entity) {
        dao.remove(entity);
    }

//    @Override
//    public String[] getCreatePermission() {
//        return new String[]{"create_aktifitas"};
//    }
//    @Override
//    public String[] getUpdatePermission() {
//        return new String[]{"update_aktifitas"};
//    }
//    @Override
//    public String[] getDeletePermission() {
//        return new String[]{"delete_aktifitas"};
//    }
    public String[] getConfirmPermission() {
//        return new String[]{"konfirmasi_aktifitas"};
        return new String[]{};
    }

    public SantriLazyChooser getSantriChooser() {
        return santriChooser;
    }

    public BentukAktifitasLazyChooser getBentukAktifitasChooser() {
        return bentukAktifitasChooser;
    }
}
