/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan;
import id.my.mdn.kupu.core.common.util.K.KEnum;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author aphasan
 */
@Dependent @FacesConverter(value = "FungsionalKepengasuhanConverter", managed = true)
public class FungsionalKepengasuhanConverter implements Converter<FungsionalKepengasuhan> {

    @Override
    public FungsionalKepengasuhan getAsObject(FacesContext context, UIComponent component, String value) {
        return KEnum.valueOf(FungsionalKepengasuhan.class, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FungsionalKepengasuhan value) {
        return value != null ? value.name() : null;
    }
    
}
