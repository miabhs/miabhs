/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.entity.RangkumanAktifitas;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton @FacesConverter(value = "RangkumanAktifitasListConverter", managed = true)
public class RangkumanAktifitasListConverter extends SelectionsConverter<RangkumanAktifitas> {

    @Override
    protected RangkumanAktifitas getAsObject(String value) {
        return new RangkumanAktifitas(KLong.valueOf(value));
    }

    @Override
    protected String getAsString(RangkumanAktifitas obj) {
        return obj != null ? String.valueOf(obj.getSantriId()) : null;
    }
    
}
