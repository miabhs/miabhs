/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitas;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "BentukAktifitasListConverter", managed = true)
public class BentukAktifitasListConverter extends SelectionsConverter<BentukAktifitas> {
    
    @Inject
    private BentukAktifitasFacade dao;

    @Override
    public BentukAktifitas getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(BentukAktifitas value) {
        return value != null ? value.toString() : null;
    }
    
}
