/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view;

import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.view.converter.BentukAktifitasTambahanConverter;
import id.my.mdn.kupu.app.pengasuhan.view.widget.BentukAktifitasTambahanList;
import id.my.mdn.kupu.core.base.view.SingleChooserPage;
import jakarta.faces.convert.Converter;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "bentukAktifitasTambahanChooserPage")
@ViewScoped
public class BentukAktifitasTambahanChooserPage extends SingleChooserPage<BentukAktifitasTambahan> implements Serializable {
    
    @Inject
    private BentukAktifitasTambahanList dataView;

    @Override
    protected BentukAktifitasTambahan returns() {
        return dataView.getSelection();
    }

    @Override
    protected Class<? extends Converter<BentukAktifitasTambahan>> getConverter() {
        return BentukAktifitasTambahanConverter.class;
    }

    public BentukAktifitasTambahanList getDataView() {
        return dataView;
    }
    
}
