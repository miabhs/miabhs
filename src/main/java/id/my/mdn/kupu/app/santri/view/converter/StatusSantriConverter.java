/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.StatusSantriFacade;
import id.my.mdn.kupu.app.santri.entity.StatusSantri;
import id.my.mdn.kupu.app.santri.entity.StatusSantriId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
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
@Singleton
@FacesConverter(managed = true, value = "StatusSantriConverter")
public class StatusSantriConverter implements Converter<StatusSantri> {
    
    @Inject
    private StatusSantriFacade dao;

    @Override
    public StatusSantri getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new StatusSantriId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, StatusSantri value) {
        return value != null ? value.toString() : null;
    }
    
}
