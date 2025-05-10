/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Embeddable
public class NikSeedId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long tahunMasuk;
    
    @Enumerated(EnumType.STRING)
    private JenisSantri jenisSantri;
    
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    
    private int angkatan;

    public NikSeedId() {
    }

    public NikSeedId(Long tahunMasuk, JenisSantri jenisSantri, GenderType gender, int angkatan) {
        this.tahunMasuk = tahunMasuk;
        this.jenisSantri = jenisSantri;
        this.gender = gender;
        this.angkatan = angkatan;
    }

    public JenisSantri getJenisSantri() {
        return jenisSantri;
    }

    public void setJenisSantri(JenisSantri jenisSantri) {
        this.jenisSantri = jenisSantri;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Long getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(Long tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public int getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(int angkatan) {
        this.angkatan = angkatan;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.tahunMasuk);
        hash = 53 * hash + Objects.hashCode(this.jenisSantri);
        hash = 53 * hash + Objects.hashCode(this.gender);
        hash = 53 * hash + this.angkatan;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NikSeedId other = (NikSeedId) obj;
        if (this.angkatan != other.angkatan) {
            return false;
        }
        if (!Objects.equals(this.tahunMasuk, other.tahunMasuk)) {
            return false;
        }
        if (this.jenisSantri != other.jenisSantri) {
            return false;
        }
        return this.gender == other.gender;
    }
    
}
