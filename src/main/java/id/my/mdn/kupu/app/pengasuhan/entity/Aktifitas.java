/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Order;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Sort;
import id.my.mdn.kupu.core.base.view.annotation.SorterFields;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.security.model.ApplicationUser;
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
import java.util.ArrayList;
import java.util.List;
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

                            @ColumnResult(name = "CONFIRMED", type = Boolean.class),

                            @ColumnResult(name = "CREATED", type = LocalDateTime.class),
                            @ColumnResult(name = "CREATOR_ID", type = Long.class),
                            @ColumnResult(name = "LASTMODIFIED", type = LocalDateTime.class),
                            @ColumnResult(name = "LASTMODIFIER_ID", type = Long.class),
                            @ColumnResult(name = "NOTES", type = String.class),

                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "SANTRI_PERSON_ID", type = Long.class),
                            @ColumnResult(name = "SANTRI_NAME", type = String.class),
                            @ColumnResult(name = "SANTRI_GENDER", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ORG_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_NAME", type = String.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "BENTUKAKTIFITAS_ID", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_BENTUK", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_JENIS", type = String.class),
                            @ColumnResult(name = "BENTUKAKTIFITAS_NILAI", type = String.class),}
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
                            @ColumnResult(name = "PERSON_ID", type = Long.class),
                            @ColumnResult(name = "PERSON_NAME", type = String.class),
                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "NIS", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_FROMDATE", type = LocalDate.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_NAME", type = String.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_KOORDINATOR", type = Boolean.class),
                            @ColumnResult(name = "LABEL", type = String.class),
                            @ColumnResult(name = "FROMDATE", type = LocalDate.class),
                            @ColumnResult(name = "THRUDATE", type = LocalDate.class),
                            @ColumnResult(name = "BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "BDAS_KUNING", type = Integer.class),
                            @ColumnResult(name = "BDAS", type = String.class),
                            @ColumnResult(name = "NON_BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS_KUNING", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS", type = String.class)}
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

    private boolean confirmed;

    @Lob
    private String notes;

    private LocalDateTime created;

    @ManyToOne
    private ApplicationUser creator;

    private LocalDateTime lastModified;

    @ManyToOne
    private ApplicationUser lastModifier;

    public Aktifitas() {
    }

    public Aktifitas(String id) {
        setId(id);
    }

    public Aktifitas(String id, LocalDate activityDate,
            Boolean confirmed, LocalDateTime created,
            Long creatorId, LocalDateTime lastModified, Long lastModifier, String notes,
            Long santriId, Long santriPersonId, String santriName, String santriGender,
            Long kelompokPengasuhanId, Long kelompokPengasuhanOrgId, String kelompokPengasuhanName, LocalDate kelompokPengasuhanPengasuhanFromDate,
            String bentukAktifitasId, String bentukAktifitasBentuk, String bentukAktifitasJenis, String bentukAktifitasNilai) {

        this.id = id;
        this.activityDate = activityDate;

        this.notes = notes;
        this.confirmed = confirmed;

        this.created = created;
        this.creator = new ApplicationUser();
        this.creator.setId(creatorId);

        this.lastModified = lastModified;
        this.lastModifier = new ApplicationUser();
        this.lastModifier.setId(lastModifier);

        if (santriId != null) {
            this.santri = Santri.builder()
                    .withPerson(
                            Person.builder()
                                    .firstName(santriName)
                                    .gender(santriGender != null ? GenderType.valueOf(santriGender) : null)
                                    .get())
                    .get();
            this.santri.setId(santriId);
            this.santri.getPerson().setId(santriPersonId);
            this.santri.setTargetRelationships(new ArrayList<>());
        }

        if (kelompokPengasuhanId != null) {
            KelompokPengasuhan kelompokPengasuhan = KelompokPengasuhan.builder().withOrganization(new Organization()).get();
            kelompokPengasuhan.setId(kelompokPengasuhanId);
            kelompokPengasuhan.getOrganization().setId(kelompokPengasuhanOrgId);
            kelompokPengasuhan.getOrganization().setName(kelompokPengasuhanName);

            Pengasuhan pengasuhan = Pengasuhan.builder().from(kelompokPengasuhan).to(this.santri).get();
            pengasuhan.setId(new PartyRelationshipId(kelompokPengasuhanId, santriId, kelompokPengasuhanPengasuhanFromDate, "Pengasuhan"));

            this.santri.getTargetRelationships().add(pengasuhan);
        }

        if (bentukAktifitasId != null) {
            this.bentukAktifitas = new BentukAktifitas();
            this.bentukAktifitas.setId(bentukAktifitasId);
            this.bentukAktifitas.setNilai(bentukAktifitasNilai != null ? NilaiAktifitas.valueOf(bentukAktifitasNilai) : null);
            this.bentukAktifitas.setBentuk(bentukAktifitasBentuk);
            this.bentukAktifitas.setJenis(bentukAktifitasJenis != null ? JenisAktifitas.valueOf(bentukAktifitasJenis) : null);
        }
    }

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        created = LocalDateTime.now();
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

    public KelompokPengasuhan getKelompokPengasuhan() {
        if (this.santri == null) {
            return null;
        }
        List<PartyRelationship> targetRelationships = this.santri.getTargetRelationships();
        if (!targetRelationships.isEmpty() && targetRelationships.size() == 1) {
            return (KelompokPengasuhan) targetRelationships.get(0).getFromRole();
        } else {
            return null;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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

    public ApplicationUser getCreator() {
        return creator;
    }

    public void setCreator(ApplicationUser creator) {
        this.creator = creator;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public ApplicationUser getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(ApplicationUser lastModifier) {
        this.lastModifier = lastModifier;
    }

}
