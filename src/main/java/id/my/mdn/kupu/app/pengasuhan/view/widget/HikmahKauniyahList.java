/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import static id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData.DESC;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "hikmahKauniyahList")
@Dependent
public class HikmahKauniyahList extends AbstractMutablePagedValueList<HikmahKauniyah> {

    @Inject
    private HikmahKauniyahFacade dao;

    @Inject
    private AktifitasHarianFilter filterContent;

    @Inject
    private PengasuhanService pengasuhanService;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
        listSorterData.add(new SorterData("created", DESC));
    }

    @Override
    protected List<HikmahKauniyah> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<HikmahKauniyah> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(HikmahKauniyah entity) {
        dao.create(entity);
    }

    @Override
    public void edit(HikmahKauniyah entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(HikmahKauniyah entity) {
        dao.remove(entity);
    }

    public AktifitasHarianFilter getFilterContent() {
        return filterContent;
    }

    @Override
    protected HikmahKauniyah findEntity(String id) {
        return dao.find(id);
    }

    @Override
    protected void updateInternal(HikmahKauniyah entity, String field, Object newValue) {
        switch (field) {
            case "date":
                entity.setDate((LocalDate) newValue);
                break;
            case "santri":
                entity.setSantri((Santri) newValue);
                break;
            case "content":
                entity.setContent((String) newValue);
                break;
            default:
                break;
        }
    }

    public void onChangeSantri(ValueChangeEvent evt) {
        Santri santri = (Santri) evt.getNewValue();
        KelompokPengasuhan kelompokPengasuhan = pengasuhanService.getKelompokPengasuhan(santri.getId(), LocalDate.now());

        santri.setKelompokPengasuhan(kelompokPengasuhan);
    }

//    @Override
//    public void onCellEdit(CellEditEvent event) {
//        int index = event.getRowIndex();
//        HikmahKauniyah hikmahKauniyah = getFetchedItems().get(index);
//        Object obj = event.getNewValue();
//        String field = event.getColumn().getExportValue();
//
//        switch (field) {
//            case "date":
//                hikmahKauniyah.setDate((LocalDate) obj);
//                dao.edit(hikmahKauniyah);
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_hikmah_kauniyah"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_hikmah_kauniyah"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_hikmah_kauniyah"};
    }

}
