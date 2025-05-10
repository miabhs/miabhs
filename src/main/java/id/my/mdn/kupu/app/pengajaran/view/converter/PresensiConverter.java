/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.entity.Presensi;
import id.my.mdn.kupu.core.common.util.K.KEnum;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author aphasan
 */
@Dependent @FacesConverter(value = "PresensiConverter", managed = true)
public class PresensiConverter implements Converter<Presensi> {

    @Override
    public Presensi getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(Presensi.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Presensi value) {
        return value != null ? value.name() : null;
    }

   
    
}
