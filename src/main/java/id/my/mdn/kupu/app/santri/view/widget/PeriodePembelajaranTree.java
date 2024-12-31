/*
 * The MIT License
 *
 * Copyright 2023 Arief Prihasanto <aphasan at mdnx.dev>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.widget.AbstractMutableTree;
import id.my.mdn.kupu.core.base.view.widget.AbstractPagedValueList.DefaultCount;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "periodePengasuhanTree")
@Dependent
public class PeriodePembelajaranTree extends AbstractMutableTree<PeriodePembelajaran> {

    @Inject
    private PeriodePembelajaranFacade dao;

    @Inject
    private PeriodePembelajaranFilter filterContent;

    @PostConstruct
    public void init() {
        filter.setContent(filterContent);
    }

    @Override
    protected List<PeriodePembelajaran> getFetchedItemsInternal(
            Map<String, Object> parameters,
            List<FilterData> filters, List<SorterData> sorters,
            DefaultList<PeriodePembelajaran> defaultReturn,
            AbstractFacade.DefaultChecker defaultChecker) {
        return dao.findAll(parameters, filters, sorters);
    }

    @Override
    protected long getItemsCountInternal(
            Map<String, Object> parameters,
            List<FilterData> filters,
            DefaultCount defaultCount,
            AbstractFacade.DefaultChecker defaultChecker) {
        return dao.countAll(parameters, filters);
    }

    @Override
    public List<SorterData> getSorters() {
        return Arrays.asList(new SorterData("id.fromDate"));
    }

    @Override
    protected void createInternal(PeriodePembelajaran entity) {
        dao.create(entity);
    }

    @Override
    public void edit(PeriodePembelajaran entity) {
        dao.edit(entity);
    }

    @Override
    protected void deleteInternal(PeriodePembelajaran entity) {
        dao.remove(entity);
    }

    public List<PeriodePembelajaran> getPeriode() {
        return dao.getChildren(getSelection());
    }

    @Override
    public String[] getCreatePermission() {
        return new String[]{"create_periode_pembelajaran"};
    }

    @Override
    public String[] getUpdatePermission() {
        return new String[]{"update_periode_pembelajaran"};
    }

    @Override
    public String[] getDeletePermission() {
        return new String[]{"delete_periode_pembelajaran"};
    }

}
