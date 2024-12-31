/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
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
import java.util.UUID;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_HIKMAHKAUNIYAH")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "HikmahKauniyah",
            classes = {
                @ConstructorResult(
                        targetClass = HikmahKauniyah.class,
                        columns = {
                            @ColumnResult(name = "ID", type = String.class),
                            @ColumnResult(name = "CONTENT", type = String.class),
                            @ColumnResult(name = "EVENT_DATE", type = LocalDate.class),

                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "PARTY_ID", type = Long.class),
                            @ColumnResult(name = "FIRSTNAME", type = String.class),
                            @ColumnResult(name = "LASTNAME", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHANID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANORGANIZATIONID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANORGANIZATIONNAME", type = String.class),
                            @ColumnResult(name = "CREATED", type = LocalDateTime.class)
                        }
                )
            }
    )
})
public class HikmahKauniyah implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToOne
    private Santri santri;

    @Column(name = "EVENT_DATE")
    private LocalDate date;

    @Lob
    private String content;

    private LocalDateTime created;

    public HikmahKauniyah() {
        this.date = LocalDate.now();
    }

    public HikmahKauniyah(String id, String content, LocalDate date,
            Long santriId, Long personId, String firstName, String lastName,
            Long kelompokPengasuhanId, Long kelompokPengasuhanOrganizationId,
            String kelompokPengasuhanOrganizationName, LocalDateTime created
    ) {

        this.id = id;
        this.content = content;
        this.date = date;
        this.created = created;

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

        if (kelompokPengasuhanId != null) {

            // Kelompok Pengasuhan
            KelompokPengasuhan kelompokPengasuhan = new KelompokPengasuhan();
            kelompokPengasuhan.setId(kelompokPengasuhanId);
            Organization organization = new Organization();
            organization.setId(kelompokPengasuhanOrganizationId);
            organization.setName(kelompokPengasuhanOrganizationName);
            kelompokPengasuhan.setOrganization(organization);
            this.santri.setKelompokPengasuhan(kelompokPengasuhan);

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HikmahKauniyah)) {
            return false;
        }
        HikmahKauniyah other = (HikmahKauniyah) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id;
    }

}
