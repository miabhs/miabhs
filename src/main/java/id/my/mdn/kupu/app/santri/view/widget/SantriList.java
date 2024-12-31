/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriList")
@Dependent
public class SantriList extends AbstractMutablePagedValueList<Santri> {

    @Inject
    private SantriFacade dao;

    @Inject
    private SantriFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<Santri> getPagedFetchedItemsInternal(int first, int pageSize,
            Map<String, Object> parameters, List<FilterData> filters, 
            List<SorterData> sorters, DefaultList<Santri> defaultList, 
            DefaultChecker defaultChecker) {        
        return dao.findAll(first, pageSize, parameters, 
                filters, sorters, defaultList.get(), defaultChecker);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, 
            List<FilterData> filters, DefaultCount defaultCount, 
            DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, 
                defaultCount.get(), defaultChecker);
    }

    @Override
    protected void createInternal(Santri entity) {
        dao.create(entity);
    }

    @Override
    public void edit(Santri entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(Santri entity) {
        dao.remove(entity);
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_santri"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_santri"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_santri"};
    }
    
    private final SantriList that = this;

    private LazyDataModel<Santri> model;

    public LazyDataModel<Santri> getModel() {
        if (model == null) {
            model = new LazyDataModel<Santri>() {
                
                private final Filter filter = that.filter;

                @Override
                public List<Santri> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                    return that.getPagedFetchedItemsInternal(first, pageSize,
                            that.parameters.get(), filter.getValues(), getSorters(),
                            that.defaultList, that.defaultChecker);
                }

                @Override
                public int count(Map<String, FilterMeta> map) {
                    return (int) that.getItemsCountInternal(that.parameters.get(), filter.getValues(), that.defaultCount, that.defaultChecker);
                }

                @Override
                public String getRowKey(Santri object) {
                    return object != null && object.getId() != null ? object.getId().toString() : null;
                }

                @Override
                public Santri getRowData(String rowKey) {
                    if (rowKey.isBlank()) {
                        return null;
                    }
                    return that.dao.find(Long.valueOf(rowKey));
                }
            };
        }
        return model;
    }

}
