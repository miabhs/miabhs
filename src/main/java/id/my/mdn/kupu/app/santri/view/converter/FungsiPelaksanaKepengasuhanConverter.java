/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.FungsiPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.FungsiPelaksanaKepengasuhan;
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
@FacesConverter(value = "FungsiPelaksanaKepengasuhanConverter", managed = true)
public class FungsiPelaksanaKepengasuhanConverter implements Converter<FungsiPelaksanaKepengasuhan> {

    @Inject
    private FungsiPelaksanaKepengasuhanFacade dao;

    @Override
    public FungsiPelaksanaKepengasuhan getAsObject(FacesContext context, UIComponent component, String value) {
        FungsiPelaksanaKepengasuhan ret = dao.find(KLong.valueOf(value));
        return ret;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FungsiPelaksanaKepengasuhan value) {
        return value != null ? value.toString() : null;
    }

}
