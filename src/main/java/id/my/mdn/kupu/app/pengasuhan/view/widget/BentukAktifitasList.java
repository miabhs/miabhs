/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.Filter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "bentukAktifitasList")
@Dependent
public class BentukAktifitasList extends AbstractMutablePagedValueList<BentukAktifitas> {

    @Inject
    private BentukAktifitasFacade dao;
    
    @Inject
    private BentukAktifitasFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<BentukAktifitas> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<BentukAktifitas> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("kode"), new SorterData("created"));
    }

    @Override
    protected void createInternal(BentukAktifitas entity) {
        dao.create(entity);
    }

    @Override
    public void edit(BentukAktifitas entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(BentukAktifitas entity) {
        dao.remove(entity);
    }
    
    private final BentukAktifitasList that = this;
    
    private LazyDataModel<BentukAktifitas> model;

    public LazyDataModel<BentukAktifitas> getModel() {
        if (model == null) {
            model = new LazyDataModel<BentukAktifitas>() {
                
                private final Filter filter = that.filter;

                @Override
                public List<BentukAktifitas> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                    List<BentukAktifitas> data = getPagedFetchedItemsInternal(first, pageSize,
                            that.parameters.get(), filter.getValues(), getSorters(),
                            that.defaultList, that.defaultChecker);
                    
                    return data;
                }

                @Override
                public int count(Map<String, FilterMeta> map) {
                    return (int) getItemsCountInternal(that.parameters.get(), filter.getValues(), that.defaultCount, that.defaultChecker);
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
        return model;
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_bentuk_aktifitas"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_bentuk_aktifitas"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_bentuk_aktifitas"};
    }

}
