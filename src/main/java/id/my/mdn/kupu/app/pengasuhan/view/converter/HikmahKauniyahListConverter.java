/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "HikmahKauniyahListConverter", managed = true)
public class HikmahKauniyahListConverter extends SelectionsConverter<HikmahKauniyah> {

    @Inject
    private HikmahKauniyahFacade dao;

    @Override
    public HikmahKauniyah getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    public String getAsString(HikmahKauniyah value) {
        return value != null ? value.getId() : null;
    }

}
