/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.RangkumanPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.RangkumanPengajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
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
@Named(value = "pengajaranList")
@Dependent
public class PengajaranList extends AbstractMutablePagedValueList<RangkumanPengajaran> {
    
    @Inject
    private RangkumanPengajaranFacade dao;

    @Inject
    private PengajaranFilter filterContent;

    public PengajaranList() {
        super(RangkumanPengajaran.class);
    }

    @PostConstruct
    public void init() {        
        filter.setContent(filterContent);
    }

    @Override
    protected List<RangkumanPengajaran> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters, 
            List<FilterData> filters, List<SorterData> sorters, 
            DefaultList<RangkumanPengajaran> defaultList, DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters, List<FilterData> filters,
            DefaultCount defaultCount, DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(RangkumanPengajaran entity) {
        dao.create(entity);
    }

    @Override
    public void edit(RangkumanPengajaran entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(RangkumanPengajaran entity) {
        dao.remove(entity);
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{};
    }    
    
}
