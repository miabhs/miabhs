/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahan;
import id.my.mdn.kupu.app.pengasuhan.entity.AktifitasTambahanId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named("AktifitasTambahanListConverter")
@Dependent @FacesConverter(value = "AktifitasTambahanListConverter", managed = true)
public class AktifitasTambahanListConverter extends SelectionsConverter<AktifitasTambahan> {
    
    @Inject
    private AktifitasTambahanFacade dao;

    @Override
    public AktifitasTambahan getAsObject(String value) {
        return dao.find(new AktifitasTambahanId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(AktifitasTambahan value) {
        return value != null ? value.toString() : null;
    }
    
}
