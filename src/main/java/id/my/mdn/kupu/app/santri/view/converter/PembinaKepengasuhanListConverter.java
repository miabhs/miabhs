/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.PembinaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.PembinaKepengasuhan;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "PembinaKepengasuhanListConverter", managed = true)
public class PembinaKepengasuhanListConverter extends SelectionsConverter<PembinaKepengasuhan> {
    
    @Inject
    private PembinaKepengasuhanFacade dao;

    @Override
    public PembinaKepengasuhan getAsObject(String value) {
        return dao.find(new PartyRelationshipId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString(PembinaKepengasuhan value) {
        return value != null ? String.valueOf(value) : null;
    }
    
}
