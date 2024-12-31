/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.Aktifitas;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "AktifitasListConverter", managed = true)
public class AktifitasListConverter extends SelectionsConverter<Aktifitas> {
    
    @Inject
    private AktifitasFacade dao;

    @Override
    public Aktifitas getAsObject(String value) {
        Aktifitas a = dao.find(value);
        return a;
    }

    @Override
    public String getAsString(Aktifitas value) {
        return value != null ? value.toString() : null;
    }
    
}
