/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.view.converter.SantriConverter;
import id.my.mdn.kupu.app.santri.view.widget.SantriFilter;
import id.my.mdn.kupu.app.santri.view.widget.SantriList;
import id.my.mdn.kupu.core.base.view.SingleChooserPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import static id.my.mdn.kupu.core.base.view.widget.Selector.SINGLE;
import jakarta.annotation.PostConstruct;
import jakarta.faces.convert.Converter;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriSingleChooserPage")
@ViewScoped
public class SantriSingleChooserPage extends SingleChooserPage<Santri> implements Serializable {

    @Inject
    @Bookmarked
    private SantriList dataView;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        dataView.setSelectionMode(() -> SINGLE);
        dataView.getFilter()
                .<SantriFilter>getContent()
                .setStatusKesantrian(StatusKesantrian.ACTIVE);
        dataView.getFilter().setFiltering(true);
    }

    @Override
    protected Santri returns() {
        return dataView.getSelection();
    }

    @Override
    protected Class<? extends Converter<Santri>> getConverter() {
        return SantriConverter.class;
    }

    public SantriList getDataView() {
        return dataView;
    }

}
