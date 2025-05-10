/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.app.pengasuhan.view.widget.BentukAktifitasFilter;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList.DefaultCount;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList.DefaultList;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "bentukAktifitasLazyList")
@Dependent
public class BentukAktifitasLazyList implements Serializable {

    @Inject
    private BentukAktifitasFacade dao;

    private LazyDataModel<BentukAktifitas> model;

    protected final Map<String, Object> parameters = new HashMap<>();

    protected final List<SorterData> listSorterData = new ArrayList<>();

    protected DefaultList<BentukAktifitas> defaultList = () -> null;

    protected DefaultChecker defaultChecker = null;

    protected DefaultCount defaultCount = () -> null;

    protected Filter filter;

    @Inject
    private BentukAktifitasFilter filterContent;

    @PostConstruct
    public void init() {
        filter = new Filter(filterContent);

        model = new LazyDataModel<BentukAktifitas>() {
            @Override
            public List<BentukAktifitas> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                
                List<BentukAktifitas> data = dao.findAll(first, pageSize, parameters, filter.getValues(), getSorters(), defaultList.get(), defaultChecker);
                if (first == 0) {
                    BentukAktifitas b = new BentukAktifitas();
                    b.setId("");
                    b.setBentuk("Semua");
                    data.add(0, b);
                }
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
            public String getRowKey(BentukAktifitas object) {
                return object != null ? object.getId() : null;
            }

            @Override
            public BentukAktifitas getRowData(String rowKey) {
                if (rowKey.isBlank()) {
                    return null;
                }
                return dao.find(rowKey);
            }
        };
    }

    protected List<SorterData> getSorters() {
        return listSorterData;
    }

    public LazyDataModel<BentukAktifitas> getModel() {
        return model;
    }

    public Filter getFilter() {
        return filter;
    }

}
