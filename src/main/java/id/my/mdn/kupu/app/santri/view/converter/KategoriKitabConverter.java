/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.KategoriKitabFacade;
import id.my.mdn.kupu.app.santri.entity.KategoriKitab;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "KategoriKitabConverter", managed = true)
public class KategoriKitabConverter implements Converter<KategoriKitab> {
    
    @Inject
    private KategoriKitabFacade dao;

    @Override
    public KategoriKitab getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, KategoriKitab value) {
        return value != null ? value.toString() : null;
    }    
    
}