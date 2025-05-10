/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Order;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Sort;
import id.my.mdn.kupu.core.base.view.annotation.SorterFields;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_AKTIFITAS")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "Aktifitas",
            classes = {
                @ConstructorResult(
                        targetClass = Aktifitas.class,
                        columns = {
                            @ColumnResult(name = "ID", type = String.class),
                            @ColumnResult(name = "CREATED", type = LocalDateTime.class),
                            @ColumnResult(name = "ACTIVITYDATE", type = LocalDate.class),

                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "SANTRI_NAME", type = String.class),
                            @ColumnResult(name = "SANTRI_GENDER", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHAN_NAME", type = String.class),

                            @ColumnResult(name = "BENTUKAKTIFITAS_ID", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_BENTUK", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_JENIS", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_NILAI", type = String.class),

                            @ColumnResult(name = "NOTES", type = String.class),
                            @ColumnResult(name = "CONFIRMED", type = Boolean.class)
                        }
                )
            }
    ),

    @SqlResultSetMapping(
            name = "CatatanHarian",
            classes = {
                @ConstructorResult(
                        targetClass = CatatanHarian.class,
                        columns = {
                            @ColumnResult(name = "ACTIVITYDATE", type = LocalDate.class),
                            @ColumnResult(name = "ID", type = String.class),
                            @ColumnResult(name = "KODE", type = String.class),
                            @ColumnResult(name = "JENIS", type = String.class),
                            @ColumnResult(name = "BENTUK", type = String.class),
                            @ColumnResult(name = "NILAI", type = String.class),
                            @ColumnResult(name = "NOTES", type = String.class)}
                )
            }
    ),

    @SqlResultSetMapping(
            name = "RangkumanKepengasuhan",
            classes = {
                @ConstructorResult(
                        targetClass = RangkumanKepengasuhan.class,
                        columns = {
                            @ColumnResult(name = "partyId", type = Long.class),
                            @ColumnResult(name = "firstname", type = String.class),
                            @ColumnResult(name = "lastname", type = String.class),
                            @ColumnResult(name = "santri_id", type = Long.class),
                            @ColumnResult(name = "nis", type = String.class),
                            @ColumnResult(name = "tahunMasukFromDate", type = LocalDate.class),
                            @ColumnResult(name = "kelompokPengasuhanId", type = Long.class),
                            @ColumnResult(name = "kelompokPengasuhanPartyName", type = String.class),
                            @ColumnResult(name = "koordinator", type = Boolean.class),
                            @ColumnResult(name = "label", type = String.class),
                            @ColumnResult(name = "fromDate", type = LocalDate.class),
                            @ColumnResult(name = "thruDate", type = LocalDate.class),
                            @ColumnResult(name = "bdas_merah", type = Integer.class),
                            @ColumnResult(name = "bdas_kuning", type = Integer.class),
                            @ColumnResult(name = "bdas", type = String.class),
                            @ColumnResult(name = "non_bdas_merah", type = Integer.class),
                            @ColumnResult(name = "non_bdas_kuning", type = Integer.class),
                            @ColumnResult(name = "non_bdas", type = String.class)}
                )
            }
    ),

    @SqlResultSetMapping(
            name = "RangkumanKepengasuhanDetail",
            classes = {
                @ConstructorResult(
                        targetClass = RangkumanKepengasuhanDetail.class,
                        columns = {
                            @ColumnResult(name = "santri_id", type = Long.class),
                            @ColumnResult(name = "catatan", type = String.class),
                            @ColumnResult(name = "jenis", type = String.class)}
                )
            }
    ),

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
@SorterFields({
    @SorterField(value = "santriName", label = "Name", sort = Sort.MANUAL),
    @SorterField(value = "activityDate", order = Order.DESC, sort = Sort.MANUAL, label = "Tanggal Aktifitas"),
    @SorterField(value = "created", order = Order.DESC, label = "Waktu Input")
})
public class Aktifitas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private LocalDate activityDate;

    @ManyToOne
    private Santri santri;

    @ManyToOne(fetch = FetchType.LAZY)
    private BentukAktifitas bentukAktifitas;

    
    private LocalDateTime created;

    private boolean confirmed;

    @Lob
    private String notes;

    @Transient 
    private String santriName;

    @Transient
    private GenderType santriGender;

    @Transient
    private String kelompokPengasuhanName;

    @Transient
    private String bentukAktifitasBentuk;

    @Transient
    private JenisAktifitas bentukAktifitasJenis;

    @Transient
    private NilaiAktifitas bentukAktifitasNilai;

    public Aktifitas() {
    }

    public Aktifitas(String id, LocalDateTime created, LocalDate activityDate,
            Long santriId, String santriName, String santriGender, String kelompokPengasuhanName,
            String bentukAktifitasId, String bentukAktifitasBentuk, String bentukAktifitasJenis, String bentukAktifitasNilai,
            String notes, Boolean confirmed) {

        this.id = id;
        this.created = created;
        this.activityDate = activityDate;

        Santri s = new Santri();
        s.setId(santriId);
        this.santri = s;

        this.santriName = santriName;

        if (santriGender != null) {
            this.santriGender = GenderType.valueOf(santriGender);
        }

        this.kelompokPengasuhanName = kelompokPengasuhanName;

        BentukAktifitas btk = new BentukAktifitas();
        btk.setId(bentukAktifitasId);
        this.bentukAktifitas = btk;
        this.bentukAktifitasBentuk = bentukAktifitasBentuk;
        this.bentukAktifitasJenis = bentukAktifitasJenis != null ? JenisAktifitas.valueOf(bentukAktifitasJenis) : null;
        this.bentukAktifitasNilai = bentukAktifitasNilai != null ? NilaiAktifitas.valueOf(bentukAktifitasNilai) : null;

        this.notes = notes;
        this.confirmed = confirmed;
    }

    public Aktifitas(
            String id, LocalDate activityDate, LocalDateTime created, String notes, Boolean confirmed,
            Long santriId, Long personId, String firstName, String lastName,
            Long kelompokPengasuhanId, Long kelompokPengasuhanOrganizationId, String kelompokPengasuhanOrganizationName,
            String bentukAktifitasId, String bentuk, String jenis, String nilai
    ) {

        this.id = id;
        this.activityDate = activityDate;
        this.created = created;
        this.notes = notes;
        this.confirmed = confirmed;

        // Santri
        if (santriId != null) {
            this.santri = new Santri();
            this.santri.setId(santriId);
            Person person = new Person();
            person.setId(personId);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            this.santri.setPerson(person);
        }

        // Kelompok Pengasuhan
        if (kelompokPengasuhanId != null) {
            KelompokPengasuhan kelompokPengasuhan = new KelompokPengasuhan();
            kelompokPengasuhan.setId(kelompokPengasuhanId);
            Organization organization = new Organization();
            organization.setId(kelompokPengasuhanOrganizationId);
            organization.setName(kelompokPengasuhanOrganizationName);
            kelompokPengasuhan.setOrganization(organization);
            this.santri.setKelompokPengasuhan(kelompokPengasuhan);

        }

        // Bentuk Aktifitas
        if (bentukAktifitasId != null && !bentukAktifitasId.isEmpty()) {
            this.bentukAktifitas = new BentukAktifitas();
            this.bentukAktifitas.setId(bentukAktifitasId);
            this.bentukAktifitas.setBentuk(bentuk);
            if (jenis != null) {
                this.bentukAktifitas.setJenis(JenisAktifitas.valueOf(jenis));
            }
            if (nilai != null) {
                this.bentukAktifitas.setNilai(NilaiAktifitas.valueOf(nilai));
            }
        } else {
            this.bentukAktifitas = null;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return santri != null ? santri.getKelompokPengasuhan() : null;
    }

    public JenisAktifitas getJenis() {
        return bentukAktifitas != null ? bentukAktifitas.getJenis() : null;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        created = LocalDateTime.now();
    }

    public String getLabel() {
        return new Formatter().format("%s", bentukAktifitas.getBentuk()).toString();
    }

    @Override
    public String toString() {
        return id;
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
        final Aktifitas other = (Aktifitas) obj;
        return Objects.equals(this.id, other.id);
    }

    public BentukAktifitas getBentukAktifitas() {
        return bentukAktifitas;
    }

    public void setBentukAktifitas(BentukAktifitas bentukAktifitas) {
        this.bentukAktifitas = bentukAktifitas;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSantriName() {
        return santriName;
    }

    public void setSantriName(String santriName) {
        this.santriName = santriName;
    }

    public String getKelompokPengasuhanName() {
        return kelompokPengasuhanName;
    }

    public void setKelompokPengasuhanName(String kelompokPengasuhanName) {
        this.kelompokPengasuhanName = kelompokPengasuhanName;
    }

    public void setBentukAktifitasBentuk(String bentukAktifitasBentuk) {
        this.bentukAktifitasBentuk = bentukAktifitasBentuk;
    }

    public void setBentukAktifitasJenis(JenisAktifitas bentukAktifitasJenis) {
        this.bentukAktifitasJenis = bentukAktifitasJenis;
    }

    public void setBentukAktifitasNilai(NilaiAktifitas bentukAktifitasNilai) {
        this.bentukAktifitasNilai = bentukAktifitasNilai;
    }

    public String getBentukAktifitasBentuk() {
        return bentukAktifitasBentuk;
    }

    public JenisAktifitas getBentukAktifitasJenis() {
        return bentukAktifitasJenis;
    }

    public NilaiAktifitas getBentukAktifitasNilai() {
        return bentukAktifitasNilai;
    }

    public GenderType getSantriGender() {
        return santriGender;
    }

}
