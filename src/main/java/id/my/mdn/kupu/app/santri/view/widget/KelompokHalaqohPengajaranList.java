/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.KelompokHalaqohPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokHalaqohPengajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
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
 * @author aphasan
 */
@Named(value = "kelompokHalaqohPengajaranList")
@Dependent
public class KelompokHalaqohPengajaranList extends AbstractMutablePagedValueList<KelompokHalaqohPengajaran> {

    @Inject
    private KelompokHalaqohPengajaranFacade dao;

    @Inject
    private CommonFilter filterContent;

    public KelompokHalaqohPengajaranList() {
        super(KelompokHalaqohPengajaran.class);
    }

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<KelompokHalaqohPengajaran> getPagedFetchedItemsInternal(
            int first, int pageSize, Map<String, Object> parameters,
            List<FilterData> filters, List<SorterData> sorters,
            DefaultList<KelompokHalaqohPengajaran> defaultList, DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters,
                filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters,
            DefaultCount defaultCount, DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(KelompokHalaqohPengajaran entity) {
        dao.create(entity);
    }

    @Override
    protected void deleteInternal(KelompokHalaqohPengajaran entity) {
        dao.remove(entity);
    }

    @Override
    public void edit(KelompokHalaqohPengajaran entity) {
        dao.edit(entity);
    }

}
