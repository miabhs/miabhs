/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
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
                            @ColumnResult(name = "ACTIVITYDATE", type = LocalDate.class),
                            @ColumnResult(name = "CREATED", type = LocalDateTime.class),
                            @ColumnResult(name = "NOTES", type = String.class),
                            @ColumnResult(name = "CONFIRMED", type = Boolean.class),

                            @ColumnResult(name = "SANTRIID", type = Long.class),
                            @ColumnResult(name = "PERSONID", type = Long.class),
                            @ColumnResult(name = "FIRSTNAME", type = String.class),
                            @ColumnResult(name = "LASTNAME", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHANID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANORGANIZATIONID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANORGANIZATIONNAME", type = String.class),

                            @ColumnResult(name = "BENTUKAKTIFITASID", type = String.class),
                            @ColumnResult(name = "BENTUK", type = String.class),
                            @ColumnResult(name = "JENIS", type = String.class),
                            @ColumnResult(name = "NILAI", type = String.class)}
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
                            @ColumnResult(name = "kelompokPengasuhanPartyName", type = String.class),
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
    )
})
public class Aktifitas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToOne
    private Santri santri;

    private LocalDate activityDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private BentukAktifitas bentukAktifitas;

    private LocalDateTime created;

    private boolean confirmed;

    @Lob
    private String notes;

    public Aktifitas() {
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

}
