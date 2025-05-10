/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.entity.JenisSantri;
import id.my.mdn.kupu.core.common.util.K.KEnum;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton 
@FacesConverter(value = "JenisSantriConverter", managed = true)
public class JenisSantriConverter implements Converter<JenisSantri>{

    @Override
    public JenisSantri getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(JenisSantri.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, JenisSantri value) {
        return value != null ? value.toString() : null;
    }
    
}
