/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.view.converter.IslamicDateConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_AKTIFITASHARIAN")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "RangkumanAktifitas",
            classes = {
                @ConstructorResult(
                        targetClass = RangkumanAktifitas.class,
                        columns = {
                            @ColumnResult(name = "PARTYID", type = Long.class),
                            @ColumnResult(name = "SANTRIID", type = Long.class),
                            @ColumnResult(name = "FROMROLEID", type = Long.class),
                            @ColumnResult(name = "NAMA", type = String.class),
                            @ColumnResult(name = "JUMLAHDATA", type = Integer.class),
                            @ColumnResult(name = "JUMLAHKUNING", type = Integer.class),
                            @ColumnResult(name = "JUMLAHMERAH", type = Integer.class),
                            @ColumnResult(name = "NAMAKELOMPOK", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK", type = LocalDate.class),
                            @ColumnResult(name = "KOORDINATOR", type = Boolean.class)
                        }
                )
            }
    )
})
public class AktifitasHarian implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AktifitasHarianId id;

    @ManyToOne
    @MapsId("santri")
    private Santri santri;

//    @OneToMany(mappedBy = "aktifitasHarian", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
//    @OrderBy("bentukAktifitas")
//    private List<Aktifitas> listAktifitas;

    @OneToMany(mappedBy = "aktifitasHarian", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @OrderBy("bentukAktifitas")
    private List<AktifitasTambahan> listAktifitasTambahan;

    @Enumerated(EnumType.STRING)
    private Status statusPokok;

    @Enumerated(EnumType.STRING)
    private Status statusTambahan;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final AktifitasHarian other = (AktifitasHarian) obj;
        return Objects.equals(this.id, other.id);
    }

    public AktifitasHarianId getId() {
        return id;
    }

    public void setId(AktifitasHarianId id) {
        this.id = id;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

//    public List<Aktifitas> getListAktifitas() {
//        return listAktifitas;
//    }

//    public void setListAktifitas(List<Aktifitas> listAktifitas) {
//        this.listAktifitas = listAktifitas;
//    }

    public List<AktifitasTambahan> getListAktifitasTambahan() {
        return listAktifitasTambahan;
    }

    public void setListAktifitasTambahan(List<AktifitasTambahan> listAktifitasTambahan) {
        this.listAktifitasTambahan = listAktifitasTambahan;
    }

    public Status getStatusPokok() {
        return statusPokok;
    }

    public void setStatusPokok(Status statusPokok) {
        this.statusPokok = statusPokok;
    }

    public LocalDate getDate() {
        return id.getDate();
    }

    public void setDate(LocalDate date) {
        id.setDate(date);
    }

    public Status getStatusTambahan() {
        return statusTambahan;
    }

    public void setStatusTambahan(Status statusTambahan) {
        this.statusTambahan = statusTambahan;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    private static final IslamicDateConverter converter = new IslamicDateConverter();

    public String getIslamicDate() {
        return converter.getAsString(null, null, getDate());
    }

    public void changeStatus() {

    }
}
