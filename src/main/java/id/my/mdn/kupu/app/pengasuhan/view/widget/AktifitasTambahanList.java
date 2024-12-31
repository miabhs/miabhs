/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahan;
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
@Named(value = "aktifitasTambahanList")
@Dependent
public class AktifitasTambahanList extends AbstractMutablePagedValueList<AktifitasTambahan> {

    @Inject
    private AktifitasTambahanFacade dao;

    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<AktifitasTambahan> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<AktifitasTambahan> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters, Arrays.asList());
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, 0L);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("bentukAktifitas"));
    }

    @Override
    protected void createInternal(AktifitasTambahan entity) {
        dao.create(entity);
    }

    @Override
    public void edit(AktifitasTambahan entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(AktifitasTambahan entity) {
        dao.remove(entity);
    }

}
