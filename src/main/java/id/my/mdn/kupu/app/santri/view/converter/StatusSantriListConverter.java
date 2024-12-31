/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.StatusSantriFacade;
import id.my.mdn.kupu.app.santri.entity.StatusSantri;
import id.my.mdn.kupu.app.santri.entity.StatusSantriId;
import id.my.mdn.kupu.core.base.util.EntityUtil;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "StatusSantriListConverter", managed = true)
public class StatusSantriListConverter extends SelectionsConverter<StatusSantri> {
    
    @Inject
    private StatusSantriFacade dao;

    @Override
    public StatusSantri getAsObject(String value) {
        return dao.find(new StatusSantriId(EntityUtil.parseCompositeId(value)));
    }

    @Override
    public String getAsString( StatusSantri value) {
        return value != null ? value.toString() : null;
    }
    
}
