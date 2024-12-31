/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.PersonalAttributeFacade;
import id.my.mdn.kupu.app.santri.entity.PersonalAttribute;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "PersonalAttributeConverter", managed = true)
public class PersonalAttributeConverter implements Converter<PersonalAttribute> {

    @Inject
    private PersonalAttributeFacade dao;

    @Override
    public PersonalAttribute getAsObject(FacesContext context, UIComponent component, String value) {
        PersonalAttribute ret = dao.find(KLong.valueOf(value));
        return ret;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PersonalAttribute value) {
        return value != null ? value.toString() : null;
    }

}
