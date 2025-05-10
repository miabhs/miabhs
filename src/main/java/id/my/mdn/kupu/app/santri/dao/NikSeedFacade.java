/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.JenisSantri;
import id.my.mdn.kupu.app.santri.entity.NikSeed;
import id.my.mdn.kupu.app.santri.entity.NikSeedId;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Singleton
public class NikSeedFacade {

    @Inject
    private EntityManager em;

    @Inject
    private SantriFacade santriFacade;

    @Lock(LockType.WRITE)
    public String generateNis(Santri santri) {
        TahunPembelajaran tahunMasuk = santri.getTahunMasuk();
        JenisSantri jenisSantri = santri.getJenisSantri();
        GenderType gender = santri.getPerson().getGender();
        int angkatan = santri.getAngkatan();

        NikSeedId id = new NikSeedId(tahunMasuk.getId(), jenisSantri, gender, angkatan);

        NikSeed seed = em.find(NikSeed.class, id);

        if (seed != null) {
            seed.setNomorUrut(seed.getNomorUrut() + 1);
            em.merge(seed);
        } else {
            seed = new NikSeed();
            seed.setId(id);
            seed.setTahunMasuk(tahunMasuk);
            seed.setNomorUrut(1);
            em.persist(seed);
        }

        String nis = seed.toString();
        santri.setNis(nis);
        santriFacade.edit(santri);

        return nis;
    }
    
    @Lock(LockType.WRITE)
    public void generateNis(List<Santri> listSantri) {
        for(Santri santri : listSantri) {
            generateNis(santri);
        }
    }

}
