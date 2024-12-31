/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.entity.Status;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(managed = true, value = "StatusConverter")
public class StatusConverter implements Converter<Status> {

    @Override
    public Status getAsObject(FacesContext context, UIComponent component, String value) {
        return Status.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Status value) {
        return value != null ? value.name() : null;
    }

}
