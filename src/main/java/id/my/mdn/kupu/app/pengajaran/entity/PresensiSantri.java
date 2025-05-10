/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.Pengajaran;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PRESENSISANTRI")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "PresensiSantri",
            classes = {
                @ConstructorResult(
                        targetClass = PresensiSantri.class,
                        columns = {
                            @ColumnResult(name = "id", type = String.class),
                            @ColumnResult(name = "pengajaranDate", type = LocalDate.class),
                            @ColumnResult(name = "created", type = LocalDateTime.class),
                            @ColumnResult(name = "notes", type = String.class),

                            @ColumnResult(name = "santriId", type = Long.class),
                            @ColumnResult(name = "personId", type = Long.class),
                            @ColumnResult(name = "firstName", type = String.class),
                            @ColumnResult(name = "lastName", type = String.class),

                            @ColumnResult(name = "kelompokPengasuhanId", type = Long.class),
                            @ColumnResult(name = "kelompokPengasuhanOrganizationId", type = Long.class),
                            @ColumnResult(name = "kelompokPengasuhanOrganizationName", type = String.class),

                            @ColumnResult(name = "jenisKitabId", type = Long.class),
                            @ColumnResult(name = "judul", type = String.class)}
                )
            }
    ),
    @SqlResultSetMapping(
            name = "PencapaianBelajar",
            classes = {
                @ConstructorResult(
                        targetClass = PencapaianBelajar.class,
                        columns = {
                            @ColumnResult(name = "JENISPENGAJARANID", type = Long.class),
                            @ColumnResult(name = "JENISPENGAJARANKODE", type = String.class),
                            @ColumnResult(name = "JENISPENGAJARANNAMA", type = String.class),

                            @ColumnResult(name = "JENISKITABID", type = Long.class),
                            @ColumnResult(name = "JENISKITABJUDUL", type = String.class),
                            
                            @ColumnResult(name = "CAPAIAN", type = String.class)}
                )
            }
    )
})
@SorterField(value = "pengajaranDate", order = SorterField.Order.DESC, label = "Tanggal Pembelajaran")
public class PresensiSantri implements Serializable {

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
    
    @Enumerated(EnumType.STRING)
    private Presensi presensi;

    private String notes;

    @SorterField(value = "CREATED", order = SorterField.Order.DESC, label = "Tanggal Input")
    private LocalDateTime created;

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final PresensiSantri other = (PresensiSantri) obj;
        return Objects.equals(this.id, other.id);
    }

    @PrePersist
    private void prePersist() {
        created = LocalDateTime.now();
    }

    public PengajaranSantriId getId() {
        return id;
    }

    public Pengajaran getPengajaran() {
        return (Pengajaran) pengajaran;
    }

    public void setId(PengajaranSantriId id) {
        this.id = id;
    }

    public LocalDate getPengajaranDate() {
        return id.getPengajaranDate();
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        id.setPengajaranDate(pengajaranDate);
    }

    public Santri getSantri() {
        return getPengajaran().getSantri();
    }

    public void setSantri(Santri santri) {
        getPengajaran().setSantri(santri);
    }

    public Presensi getPresensi() {
        return presensi;
    }

    public void setPresensi(Presensi presensi) {
        this.presensi = presensi;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

}
