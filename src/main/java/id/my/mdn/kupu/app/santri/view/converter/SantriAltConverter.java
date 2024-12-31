/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "SantriAltConverter", managed = true)
public class SantriAltConverter implements Converter<Santri> {
    
    @Inject
    private SantriFacade dao;

    @Override
    public Santri getAsObject(FacesContext context, UIComponent component, String value) {
        return StringUtils.isNumeric(value) ? dao.find(KLong.valueOf(value)) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Santri value) {
        return value != null ? value.toString() : null;
    }
    
}
