/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.AtributHasilPembelajaran;
import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaran;
import id.my.mdn.kupu.app.pengajaran.entity.AtributPembelajaranValue;
import id.my.mdn.kupu.app.pengajaran.entity.NilaiSetoran;
import id.my.mdn.kupu.app.pengajaran.entity.PencapaianBelajar;
import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantriId;
import id.my.mdn.kupu.app.pengajaran.entity.SetoranSantri;
import id.my.mdn.kupu.app.santri.dao.JenisKitabFacade;
import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Stateless
public class SetoranSantriFacade extends AbstractFacade<SetoranSantri> {

    @Inject
    private EntityManager em;    

    public SetoranSantriFacade() {
        super(SetoranSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void updateSetoran(SetoranSantri setoranSantri) {
        SetoranSantri existingSetoran = find(setoranSantri.getId());
        if (existingSetoran == null && !setoranSantri.getPencapaian().getSetoran().equals(NilaiSetoran.E)) {
            create(setoranSantri);
        } else if (existingSetoran != null && !setoranSantri.getPencapaian().getSetoran().equals(NilaiSetoran.E)) {
            remove(existingSetoran);
            getEntityManager().flush();
            create(setoranSantri);
        } else if (existingSetoran != null && setoranSantri.getPencapaian().getSetoran().equals(NilaiSetoran.E)) {
            remove(existingSetoran);
        } else {

        }
    }
    
    @Inject
    private JenisKitabFacade kitabFacade;

    public SetoranSantri getSetoran(PartyRelationshipId pengajaranId, Long idKitab, LocalDate date) {

        PengajaranSantriId id = new PengajaranSantriId(pengajaranId, date);

        SetoranSantri setoranSantri = find(id);

        if (setoranSantri == null) {
            setoranSantri = new SetoranSantri();
            setoranSantri.setId(id);

            PencapaianBelajar pencapaianBelajar = new PencapaianBelajar();
            pencapaianBelajar.setSantri(new Santri(id.getPengajaran().getToRole()));
            pencapaianBelajar.setJenisKitab(new JenisKitab(idKitab));
            pencapaianBelajar.setPengajaranDate(date);
            pencapaianBelajar.setSetoranPembelajaran(new ArrayList<>());

            setoranSantri.setPencapaian(pencapaianBelajar);
        }

        JenisKitab kitab = kitabFacade.find(idKitab);
        
        for (AtributPembelajaran ap : kitab.getListAtribut()) {
            AtributHasilPembelajaran ahpSetoran = new AtributHasilPembelajaran();
            ahpSetoran.setName(ap.getName());
            List<AtributHasilPembelajaran> listAtributHasilPembelajaran = setoranSantri.getPencapaian().getSetoranPembelajaran();
            if (!listAtributHasilPembelajaran.contains(ahpSetoran)) {
                ahpSetoran.setHasil(new AtributPembelajaranValue("", null, null));
                listAtributHasilPembelajaran.add(ahpSetoran);
            }
        }

        return setoranSantri;
    }

    public List<AtributPembelajaran> getAtributPembelajaranKitab(Long kitabId) {
        JenisKitab kitab = em.find(JenisKitab.class, kitabId);
        if (kitab != null) {
            return kitab.getListAtribut();
        } else {
            return new ArrayList<>();
        }
    }

}
