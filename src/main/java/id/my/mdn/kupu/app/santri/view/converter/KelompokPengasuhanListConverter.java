/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@FacesConverter(value = "KelompokPengasuhanListConverter", managed = true)
public class KelompokPengasuhanListConverter implements Converter<List<KelompokPengasuhan>> {
    
    @Inject
    private KelompokPengasuhanFacade dao;

    @Override
    public List<KelompokPengasuhan> getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            List<KelompokPengasuhan> objs = new ArrayList<>();

            String decodedValue = null;
            try {
                decodedValue = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(KelompokPengasuhanListConverter.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (decodedValue != null) {
                String[] ids = decodedValue.split("\\+");
                for (String id : ids) {
                    KelompokPengasuhan obj = dao.find(KLong.valueOf(id));
                    if (obj != null) {
                        objs.add(obj);
                    }
                }
            }

            return objs;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, List<KelompokPengasuhan> values) {
        if (values != null) {
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (KelompokPengasuhan value : values) {
                sb.append(value != null ? value.toString() : null);
                if (i < values.size()) {
                    sb.append("+");
                }
                i++;
            }

            try {
                String encodedString = URLEncoder.encode(sb.toString(), "UTF-8");
                return encodedString;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(KelompokPengasuhanListConverter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return "";
    }
    
}
