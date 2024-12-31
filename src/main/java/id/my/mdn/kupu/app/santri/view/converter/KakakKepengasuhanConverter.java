/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.KakakKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KakakKepengasuhan;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "KakakKepengasuhanConverter", managed = true)
public class KakakKepengasuhanConverter implements Converter<KakakKepengasuhan> {
    
    @Inject
    private KakakKepengasuhanFacade dao;

    @Override
    public KakakKepengasuhan getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new PartyRelationshipId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, KakakKepengasuhan value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
