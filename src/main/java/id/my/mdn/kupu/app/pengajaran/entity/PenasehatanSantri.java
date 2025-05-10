/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.pengasuhan.entity.PengasuhanSantri;
import id.my.mdn.kupu.app.santri.entity.Pengajaran;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_PENASEHATANSANTRI")
public class PenasehatanSantri implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "pengajaran.fromRole", column = @Column(name = "PENGAJARAN_FROMROLE_ID")),
        @AttributeOverride(name = "pengajaran.toRole", column = @Column(name = "PENGAJARAN_TOROLE_ID")),
        @AttributeOverride(name = "pengajaran.fromDate", column = @Column(name = "PENGAJARAN_FROMDATE")),
        @AttributeOverride(name = "pengajaran.partyRelationshipType", column = @Column(name = "PENGAJARAN_PARTYRELATIONSHIPTYPE_ID"))
    })
    private PengajaranSantriId id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PENGAJARAN_FROMROLE_ID", referencedColumnName = "FROMROLE_ID", insertable = false, updatable = false),
        @JoinColumn(name = "PENGAJARAN_TOROLE_ID", referencedColumnName = "TOROLE_ID", insertable = false, updatable = false),
        @JoinColumn(name = "PENGAJARAN_FROMDATE", referencedColumnName = "FROMDATE", insertable = false, updatable = false),
        @JoinColumn(name = "PENGAJARAN_PARTYRELATIONSHIPTYPE_ID", referencedColumnName = "PARTYRELATIONSHIPTYPE_ID", insertable = false, updatable = false)
    })
    private Pengajaran pengajaran;
    
    @OneToOne(cascade = CascadeType.ALL)
    private PengasuhanSantri pengasuhan;

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final PenasehatanSantri other = (PenasehatanSantri) obj;
        return Objects.equals(this.id, other.id);
    }

    public PengajaranSantriId getId() {
        return id;
    }

    public void setId(PengajaranSantriId id) {
        this.id = id;
    }

    public Pengajaran getPengajaran() {
        return pengajaran;
    }

    public void setPengajaran(Pengajaran pengajaran) {
        this.pengajaran = pengajaran;
    }

    public PengasuhanSantri getPengasuhan() {
        return pengasuhan;
    }

    public void setPengasuhan(PengasuhanSantri pengasuhan) {
        this.pengasuhan = pengasuhan;
    }
    
}
