/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import id.my.mdn.kupu.core.common.view.widget.CommonFilter;
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
@Named(value = "tahunPembelajaranList")
@Dependent
public class TahunPembelajaranList extends AbstractMutablePagedValueList<TahunPembelajaran> {
    
    @Inject
    private TahunPembelajaranFacade dao;

    @Inject
    private CommonFilter filterContent;

    public TahunPembelajaranList() {
        super(TahunPembelajaran.class);
    }

    @PostConstruct
    public void init() {        
        filter.setContent(filterContent);
    }

    @Override
    protected List<TahunPembelajaran> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<TahunPembelajaran> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(TahunPembelajaran entity) {
        dao.create(entity);
    }

    @Override
    public void edit(TahunPembelajaran entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(TahunPembelajaran entity) {
        dao.remove(entity);
    }

    @Override
    public String[] getCreatePermission() {
//        return new String[]{"create_tahun_pembelajaran"};
        return new String[]{};
    }

    @Override
    public String[] getUpdatePermission() {
//        return new String[]{"update_tahun_pembelajaran"};
        return new String[]{};
    }

    @Override
    public String[] getDeletePermission() {
//        return new String[]{"delete_tahun_pembelajaran"};
        return new String[]{};
    }
    
}
