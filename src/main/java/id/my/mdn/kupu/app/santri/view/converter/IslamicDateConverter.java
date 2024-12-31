/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "IslamicDateConverter", managed = true)
public class IslamicDateConverter implements Converter<LocalDate> {
    
    private final Locale locale = new Locale("ID");

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale);
        value = value.replace("Ahad", "Minggu");
        return LocalDate.parse(value, formatter);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        if(value == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale);
        return value.format(formatter).replace("Minggu", "Ahad");
    }

    public Locale getLocale() {
        return locale;
    }
    
}
