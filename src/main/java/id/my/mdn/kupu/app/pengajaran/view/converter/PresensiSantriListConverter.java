/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.dao.PresensiSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent @FacesConverter(value = "PresensiSantriListConverter", managed = true)
public class PresensiSantriListConverter extends SelectionsConverter<PresensiSantri> {    
    
    @Inject
    private PresensiSantriFacade dao;

    @Override
    public PresensiSantri getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(PresensiSantri value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
