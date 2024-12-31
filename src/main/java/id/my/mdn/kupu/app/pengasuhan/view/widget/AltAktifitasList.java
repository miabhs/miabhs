/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import static id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData.DESC;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.Arrays;
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

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
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
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("activityDate", DESC), new SorterData("created", DESC));
    }

    @Override
    protected void updateInternal(Aktifitas entity, String field, Object newValue) {        

        switch (field) {
            case "activityDate":
                entity.setActivityDate((LocalDate) newValue);
                break;
            case "notes":
                entity.setNotes((String) newValue);
                break;
            default:
                break;
        }
    }

    @Inject
    private PengasuhanService pengasuhanService;

    public void onChangeSantri(ValueChangeEvent evt) {
        Santri santri = (Santri) evt.getNewValue();
        KelompokPengasuhan kelompokPengasuhan = pengasuhanService.getKelompokPengasuhan(santri.getId(), LocalDate.now());

        santri.setKelompokPengasuhan(kelompokPengasuhan);
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

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_aktifitas"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_aktifitas"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_aktifitas"};
    }

    public String[] getConfirmPermission() {
        return new String[]{"konfirmasi_aktifitas"};
    }
}
