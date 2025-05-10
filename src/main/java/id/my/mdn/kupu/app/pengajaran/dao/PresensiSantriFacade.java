/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.dao;

import id.my.mdn.kupu.app.pengajaran.entity.PengajaranSantriId;
import id.my.mdn.kupu.app.pengajaran.entity.Presensi;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PresensiSantriFacade extends AbstractFacade<PresensiSantri> {

    @Inject
    private EntityManager em;

    public PresensiSantriFacade() {
        super(PresensiSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void updatePresensi(PresensiSantri presensiSantri) {        
        
        PresensiSantri existingPresensi = find(presensiSantri.getId());

        if (existingPresensi == null && !presensiSantri.getPresensi().equals(Presensi.HADIR)) {
            create(presensiSantri);
        } else if (existingPresensi != null && !presensiSantri.getPresensi().equals(Presensi.HADIR)) {
            presensiSantri.setPresensi(presensiSantri.getPresensi());
            edit(presensiSantri);
        } else if (existingPresensi != null && presensiSantri.getPresensi().equals(Presensi.HADIR)) {
            remove(existingPresensi);
        } else {

        }
    }

    public PresensiSantri getPresensi(PartyRelationshipId pengajaranId, LocalDate date) {

        PengajaranSantriId id = new PengajaranSantriId(pengajaranId, date);

        PresensiSantri presensiSantri = find(id);

        if (presensiSantri == null) {
            presensiSantri = new PresensiSantri();
            presensiSantri.setId(id);
        }

        return presensiSantri;
    }
    
}
