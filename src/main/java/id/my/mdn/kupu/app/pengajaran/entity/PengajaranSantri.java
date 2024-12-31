/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_PENGAJARANSANTRI")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "PengajaranSantri",
            classes = {
                @ConstructorResult(
                        targetClass = PengajaranSantri.class,
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
                            @ColumnResult(name = "jenisPengajaranId", type = Long.class),
                            @ColumnResult(name = "jenisPengajaranKode", type = String.class),
                            @ColumnResult(name = "jenisPengajaranNama", type = String.class),

                            @ColumnResult(name = "jenisKitabId", type = Long.class),
                            @ColumnResult(name = "jenisKitabJudul", type = String.class),
                            
                            @ColumnResult(name = "notes", type = String.class)}
                )
            }
    )
})
public class PengajaranSantri implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private LocalDate pengajaranDate;

    @ManyToOne
    private Santri santri;

    @ManyToOne(fetch = FetchType.LAZY)
    private JenisKitab jenisKitab;

    private String notes;

    private LocalDateTime created;

    public PengajaranSantri() {
    }

    public PengajaranSantri(
            String id, LocalDate pengajaranDate, LocalDateTime created, String notes,
            Long santriId, Long personId, String firstName, String lastName,
            Long kelompokPengasuhanId, Long kelompokPengasuhanOrganizationId, String kelompokPengasuhanOrganizationName,
            Long jenisKitabId, String judul
    ) {

        this.id = id;
        this.pengajaranDate = pengajaranDate;
        this.created = created;
        this.notes = notes;

        // Santri
        this.santri = new Santri();
        this.santri.setId(santriId);
        Person person = new Person();
        person.setId(personId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        this.santri.setPerson(person);

        // Kelompok Pengasuhan
        KelompokPengasuhan kelompokPengasuhan = new KelompokPengasuhan();
        kelompokPengasuhan.setId(kelompokPengasuhanId);
        Organization organization = new Organization();
        organization.setId(kelompokPengasuhanOrganizationId);
        organization.setName(kelompokPengasuhanOrganizationName);
        kelompokPengasuhan.setOrganization(organization);
        this.santri.setKelompokPengasuhan(kelompokPengasuhan);

        // Bentuk Aktifitas
        if (jenisKitabId != null) {
            this.jenisKitab = new JenisKitab();
            this.jenisKitab.setId(jenisKitabId);
            this.jenisKitab.setJudul(judul);
        } else {
            this.jenisKitab = null;
        }

    }

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        created = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getPengajaranDate() {
        return pengajaranDate;
    }

    public void setPengajaranDate(LocalDate pengajaranDate) {
        this.pengajaranDate = pengajaranDate;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
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
        final PengajaranSantri other = (PengajaranSantri) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id;
    }

}
