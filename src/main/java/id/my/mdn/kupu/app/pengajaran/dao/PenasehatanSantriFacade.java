/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.PenasehatanSantri;
import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantriId;
import id.my.mdn.kupu.app.pengasuhan.entity.JenisPengasuhan;
import id.my.mdn.kupu.app.pengasuhan.entity.PengasuhanSantri;
import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

/**
 *
 * @author aphasan
 */
@Stateless
public class PenasehatanSantriFacade extends AbstractFacade<PenasehatanSantri> {

    @Inject
    private EntityManager em;

    public PenasehatanSantriFacade() {
        super(PenasehatanSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void saveNasehat(PartyRelationshipId pengajaranId, LocalDate date, HalaqohPengajaran halaqohPengajaran) {

        PengajaranSantriId id = new PengajaranSantriId(pengajaranId, date);

        PenasehatanSantri penasehatanSantri = find(id);

        if (penasehatanSantri == null) {
            penasehatanSantri = new PenasehatanSantri();
            penasehatanSantri.setId(id);

            PengasuhanSantri pengasuhanSantri = new PengasuhanSantri();

            Ustadz pengampu = new Ustadz();
            pengampu.setId(halaqohPengajaran.getPengampuId());
            pengasuhanSantri.setUstadz(pengampu);

            Santri santri = new Santri();
            santri.setId(pengajaranId.getToRole());
            pengasuhanSantri.setSantri(santri);
            
            pengasuhanSantri.setJenis(JenisPengasuhan.NASEHAT);
            pengasuhanSantri.setPengasuhanDate(date);

            penasehatanSantri.setPengasuhan(pengasuhanSantri);

            create(penasehatanSantri);

        } else {
            remove(penasehatanSantri);
        }

    }

}
