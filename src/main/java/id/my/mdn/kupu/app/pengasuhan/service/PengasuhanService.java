/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.service;

import id.my.mdn.kupu.app.santri.dao.PelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.PengasuhanFacade;
import id.my.mdn.kupu.app.santri.entity.FungsionalKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.app.santri.entity.PelaksanaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.base.util.QueryHelper;
import id.my.mdn.kupu.core.base.util.QueryParameter;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PengasuhanService {

    @Inject
    private EntityManager em;

    @Inject
    private PengasuhanFacade pengasuhanFacade;

    @Inject
    private PelaksanaKepengasuhanFacade pelaksanaKepengasuhanFacade;

    @Inject
    private QueryHelper helper;

    public void addSantri(KelompokPengasuhan kelompokPengasuhan, List<Santri> listSantri) {
        listSantri.stream().forEach(santri -> {
            List<FilterData> filters = new ArrayList<>();
            filters.add(new FilterData("fromRole", kelompokPengasuhan));
            filters.add(new FilterData("toRole", santri));
            filters.add(new FilterData("ongoing", null));

            Pengasuhan pengasuhan = pengasuhanFacade.findSingleByAttributes(filters);

            if (pengasuhan == null) {
                pengasuhan = new Pengasuhan();
                pengasuhan.setKelompokPengasuhan(kelompokPengasuhan);
                pengasuhan.setSantri(santri);
                pengasuhanFacade.create(pengasuhan);
            }
        });
    }

    public Ustadz getUstadzPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        Ustadz ustadz = helper.queryOne(em, "Pengasuhan.getUstadz", Ustadz.class,
                new QueryParameter("kelompokPengasuhan", kelompokPengasuhan));

        return ustadz;
    }

    public Musyrif getMusyrifPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        Musyrif musyrif = helper.queryOne(em, "Pengasuhan.getMusyrif", Musyrif.class,
                new QueryParameter("kelompokPengasuhan", kelompokPengasuhan));

        return musyrif;
    }

    public KelompokPengasuhan getKelompokPengasuhan(Long santriId, LocalDate date) {
        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("toRoleId", santriId));
        filters.add(new FilterData("ondate", date));

        List<Pengasuhan> listPengasuhan = pengasuhanFacade.findAll(filters);

        if (listPengasuhan.isEmpty() || listPengasuhan.size() > 1) {
            return null;
        } else {
            return listPengasuhan.get(0).getKelompokPengasuhan();
        }
    }

    public Ustadz getMasulKepengasuhan(KelompokPengasuhan kelompokPengasuhan, LocalDate date) {

        List<FilterData> filters = new ArrayList<>();
        filters.add(new FilterData("toRole", kelompokPengasuhan));
        filters.add(new FilterData("ondate", date));
        filters.add(new FilterData("fungsi", List.of(FungsionalKepengasuhan.MASUL_KEPENGASUHAN)));

        List<PelaksanaKepengasuhan> listPembantuPelaksanaKepengasuhan = pelaksanaKepengasuhanFacade.findAll(filters);

        if (listPembantuPelaksanaKepengasuhan.isEmpty() || listPembantuPelaksanaKepengasuhan.size() > 1) {
            return null;
        } else {
            return listPembantuPelaksanaKepengasuhan.get(0).getUstadz();
        }

    }
}
