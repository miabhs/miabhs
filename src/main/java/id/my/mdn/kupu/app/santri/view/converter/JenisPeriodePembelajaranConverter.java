/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.core.common.util.K.KEnum;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent @FacesConverter(value = "JenisPeriodePembelajaranConverter", managed = true)
public class JenisPeriodePembelajaranConverter implements Converter<JenisPeriodePembelajaran> {

    @Override
    public JenisPeriodePembelajaran getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(JenisPeriodePembelajaran.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, JenisPeriodePembelajaran value) {
        return value != null ? value.toString() : null;
    }
    
}
