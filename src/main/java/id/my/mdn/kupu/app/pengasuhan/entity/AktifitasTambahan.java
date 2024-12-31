/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_AKTIFITASTAMBAHAN")
public class AktifitasTambahan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private AktifitasTambahanId id;
    
    @ManyToOne
    @MapsId("aktifitasHarian")
    private AktifitasHarian aktifitasHarian;
    
    @ManyToOne
    @MapsId("bentukAktifitas")
    private BentukAktifitasTambahan bentukAktifitas;
    
    @Lob
    private String masalah;
    
    private boolean confirmed;

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final AktifitasTambahan other = (AktifitasTambahan) obj;
        return Objects.equals(this.id, other.id);
    }

    public AktifitasTambahanId getId() {
        return id;
    }

    public void setId(AktifitasTambahanId id) {
        this.id = id;
    }

    public AktifitasHarian getAktifitasHarian() {
        return aktifitasHarian;
    }

    public void setAktifitasHarian(AktifitasHarian aktifitasHarian) {
        this.aktifitasHarian = aktifitasHarian;
    }

    public BentukAktifitasTambahan getBentukAktifitas() {
        return bentukAktifitas;
    }

    public void setBentukAktifitas(BentukAktifitasTambahan bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
    }

    public String getMasalah() {
        return masalah;
    }

    public void setMasalah(String masalah) {
        this.masalah = masalah;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    public String getKeterangan() {
        return bentukAktifitas.getBentuk();
    } 
    
}
