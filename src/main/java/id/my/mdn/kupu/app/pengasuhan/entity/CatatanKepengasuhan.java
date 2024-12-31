/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
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
@Table(name = "MIABH_CATATANKEPENGASUHAN")
public class CatatanKepengasuhan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private CatatanKepengasuhanId id;
    
    @MapsId("santri")
    @ManyToOne
    private Santri santri;
    
    @MapsId("periodePembelajaran")
    @ManyToOne
    private PeriodePembelajaran periodePembelajaran;
    
    @Lob
    private String catatanPengasuh;
    
    @Lob
    private String catatanMasul;

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final CatatanKepengasuhan other = (CatatanKepengasuhan) obj;
        return Objects.equals(this.id, other.id);
    }

    public CatatanKepengasuhanId getId() {
        return id;
    }

    public void setId(CatatanKepengasuhanId id) {
        this.id = id;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public PeriodePembelajaran getPeriodePembelajaran() {
        return periodePembelajaran;
    }

    public void setPeriodePembelajaran(PeriodePembelajaran periodePembelajaran) {
        this.periodePembelajaran = periodePembelajaran;
    }

    public String getCatatanPengasuh() {
        return catatanPengasuh;
    }

    public void setCatatanPengasuh(String catatanPengasuh) {
        this.catatanPengasuh = catatanPengasuh;
    }

    public String getCatatanMasul() {
        return catatanMasul;
    }

    public void setCatatanMasul(String catatanMasul) {
        this.catatanMasul = catatanMasul;
    }
    
}
