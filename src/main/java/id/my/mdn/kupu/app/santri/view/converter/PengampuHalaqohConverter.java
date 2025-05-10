/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.PengampuHalaqohFacade;
import id.my.mdn.kupu.app.santri.entity.PengampuHalaqoh;
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
@FacesConverter(value = "PengampuHalaqohConverter", managed = true)
public class PengampuHalaqohConverter implements Converter<PengampuHalaqoh> {
    
    @Inject
    private PengampuHalaqohFacade dao;

    @Override
    public PengampuHalaqoh getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new PartyRelationshipId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PengampuHalaqoh value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
