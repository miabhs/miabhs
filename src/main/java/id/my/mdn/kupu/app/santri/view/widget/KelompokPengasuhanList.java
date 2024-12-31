/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
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
@Named(value = "kelompokPengasuhanList")
@Dependent
public class KelompokPengasuhanList extends AbstractMutablePagedValueList<KelompokPengasuhan> {

    @Inject
    private KelompokPengasuhanFacade dao;

    @Inject
    private CommonFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<KelompokPengasuhan> getPagedFetchedItemsInternal(int first, int pageSize, Map<String, Object> parameters, List<FilterData> filters, List<SorterData> sorters, DefaultList<KelompokPengasuhan> defaultList, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(first, pageSize, parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(Map<String, Object> parameters, List<FilterData> filters, DefaultCount defaultCount, AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    protected void createInternal(KelompokPengasuhan entity) {
        dao.create(entity);
    }

    @Override
    public void edit(KelompokPengasuhan entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(KelompokPengasuhan entity) {
        dao.remove(entity);
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_kelompok_pengasuhan"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_kelompok_pengasuhan"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_kelompok_pengasuhan"};
    }

}
