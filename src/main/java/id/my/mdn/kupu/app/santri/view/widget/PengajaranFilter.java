/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.widget;

import id.my.mdn.kupu.app.santri.dao.HalaqohPengajaranSqlFacade;
import id.my.mdn.kupu.app.santri.dao.TahunPembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.JenisPeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractFacade.DefaultChecker;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.view.annotation.Bookmark;
import id.my.mdn.kupu.core.base.view.widget.AbstractValueList;
import id.my.mdn.kupu.core.base.view.widget.FilterContent;
import id.my.mdn.kupu.core.base.view.widget.SorterData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Dependent
public class PengajaranFilter extends FilterContent implements Serializable {

    @Bookmark(name = "hp")
    private HalaqohPengajaran halaqohPengajaran;

    @Bookmark(name = "tp")
    private TahunPembelajaran tahunPembelajaran;

    @Bookmark(name = "mp")
    private PeriodePembelajaran bulanPembelajaran;

    @Bookmark(name = "pp")
    private PeriodePembelajaran periodePembelajaran;

    @Inject
    private HalaqohPengajaranSqlFacade halaqohFacade;

    @Inject
    private TahunPembelajaranFacade tahunFacade;

    @Inject
    private PeriodePembelajaranList bulanList;

    @Inject
    private PeriodePembelajaranList pekanList;

    private final AbstractValueList halaqohList = new AbstractValueList<>(HalaqohPengajaran.class) {
        @Override
        protected List<HalaqohPengajaran> getFetchedItemsInternal(
                Map<String, Object> parameters, List<FilterData> filters,
                List<SorterData> sorters, DefaultList<HalaqohPengajaran> defaultList,
                DefaultChecker defaultChecker) {
            return halaqohFacade.findAll();
        }
    };

    private final AbstractValueList tahunList = new AbstractValueList<>(TahunPembelajaran.class) {
        @Override
        protected List<TahunPembelajaran> getFetchedItemsInternal(
                Map<String, Object> parameters, List<FilterData> filters,
                List<SorterData> sorters, DefaultList<TahunPembelajaran> defaultList,
                DefaultChecker defaultChecker) {
            return tahunFacade.findAll();
        }
    };

    @PostConstruct
    private void init() {

        tahunList.getSelector().addListener(o -> bulanList.reset());

        bulanList.getFilter().setStaticFilter(this::bulanFilter);
        bulanList.getSelector().addListener(o -> pekanList.reset());

        pekanList.getFilter().setStaticFilter(this::pekanFilter);
    }

    private List<FilterData> bulanFilter() {
        return List.of(
                FilterData.by("tahunPembelajaran", tahunPembelajaran),
                FilterData.by("jenisPeriode", JenisPeriodePembelajaran.BULANAN)
        );
    }

    private List<FilterData> pekanFilter() {
        return bulanPembelajaran != null ? List.of(
                FilterData.by("geFromDate", bulanPembelajaran.getFromDate()),
                FilterData.by("leThruDate", bulanPembelajaran.getThruDate()),
                FilterData.by("jenisPeriode", JenisPeriodePembelajaran.PEKANAN)
        ) : List.of(
                FilterData.by("tahunPembelajaran", null),
                FilterData.by("jenisPeriode", JenisPeriodePembelajaran.BULANAN)
        );
    }

    public void onTahunChanged(AjaxBehaviorEvent evt) {
        bulanList.reset();
        pekanList.reset();
    }

    public void onBulanChanged(AjaxBehaviorEvent evt) {
        pekanList.reset();
    }

    public HalaqohPengajaran getHalaqohPengajaran() {
        return halaqohPengajaran;
    }

    public void setHalaqohPengajaran(HalaqohPengajaran halaqohPengajaran) {
        this.halaqohPengajaran = halaqohPengajaran;
    }

    public TahunPembelajaran getTahunPembelajaran() {
        return tahunPembelajaran;
    }

    public void setTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
        this.tahunPembelajaran = tahunPembelajaran;
    }

    public PeriodePembelajaran getBulanPembelajaran() {
        return bulanPembelajaran;
    }

    public void setBulanPembelajaran(PeriodePembelajaran bulanPembelajaran) {
        this.bulanPembelajaran = bulanPembelajaran;
    }

    public PeriodePembelajaran getPeriodePembelajaran() {
        return periodePembelajaran;
    }

    public void setPeriodePembelajaran(PeriodePembelajaran periodePembelajaran) {
        this.periodePembelajaran = periodePembelajaran;
    }

    public AbstractValueList getHalaqohList() {
        return halaqohList;
    }

    public AbstractValueList getTahunList() {
        return tahunList;
    }

    public PeriodePembelajaranList getBulanList() {
        return bulanList;
    }

    public PeriodePembelajaranList getPekanList() {
        return pekanList;
    }

}
