/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList.DefaultCount;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList.DefaultList;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.view.widget.IValueList;
import id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "lazySantriList")
@ViewScoped
public class LazySantriList implements Serializable {

    @Inject
    private SantriFacade dao;

    protected Filter filter;

    private LazyDataModel<Santri> model;

    protected final Map<String, Object> parameters = new HashMap<>();
    
    protected final List<IValueList.SorterData> listSorterData = new ArrayList<>();
    
    protected DefaultList<Santri> defaultList = () -> null;
    
    protected DefaultChecker defaultChecker = null;
    
    protected DefaultCount defaultCount = () -> null;

    @Inject
    private SantriFilter filterContent;

    @PostConstruct
    public void init() {
        filter = new Filter(filterContent);
        filterContent.setStatusKesantrian(StatusKesantrian.ACTIVE);

        model = new LazyDataModel<Santri>() {
            @Override
            public List<Santri> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                List<Santri> data = dao.findAll(first, pageSize, 
                        parameters, filter.getValues(), getSorters(), 
                        defaultList.get(), defaultChecker);
//                if(first == 0) data.add(0, null);
                return data;
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                return dao.countAll(
                        parameters, filter.getValues(), 
                        defaultCount.get(), defaultChecker)
                        .intValue();
            }

            @Override
            public String getRowKey(Santri object) {
                return object != null ? object.getId().toString() : null;
            }

            @Override
            public Santri getRowData(String rowKey) {
                if(rowKey.isBlank()) return null;
                return dao.find(Long.valueOf(rowKey));
            }
        };
    }

    protected List<SorterData> getSorters() {
        return listSorterData;
    }

    public LazyDataModel<Santri> getModel() {
        return model;
    }

    public Filter getFilter() {
        return filter;
    }

}
