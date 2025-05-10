/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.app.pengajaran.entity.PenasehatanSantri;
import id.my.mdn.kupu.app.pengajaran.entity.PresensiSantri;
import id.my.mdn.kupu.app.pengajaran.entity.SetoranSantri;
import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.PartyRelationship;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aphasan
 */
@Entity
@Table(name = "MIABH_PENGAJARAN")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "RangkumanPengajaran",
            classes = {
                @ConstructorResult(
                        targetClass = RangkumanPengajaran.class,
                        columns = {
                            @ColumnResult(name = "HALAQOH_ID", type = Long.class),
                            @ColumnResult(name = "SANTRI_ID", type = Long.class),
                            @ColumnResult(name = "PENGAJARAN_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "KITAB_ID", type = Long.class),
                            @ColumnResult(name = "KITAB_KODE", type = String.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ID", type = Long.class),
                            @ColumnResult(name = "PENGASUHAN_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "NAME", type = String.class),

                            @ColumnResult(name = "NIS", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_NAME", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "BDAS_KUNING", type = Integer.class),
                            @ColumnResult(name = "BDAS_WARNING", type = Boolean.class),
                            @ColumnResult(name = "NON_BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS_KUNING", type = Integer.class),

                            @ColumnResult(name = "NASEHAT", type = Boolean.class),

                            @ColumnResult(name = "D1", type = LocalDate.class),
                            @ColumnResult(name = "K1", type = String.class),
                            @ColumnResult(name = "S1", type = String.class),

                            @ColumnResult(name = "D2", type = LocalDate.class),
                            @ColumnResult(name = "K2", type = String.class),
                            @ColumnResult(name = "S2", type = String.class),

                            @ColumnResult(name = "D3", type = LocalDate.class),
                            @ColumnResult(name = "K3", type = String.class),
                            @ColumnResult(name = "S3", type = String.class),

                            @ColumnResult(name = "D4", type = LocalDate.class),
                            @ColumnResult(name = "K4", type = String.class),
                            @ColumnResult(name = "S4", type = String.class),

                            @ColumnResult(name = "D5", type = LocalDate.class),
                            @ColumnResult(name = "K5", type = String.class),
                            @ColumnResult(name = "S5", type = String.class),

                            @ColumnResult(name = "D6", type = LocalDate.class),
                            @ColumnResult(name = "K6", type = String.class),
                            @ColumnResult(name = "S6", type = String.class)
                        }
                )
            }
    ),
    @SqlResultSetMapping(
            name = "CatatanAbsensiHalaqoh",
            classes = {
                @ConstructorResult(
                        targetClass = CatatanAbsensiHalaqoh.class,
                        columns = {
                            @ColumnResult(name = "SN_NUM", type = Integer.class),
                            @ColumnResult(name = "KPP_NUM", type = Integer.class),

                            @ColumnResult(name = "BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "BDAS_KUNING", type = Integer.class),
                            @ColumnResult(name = "BDAS_WARNING", type = Boolean.class),
                            @ColumnResult(name = "NON_BDAS_MERAH", type = Integer.class),
                            @ColumnResult(name = "NON_BDAS_KUNING", type = Integer.class),

                            @ColumnResult(name = "SANTRI_ID", type = Long.class)}
                )
            }
    ),
    @SqlResultSetMapping(
            name = "DetailCatatanAbsensiHalaqoh",
            classes = {
                @ConstructorResult(
                        targetClass = DetailCatatanAbsensiHalaqoh.class,
                        columns = {
                            @ColumnResult(name = "BENTUK", type = String.class),
                            @ColumnResult(name = "AKTIFITAS_COUNT", type = Integer.class),
                            @ColumnResult(name = "CONFIRMED", type = Boolean.class)}
                )
            }
    )
})
public class Pengajaran extends PartyRelationship {

    private static final long serialVersionUID = 1L;

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Pengajaran> {

        public Builder() {
            super(new Pengajaran());
        }

        public Builder from(HalaqohPengajaran source) {
            if (source != null) {
                entity.setFromRole(source);
                if (source.getSourceRelationships() == null) {
                    source.setSourceRelationships(new ArrayList<>());
                }
                source.getSourceRelationships().add(entity);
            }

            return this;
        }

        public Builder to(Santri target) {
            if (target != null) {
                entity.setToRole(target);
                if (target.getTargetRelationships() == null) {
                    target.setTargetRelationships(new ArrayList<>());
                }
                target.getTargetRelationships().add(entity);
            }

            return this;
        }

    }

    @ManyToOne(fetch = FetchType.LAZY)
    private JenisKitab jenisKitab;

    @OneToMany(mappedBy = "pengajaran", cascade = CascadeType.ALL)
    private List<PresensiSantri> listPresensiSantri;

    @OneToMany(mappedBy = "pengajaran", cascade = CascadeType.ALL)
    private List<SetoranSantri> listSetoranSantri;

    @OneToMany(mappedBy = "pengajaran", cascade = CascadeType.ALL)
    private List<PenasehatanSantri> listPenasehatanSantri;

    public HalaqohPengajaran getHalaqoh() {
        return (HalaqohPengajaran) getFromRole();
    }

    public void setHalaqoh(HalaqohPengajaran halaqoh) {
        setFromRole(halaqoh);
    }

    public Long getSantriId() {
        return getId().getToRole();
    }

    public Santri getSantri() {
        return (Santri) getToRole();
    }

    public void setSantri(Santri santri) {
        setToRole(santri);
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public void setJenisKitab(JenisKitab jenisKitab) {
        this.jenisKitab = jenisKitab;
    }

    public List<PresensiSantri> getListPresensiSantri() {
        return listPresensiSantri;
    }

    public void setListPresensiSantri(List<PresensiSantri> listPresensiSantri) {
        this.listPresensiSantri = listPresensiSantri;
    }

    public List<SetoranSantri> getListSetoranSantri() {
        return listSetoranSantri;
    }

    public void setListSetoranSantri(List<SetoranSantri> listSetoranSantri) {
        this.listSetoranSantri = listSetoranSantri;
    }

    public List<PenasehatanSantri> getListPenasehatanSantri() {
        return listPenasehatanSantri;
    }

    public void setListPenasehatanSantri(List<PenasehatanSantri> listPenasehatanSantri) {
        this.listPenasehatanSantri = listPenasehatanSantri;
    }

}
