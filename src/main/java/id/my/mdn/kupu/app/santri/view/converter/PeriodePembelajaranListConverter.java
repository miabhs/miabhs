package id.my.mdn.kupu.app.santri.view.converter;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.view.converter.SelectionsConverter;
import jakarta.faces.convert.FacesConverter;
import id.my.mdn.kupu.core.common.util.K.KLong;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 *
 * @author aphasan
 */
@Singleton @FacesConverter(value = "PeriodePembelajaranListConverter", managed = true)
public class PeriodePembelajaranListConverter extends SelectionsConverter<PeriodePembelajaran> {

    @Inject
    private PeriodePembelajaranFacade dao;

    @Override
    public PeriodePembelajaran getAsObject(String string) {
        return dao.find(KLong.valueOf(string));
    }

    @Override
    public String getAsString(PeriodePembelajaran t) {
        return t != null ? t.toString() : null;
    }
}
