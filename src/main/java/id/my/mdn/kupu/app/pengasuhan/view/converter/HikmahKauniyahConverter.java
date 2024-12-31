/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
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
@Singleton @FacesConverter(value = "HikmahKauniyahConverter", managed = true)
public class HikmahKauniyahConverter implements Converter<HikmahKauniyah> {

    @Inject
    private HikmahKauniyahFacade dao;

    @Override
    public HikmahKauniyah getAsObject(FacesContext context, UIComponent component, String value) {
        HikmahKauniyah find = dao.find(value);
        return find;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, HikmahKauniyah value) {
        return value != null ? value.getId() : null;
    }

}
