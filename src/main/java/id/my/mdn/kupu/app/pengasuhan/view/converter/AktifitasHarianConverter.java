/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasHarianFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarian;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasHarianId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named("AktifitasHarianConverter")
@Singleton @FacesConverter(value = "AktifitasHarianConverter", managed = true)
public class AktifitasHarianConverter implements Converter<AktifitasHarian> {

    @Inject
    private AktifitasHarianFacade dao;

    @Override
    public AktifitasHarian getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new AktifitasHarianId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AktifitasHarian value) {
        return value != null ? value.toString() : null;
    }

}
