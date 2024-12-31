/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.JenisKitabFacade;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
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
@Named(value = "jenisKitabList")
@Dependent
public class JenisKitabList extends AbstractMutablePagedValueList<JenisKitab> {

    @Inject
    private JenisKitabFacade dao;
    
    @Inject
    private JenisKitabFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<JenisKitab> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<JenisKitab> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(filters);
    }

    @Override
    protected void createInternal(JenisKitab entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(JenisKitab entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(JenisKitab entity) {
        dao.edit(entity);
    }
    
    private final JenisKitabList that = this;

    private LazyDataModel<JenisKitab> model;

    public LazyDataModel<JenisKitab> getModel() {
        if (model == null) {
            model = new LazyDataModel<JenisKitab>() {
                
                private final Filter filter = that.filter;

                @Override
                public List<JenisKitab> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                    return that.getPagedFetchedItemsInternal(first, pageSize,
                            that.parameters.get(), filter.getValues(), getSorters(),
                            that.defaultList, that.defaultChecker);
                }

                @Override
                public int count(Map<String, FilterMeta> map) {
                    return (int) that.getItemsCountInternal(that.parameters.get(), filter.getValues(), that.defaultCount, that.defaultChecker);
                }

                @Override
                public String getRowKey(JenisKitab object) {
                    return object != null && object.getId() != null ? object.getId().toString() : null;
                }

                @Override
                public JenisKitab getRowData(String rowKey) {
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
