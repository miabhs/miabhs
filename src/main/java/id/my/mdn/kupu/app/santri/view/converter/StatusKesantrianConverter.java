/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.core.common.util.K.KEnum;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "StatusKesantrianConverter", managed = true)
public class StatusKesantrianConverter implements Converter<StatusKesantrian> {

    @Override
    public StatusKesantrian getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(StatusKesantrian.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, StatusKesantrian value) {
        return value != null ? value.toString() : null;
    }
    
}
