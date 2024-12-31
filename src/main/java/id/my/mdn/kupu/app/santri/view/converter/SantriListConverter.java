/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "SantriListConverter", managed = true)
public class SantriListConverter extends SelectionsConverter<Santri> {
    
    @Inject
    private SantriFacade dao;

    @Override
    public Santri getAsObject(String value) {
        Santri santri = dao.find(KLong.valueOf(value));
        return santri;
    }

    @Override
    public String getAsString(Santri value) {
        String ret = value != null ? value.toString() : null;
        return ret;
    }
    
}
