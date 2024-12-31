/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.dao.PengajaranSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantri;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "PengajaranSantriConverter", managed = true)
public class PengajaranSantriConverter implements Converter<PengajaranSantri> {
    
    @Inject
    private PengajaranSantriFacade dao;

    @Override
    public PengajaranSantri getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PengajaranSantri value) {
        return value != null ? String.valueOf(value) : null;
    }    
    
}
