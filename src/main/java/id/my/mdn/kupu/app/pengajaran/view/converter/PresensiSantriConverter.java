/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.dao.PresensiSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent @FacesConverter(value = "PresensiSantriConverter", managed = true)
public class PresensiSantriConverter implements Converter<PresensiSantri> {
    
    @Inject
    private PresensiSantriFacade dao;

    @Override
    public PresensiSantri getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PresensiSantri value) {
        return value != null ? String.valueOf(value) : null;
    }    
    
}
