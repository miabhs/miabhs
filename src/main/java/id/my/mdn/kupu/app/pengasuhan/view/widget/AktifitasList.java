/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "aktifitasList")
@Dependent
public class AktifitasList extends AbstractMutablePagedValueList<Aktifitas> {
    
    private static final Logger LOG = Logger.getLogger(Aktifitas.class.getCanonicalName());

    @Inject
    private AktifitasFacade dao;

    @PostConstruct
    public void init() {
        
    }

    @Override
    protected List<Aktifitas> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<Aktifitas> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(
                new SorterData("activityDate"), 
                new SorterData("created")
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
    
    @Override
    public void onCellEdit(CellEditEvent event) {   
        String id = event.getRowKey();
        Aktifitas aktifitas = dao.find(id);
        Object obj = event.getNewValue();
        String field = event.getColumn().getField();
        
        switch(field) {
            case "activityDate":
                aktifitas.setActivityDate((LocalDate) obj);
                break;
            case "santri":
                aktifitas.setSantri((Santri) obj);
                break;
            case "bentukAktifitas":
                aktifitas.setBentukAktifitas((BentukAktifitas) obj);
                break;
            default:
                break;
        }
        
//        Aktifitas aktifitas = fetchedItems.get(event.getRowIndex());
        dao.edit(aktifitas);
    }
    
    @Inject
    private PengasuhanService pengasuhanService;
    
    public void onChangeSantri(ValueChangeEvent evt) {
        Santri santri = (Santri) evt.getNewValue();
        KelompokPengasuhan kelompokPengasuhan = pengasuhanService.getKelompokPengasuhan(santri.getId(), LocalDate.now());
        
        santri.setKelompokPengasuhan(kelompokPengasuhan);
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

}
