/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.santri.entity.Santri;
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
@Named(value = "hikmahKauniyahList")
@Dependent
public class HikmahKauniyahList extends AbstractMutablePagedValueList<HikmahKauniyah> {

    @Inject
    private HikmahKauniyahFacade dao;

    @Inject
    private AktifitasHarianFilter filterContent;

    @Inject
    private SantriLazyChooser santriChooser;

    public HikmahKauniyahList() {
        super(HikmahKauniyah.class);
        setParameters(this::parameters);
    }

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
        santriChooser.setListener(this::onSelectSantri);
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

    @Override
    protected void updateInternal(HikmahKauniyah entity, String field, Object newValue) {
        switch (field) {
            case "date" -> { 
                entity.setDate((LocalDate) newValue);
            }
            case "santri" -> {
                Santri santri = (Santri) newValue;
                entity.setSantri(santri);
                if (santri != null) {
                    entity.setSantriName(santri.getPerson().getName());
                    entity.setKelompokPengasuhanName(santri.getKelompokPengasuhan().getOrganization().getName());
                }
            }
            case "content" -> entity.setContent((String) newValue);
            default -> 
                Do.nothing();
        }
    }

//    @Override
//    public String[] getCreatePermission() {
//        return new String[]{"create_hikmah_kauniyah"};
//    }

//    @Override
//    public String[] getUpdatePermission() {
//        return new String[]{"update_hikmah_kauniyah"};
//    }

//    @Override
//    public String[] getDeletePermission() {
//        return new String[]{"delete_hikmah_kauniyah"};
//    }

    public SantriLazyChooser getSantriChooser() {
        return santriChooser;
    }

}
