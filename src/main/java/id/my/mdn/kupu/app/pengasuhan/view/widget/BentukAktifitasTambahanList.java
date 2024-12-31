/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "bentukAktifitasTambahanList")
@Dependent
public class BentukAktifitasTambahanList extends AbstractMutablePagedValueList<BentukAktifitasTambahan> {

    @Inject
    private BentukAktifitasTambahanFacade dao;

    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<BentukAktifitasTambahan> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<BentukAktifitasTambahan> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("id"));
    }

    @Override
    protected void createInternal(BentukAktifitasTambahan entity) {
        dao.create(entity);
    }

    @Override
    public void edit(BentukAktifitasTambahan entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(BentukAktifitasTambahan entity) {
        dao.remove(entity);
    }

}
