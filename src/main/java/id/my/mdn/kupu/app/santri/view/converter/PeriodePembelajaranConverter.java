package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton @FacesConverter(value = "PeriodePembelajaranConverter", managed = true)
public class PeriodePembelajaranConverter implements Converter<PeriodePembelajaran> {
    
    @Inject
    private PeriodePembelajaranFacade dao;

    @Override
    public PeriodePembelajaran getAsObject(FacesContext fc, UIComponent uic, String string) {
        return dao.find(KLong.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, PeriodePembelajaran t) {
        return t != null ? t.toString() : null;
    }
}
