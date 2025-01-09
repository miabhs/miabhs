/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.HalaqohPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "halaqohPengajaranList")
@Dependent
public class HalaqohPengajaranList extends AbstractMutablePagedValueList<HalaqohPengajaran> {
    
    @Inject
    private HalaqohPengajaranFacade dao;
    
    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<HalaqohPengajaran> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<HalaqohPengajaran> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(filters);
    }

    @Override
    protected void createInternal(HalaqohPengajaran entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(HalaqohPengajaran entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(HalaqohPengajaran entity) {
        dao.edit(entity);
    }   
    
}
