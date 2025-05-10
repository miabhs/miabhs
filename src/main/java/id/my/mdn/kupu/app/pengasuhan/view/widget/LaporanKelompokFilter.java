/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.widget;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.view.widget.KelompokPengasuhanSelectList;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Named(value = "laporanKelompokFilter")
@Dependent
public class LaporanKelompokFilter extends FilterContent implements Serializable {

    @Bookmark(name = "fd")
    private LocalDate fromDate;

    @Bookmark(name = "td")
    private LocalDate thruDate;

    @Bookmark(name = "gd")
    private GenderType gender;

    @Bookmark(name = "kp")
    private KelompokPengasuhan kelompokPengasuhan;

    @Inject
    private KelompokPengasuhanSelectList kelompokPengasuhanChooser;

    @Inject
    private PeriodePembelajaranFacade periodeFacade;

    @PostConstruct
    public void init() {

        kelompokPengasuhanChooser.setFilters(() -> {
            if (gender == null) {
                return List.of();
            }
            return List.of(FilterData.by("gender", gender));
        });
        
        loadDefaultIfNeeded();

    }

    private void loadDefaultIfNeeded() {

        if (fromDate == null || thruDate == null || thruDate.isBefore(fromDate)) {

            LocalDate now = LocalDate.now();

            PeriodePembelajaran currentPeriodePembelajaran = periodeFacade.getPeriodePembelajaranMin(
                    now, JenisPeriodePembelajaran.PEKANAN
            );

            if (currentPeriodePembelajaran != null) {
                fromDate = currentPeriodePembelajaran.getFromDate();
                thruDate = currentPeriodePembelajaran.getThruDate();
            } else {
                DayOfWeek dayOfWeek = now.getDayOfWeek();
                fromDate = now.minusDays(dayOfWeek.getValue() - 1);
                thruDate = now.plusDays(7 - dayOfWeek.getValue());
            }
        }
        
    }

    public List<FilterData> santriFilter() {
        List<FilterData> filterSantri = new ArrayList<>();

        if (kelompokPengasuhan != null) {
            filterSantri.add(FilterData.by("kelompokPengasuhan", kelompokPengasuhan));
        }

        if (gender != null) {
            filterSantri.add(FilterData.by("gender", gender));
        }

        return filterSantri;
    }

    public void updateGenderFilter(AjaxBehaviorEvent evt) {
        kelompokPengasuhan = null;
    }

    public LocalDate getFromDate() {
        loadDefaultIfNeeded();
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        loadDefaultIfNeeded();
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public KelompokPengasuhanSelectList getKelompokPengasuhanChooser() {
        return kelompokPengasuhanChooser;
    }

}
