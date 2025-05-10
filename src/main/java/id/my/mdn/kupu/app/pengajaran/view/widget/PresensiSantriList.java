/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.widget;

import id.my.mdn.kupu.app.pengajaran.dao.PresensiSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import id.my.mdn.kupu.app.pengasuhan.service.PengasuhanService;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "presensiSantriList")
@Dependent
public class PresensiSantriList extends AbstractMutablePagedValueList<PresensiSantri> {    
    
    @Inject
    private PresensiSantriFacade dao;

    @Inject
    private PengajaranSantriFilter filterContent;

    public PresensiSantriList() {
        super(PresensiSantri.class);
    }

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<PresensiSantri> getPagedFetchedItemsInternal(int first, int pageSize, 
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<PresensiSantri> defaultList, 
            AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, 
            List<FilterData> filters, DefaultCount defaultCount, 
            DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Inject
    private PengasuhanService pengasuhanService;

    public void onChangeSantri(ValueChangeEvent evt) {
        Santri santri = (Santri) evt.getNewValue();
        KelompokPengasuhan kelompokPengasuhan = pengasuhanService.getKelompokPengasuhan(santri.getId(), LocalDate.now());

        santri.setKelompokPengasuhan(kelompokPengasuhan);
    }

    @Override
    protected void createInternal(PresensiSantri entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(PresensiSantri entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(PresensiSantri entity) {
        dao.edit(entity);
    }
    
}
