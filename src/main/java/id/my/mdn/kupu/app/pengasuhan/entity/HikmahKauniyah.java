/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Order;
import id.my.mdn.kupu.core.base.view.annotation.SorterFields;
import id.my.mdn.kupu.core.party.entity.GenderType;
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
import jakarta.persistence.Transient;
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
                            @ColumnResult(name = "CREATED", type = LocalDateTime.class),

                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "PARTY_ID", type = Long.class),
                            @ColumnResult(name = "SANTRI_NAME", type = String.class),
                            @ColumnResult(name = "SANTRI_GENDER", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_NAME", type = String.class)
                        }
                )
            }
    )
})
@SorterFields({
    @SorterField(value = "santriName", label = "Name", sort = SorterField.Sort.MANUAL),
    @SorterField(value = "date", order = Order.DESC, sort = SorterField.Sort.MANUAL, label = "Tanggal"),
    @SorterField(value = "created", order = Order.DESC, label = "Waktu Input")
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

    @Transient
    private String santriName;

    @Transient
    private GenderType santriGender;

    @Transient
    private String kelompokPengasuhanName;

    public HikmahKauniyah() {
        this.date = LocalDate.now();

        if (santri != null) {
            santriName = santri.getPerson().getName();
            kelompokPengasuhanName = santri.getKelompokPengasuhan().getOrganization().getName();
        }
    }

    public HikmahKauniyah(String id, String content, LocalDate date, LocalDateTime created,
            Long santriId, Long personId, String santriName, String santriGender,
            Long kelompokPengasuhanId, //Long kelompokPengasuhanOrganizationId,
            String kelompokPengasuhanOrganizationName
    ) {

        this.id = id;
        this.content = content;
        this.date = date;
        this.created = created;

        // Santri
        if (santriId != null) {
            Santri santri = new Santri();
            santri.setId(santriId);
            this.santri = santri;

            this.santriName = santriName;

            if (santriGender != null) {
                this.santriGender = GenderType.valueOf(santriGender);
            }

            this.kelompokPengasuhanName = kelompokPengasuhanOrganizationName;

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

    public String getSantriName() {
        return santriName;
    }

    public void setSantriName(String santriName) {
        this.santriName = santriName;
    }

    public GenderType getSantriGender() {
        return santriGender;
    }

    public void setSantriGender(GenderType santriGender) {
        this.santriGender = santriGender;
    }

    public String getKelompokPengasuhanName() {
        return kelompokPengasuhanName;
    }

    public void setKelompokPengasuhanName(String kelompokPengasuhanName) {
        this.kelompokPengasuhanName = kelompokPengasuhanName;
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
