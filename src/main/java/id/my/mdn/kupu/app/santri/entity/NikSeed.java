/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_NIKSEED")
public class NikSeed implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private NikSeedId id;
    
    @MapsId("tahunMasuk")
    @ManyToOne
    private TahunPembelajaran tahunMasuk;
    
    private int nomorUrut;

    @Override
    public String toString() {
        return String.format("%s%s%d%03d%03d", 
                id.getJenisSantri().getKode(),
                id.getGender().getKode(),
                tahunMasuk.getFromDate().getYear(),
                id.getAngkatan(),
                nomorUrut
        ); 
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final NikSeed other = (NikSeed) obj;
        return Objects.equals(this.id, other.id);
    }

    public NikSeedId getId() {
        return id;
    }

    public void setId(NikSeedId id) {
        this.id = id;
    }

    public TahunPembelajaran getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(TahunPembelajaran tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public int getNomorUrut() {
        return nomorUrut;
    }

    public void setNomorUrut(int nomorUrut) {
        this.nomorUrut = nomorUrut;
    }
}
