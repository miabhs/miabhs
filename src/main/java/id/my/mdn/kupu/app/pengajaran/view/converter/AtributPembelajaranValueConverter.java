/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaranValue;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author aphasan
 */
@RequestScoped
@FacesConverter(value = "AtributPembelajaranValueConverter", managed = true)
public class AtributPembelajaranValueConverter implements Converter<AtributPembelajaranValue>{

    @Override
    public AtributPembelajaranValue getAsObject(FacesContext context, UIComponent component, String value) {
        String[] split = value.split("\\:");
        return value != null ? new AtributPembelajaranValue(value.split("\\:")) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AtributPembelajaranValue value) {
        return value != null ? value.toString() : null;
    }
    
}
