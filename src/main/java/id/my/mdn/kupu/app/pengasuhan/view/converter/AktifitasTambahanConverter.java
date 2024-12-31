/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahanId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named("AktifitasTambahanConverter")
@Dependent @FacesConverter(value = "AktifitasTambahanConverter", managed = true)
public class AktifitasTambahanConverter implements Converter<AktifitasTambahan> {

    @Inject
    private AktifitasTambahanFacade dao;

    @Override
    public AktifitasTambahan getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new AktifitasTambahanId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AktifitasTambahan value) {
        return value != null ? value.toString() : null;
    }

}
