/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.widget;

import id.my.mdn.kupu.app.pengajaran.dao.PengajaranSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantri;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
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
@Named(value = "penngajaranSantriList")
@Dependent
public class PengajaranSantriList extends AbstractMutablePagedValueList<PengajaranSantri> {    
    
    @Inject
    private PengajaranSantriFacade dao;

    @Inject
    private PengajaranSantriFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<PengajaranSantri> getPagedFetchedItemsInternal(int first, int pageSize, 
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<PengajaranSantri> defaultList, 
            AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, 
            List<FilterData> filters, DefaultCount defaultCount, 
            DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("pengajaranDate", DESC), new SorterData("created", DESC));
    }

//    @Override
//    public void onCellEdit(CellEditEvent event) {
//        int index = event.getRowIndex();
//        PengajaranSantri pengajaran = getFetchedItems().get(index);
//        Object obj = event.getNewValue();
//        String field = event.getColumn().getExportValue();
//
//        switch (field) {
//            case "pengajaranDate":
//                pengajaran.setPengajaranDate((LocalDate) obj);
//                dao.edit(pengajaran);
//                break;
//            case "notes":
//                pengajaran.setNotes((String) obj);
//                dao.edit(pengajaran);
//                break;
//            default:
//                break;
//        }
//    }

    @Inject
    private PengasuhanService pengasuhanService;

    public void onChangeSantri(ValueChangeEvent evt) {
        Santri santri = (Santri) evt.getNewValue();
        KelompokPengasuhan kelompokPengasuhan = pengasuhanService.getKelompokPengasuhan(santri.getId(), LocalDate.now());

        santri.setKelompokPengasuhan(kelompokPengasuhan);
    }

    @Override
    protected void createInternal(PengajaranSantri entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(PengajaranSantri entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(PengajaranSantri entity) {
        dao.edit(entity);
    }
    
}
