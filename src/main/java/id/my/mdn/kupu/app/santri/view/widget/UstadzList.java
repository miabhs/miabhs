/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.UstadzFacade;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutablePagedValueList;
import id.my.mdn.kupu.core.base.view.widget.CommonFilter;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "ustadzList")
@Dependent
public class UstadzList extends AbstractMutablePagedValueList<Ustadz> {
    
    @Inject
    private UstadzFacade dao;
    
    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {        
        filter.setContent(filterContent);
    }

    @Override
    protected List<Ustadz> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<Ustadz> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(Ustadz entity) {
        dao.create(entity);
    }

    @Override
    public void edit(Ustadz entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(Ustadz entity) {
        dao.remove(entity);
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_ustadz"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_ustadz"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_ustadz"};
    }
    
}
