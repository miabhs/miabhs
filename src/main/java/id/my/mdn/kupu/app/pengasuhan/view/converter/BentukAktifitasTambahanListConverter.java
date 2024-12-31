/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.converter;

import id.my.mdn.kupu.app.pengasuhan.dao.BentukAktifitasTambahanFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.BentukAktifitasTambahan;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Singleton
@FacesConverter(value = "BentukAktifitasTambahanListConverter", managed = true)
public class BentukAktifitasTambahanListConverter extends SelectionsConverter<BentukAktifitasTambahan> {
    
    @Inject
    private BentukAktifitasTambahanFacade dao;

    @Override
    protected BentukAktifitasTambahan getAsObject(String value) {
        return dao.find(value);
    }

    @Override
    protected String getAsString(BentukAktifitasTambahan obj) {
        return obj.getId();
    }

//    @Override
//    public List<BentukAktifitasTambahan> getAsObject(String value) {
//        if (value != null && !value.isEmpty()) {
//            List<BentukAktifitasTambahan> objs = new ArrayList<>();
//
//            String decodedValue = null;
//            try {
//                decodedValue = URLDecoder.decode(value, "UTF-8");
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(BentukAktifitasTambahanListConverter.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            if (decodedValue != null) {
//                String[] ids = decodedValue.split("\\+");
//                for (String id : ids) {
//                    BentukAktifitasTambahan obj = dao.find(id);
//                    if (obj != null) {
//                        objs.add(obj);
//                    }
//                }
//            }
//
//            return objs;
//        } else {
//            return new ArrayList<>();
//        }
//    }
//
//    @Override
//    public String getAsString(List<BentukAktifitasTambahan> values) {
//        if (values != null) {
//            StringBuilder sb = new StringBuilder();
//            int i = 1;
//            for (BentukAktifitasTambahan value : values) {
//                sb.append(value != null ? value.toString() : null);
//                if (i < values.size()) {
//                    sb.append("+");
//                }
//                i++;
//            }
//
//            try {
//                String encodedString = URLEncoder.encode(sb.toString(), "UTF-8");
//                return encodedString;
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(BentukAktifitasTambahanListConverter.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//        return "";
//    }
    
}
