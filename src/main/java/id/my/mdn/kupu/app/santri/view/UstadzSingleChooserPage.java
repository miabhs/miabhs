/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view;

import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.app.santri.view.converter.UstadzConverter;
import id.my.mdn.kupu.app.santri.view.widget.UstadzList;
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
@Named(value = "ustadzSingleChooserPage")
@ViewScoped
public class UstadzSingleChooserPage extends SingleChooserPage<Ustadz> implements Serializable {

    @Inject
    @Bookmarked
    private UstadzList dataView;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        dataView.setSelectionMode(() -> SINGLE);
    }

    @Override
    protected Ustadz returns() {
        return dataView.getSelection();
    }

    @Override
    protected Class<? extends Converter<Ustadz>> getConverter() {
        return UstadzConverter.class;
    }

    public UstadzList getDataView() {
        return dataView;
    }
    
}
