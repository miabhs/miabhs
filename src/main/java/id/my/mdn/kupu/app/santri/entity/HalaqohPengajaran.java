/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.OrganizationRole;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_HALAQOHPENGAJARAN")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "HalaqohPengajaran",
            classes = {
                @ConstructorResult(
                        targetClass = HalaqohPengajaran.class,
                        columns = {
                            @ColumnResult(name = "ID", type = Long.class),
                            @ColumnResult(name = "HALAQOH_NAME", type = String.class),
                            @ColumnResult(name = "JENISKITAB_ID", type = Long.class), 
                            @ColumnResult(name = "JENISKITAB_JUDUL", type = String.class),
                            @ColumnResult(name = "GENDER", type = String.class),
                            @ColumnResult(name = "KELOMPOK_WAKTU", type = String.class),

                            @ColumnResult(name = "PENGAMPU_ID", type = Long.class),
                            @ColumnResult(name = "PENGAMPU_FIRSTNAME", type = String.class),
                            @ColumnResult(name = "PENGAMPU_LASTNAME", type = String.class)
                        }
                )
            }
    ),
    @SqlResultSetMapping(
            name = "AbsensiHalaqoh",
            classes = {
                @ConstructorResult(
                        targetClass = AbsensiHalaqoh.class,
                        columns = {
                            @ColumnResult(name = "SANTRIID", type = Long.class),

                            @ColumnResult(name = "FIRSTNAME", type = String.class),
                            @ColumnResult(name = "LASTNAME", type = String.class),

                            @ColumnResult(name = "NIS", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_NAME", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "BDAS_KUNING", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS_KUNING", type = Integer.class)}
                )
            }
    ),
    @SqlResultSetMapping(
            name = "KppHalaqoh",
            classes = {
                @ConstructorResult(
                        targetClass = KppHalaqoh.class,
                        columns = {
                            @ColumnResult(name = "KPP_NUM", type = Integer.class),
                            @ColumnResult(name = "FIRSTNAME", type = String.class),
                            @ColumnResult(name = "LASTNAME", type = String.class)}
                )
            }
    )
})
public class HalaqohPengajaran extends OrganizationRole {

    private static final long serialVersionUID = 1L;

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<HalaqohPengajaran> {

        public Builder() {
            super(new HalaqohPengajaran());
        }

        public Builder withOrganization(Organization organization) {
            if (organization.getRoles() == null) {
                organization.setRoles(new ArrayList<>());
            }
            entity.setParty(organization);
            organization.getRoles().add(entity);

            return this;
        }

    }

    @ManyToOne
    private KategoriKitab kategoriKitab;
    
    @ManyToOne
    private JenisKitab jenisKitab;
    
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @ManyToOne
    private KelompokHalaqohPengajaran kelompok;

    @Transient
    private String name;

    @Transient
    private Long jenisKitabId;

    @Transient
    private String jenisKitabJudul;

    @Transient
    private String kelompokWaktu;

    @Transient
    private Long pengampuId;

    @Transient
    private String pengampuName;

    public HalaqohPengajaran() {
    }

    public HalaqohPengajaran(Long id) {
        setId(id);
    }

    public HalaqohPengajaran(Long id, String name, Long jenisKitabId, String jenisKitabJudul, String gender, String kelompokWaktu, Long pengampuId, String pengampuFirstName, String pengampuLastName) {
        setId(id);
        this.name = name;
        this.jenisKitabId = jenisKitabId;
        this.jenisKitabJudul = jenisKitabJudul;
        this.gender = GenderType.valueOf(gender);
        this.kelompokWaktu = kelompokWaktu;
        this.pengampuId = pengampuId;
        this.pengampuName
                = ((pengampuFirstName != null && !pengampuFirstName.isBlank()) ? " " + pengampuFirstName : "")
                + ((pengampuLastName != null && !pengampuLastName.isBlank()) ? " " + pengampuLastName : "");
    }

    public String getNama() {
        return getOrganization().getName();
    }

    public void setNama(String nama) {
        getOrganization().setName(nama);
    }

    public KategoriKitab getKategoriKitab() {
        return kategoriKitab;
    }

    public void setKategoriKitab(KategoriKitab kategoriKitab) {
        this.kategoriKitab = kategoriKitab;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
    }

    public KelompokHalaqohPengajaran getKelompok() {
        return kelompok;
    }

    public void setKelompok(KelompokHalaqohPengajaran kelompok) {
        this.kelompok = kelompok;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Long getJenisKitabId() {
        return jenisKitabId;
    }

    public void setJenisKitabId(Long jenisKitabId) {
        this.jenisKitabId = jenisKitabId;
    }

    public String getJenisKitabJudul() {
        return jenisKitabJudul;
    }

    public void setJenisKitabJudul(String jenisKitabJudul) {
        this.jenisKitabJudul = jenisKitabJudul;
    }

    public String getKelompokWaktu() {
        return kelompokWaktu;
    }

    public Long getPengampuId() {
        return pengampuId;
    }

    public void setPengampuId(Long pengampuId) {
        this.pengampuId = pengampuId;
    }

    public String getPengampuName() {
        return (pengampuName != null && !pengampuName.isBlank()) ? "Ust. " + pengampuName : "Belum ada";
    }

    public void setPengampuName(String pengampuName) {
        this.pengampuName = pengampuName;
    }
    
    public String getLabel() {
        return new StringBuilder(name).append(" - ").append(jenisKitabJudul).toString();
    }

}
