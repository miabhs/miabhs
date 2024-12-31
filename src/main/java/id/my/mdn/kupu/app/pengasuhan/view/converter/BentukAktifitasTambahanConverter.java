/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
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
@FacesConverter(value = "BentukAktifitasTambahanConverter", managed = true)
public class BentukAktifitasTambahanConverter implements Converter<BentukAktifitasTambahan> {

    @Inject
    private BentukAktifitasTambahanFacade dao;

    @Override
    public BentukAktifitasTambahan getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, BentukAktifitasTambahan value) {
        return value != null ? value.getId() : null;
    }

}
