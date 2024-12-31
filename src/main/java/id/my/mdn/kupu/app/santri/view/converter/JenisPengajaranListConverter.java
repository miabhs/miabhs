/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.JenisPengajaranFacade;
import id.my.mdn.kupu.app.santri.entity.JenisPengajaran;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "JenisPengajaranListConverter", managed = true)
public class JenisPengajaranListConverter extends SelectionsConverter<JenisPengajaran>{
    
    @Inject
    private JenisPengajaranFacade dao;

    @Override
    public JenisPengajaran getAsObject(String value) {
        return dao.find(KLong.valueOf(value));
    }

    @Override
    public String getAsString(JenisPengajaran value) {
        return value != null ? value.toString() : null;
    }    
    
    
}
