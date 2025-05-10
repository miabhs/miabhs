/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.view.converter;

import id.my.mdn.kupu.app.santri.dao.RangkumanPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.RangkumanPengajaran;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Dependent @FacesConverter(value = "RangkumanPengajaranConverter", managed = true)
public class RangkumanPengajaranConverter implements Converter<RangkumanPengajaran> {
    
    @Inject
    private RangkumanPengajaranFacade dao;

    @Override
    public RangkumanPengajaran getAsObject(FacesContext context, UIComponent component, String value) {
        return dao.find(new PartyRelationshipId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RangkumanPengajaran value) {
        return value != null ? value.toString() : null;
    }
    
}
