/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.pengajaran.dao.PengajaranSantriFacade;
import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantri;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "PengajaranSantriListConverter", managed = true)
public class PengajaranSantriListConverter extends SelectionsConverter<PengajaranSantri> {    
    
    @Inject
    private PengajaranSantriFacade dao;

    @Override
    public PengajaranSantri getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(PengajaranSantri value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
