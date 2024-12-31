/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.StatusSantriFacade;
import id.my.mdn.kupu.app.santri.entity.StatusSantri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import static id.my.mdn.kupu.core.base.view.widget.IValueList.SorterData.DESC;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "statusSantriList")
@Dependent
public class StatusSantriList extends AbstractMutablePagedValueList<StatusSantri> {

    @Inject
    private StatusSantriFacade dao;

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("fromDate", DESC));
    }

    @Override
    protected List<StatusSantri> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<StatusSantri> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters, 0L);
    }

    @Override
    protected void createInternal(StatusSantri entity) {
        dao.create(entity);
    }

    @Override
    public void edit(StatusSantri entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(StatusSantri entity) {
        dao.remove(entity);
    }

}
