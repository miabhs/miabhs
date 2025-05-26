/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.base.view.annotation.SorterField.Sort;
import id.my.mdn.kupu.core.base.view.annotation.SorterFields;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Entity
@Table(name = "MIABH_SANTRI")
@SqlResultSetMappings({
    @SqlResultSetMapping(
            name = "Santri",
            classes = {
                @ConstructorResult(
                        targetClass = Santri.class,
                        columns = {
                            @ColumnResult(name = "ID", type = Long.class),

                            @ColumnResult(name = "PERSON_ID", type = Long.class),
                            @ColumnResult(name = "PERSON_NAME", type = String.class),
                            @ColumnResult(name = "PERSON_GENDER", type = String.class),
                            @ColumnResult(name = "PERSON_DOB", type = LocalDate.class),

                            @ColumnResult(name = "NAMABAPAK", type = String.class),
                            @ColumnResult(name = "NIS", type = String.class),
                            @ColumnResult(name = "JENISSANTRI", type = String.class),
                            @ColumnResult(name = "ANGKATAN", type = Integer.class),
                            @ColumnResult(name = "TAHUNMASUK_ID", type = Long.class),
                            @ColumnResult(name = "TAHUNMASUK_NAME", type = String.class),
                            @ColumnResult(name = "TAHUNMASUK_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_ORG_ID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_NAME", type = String.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_KOORDINATOR", type = Boolean.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHAN_PENGASUHAN_FROMDATE", type = LocalDate.class),

                            @ColumnResult(name = "STATUS", type = String.class),
                            @ColumnResult(name = "STATUS_FROMDATE", type = LocalDate.class)}
                )
            }
    )
})
@SorterFields({
    @SorterField(value = "nis", label = "NIS", sort = Sort.MANUAL),
    @SorterField(value = "name", label = "Nama", sort = Sort.MANUAL),
    @SorterField(value = "dateOfBirth", label = "Tanggal Lahir", sort = Sort.MANUAL)
})
public class Santri extends PersonRole {

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Santri> {

        public Builder() {
            super(new Santri());
        }

        public Builder withPerson(Person person) {
            if (person.getRoles() == null) {
                person.setRoles(new ArrayList<>());
            }
            entity.setParty(person);
            person.getRoles().add(entity);

            return this;
        }

    }

    private String nis;

    @ManyToOne
    private TahunPembelajaran tahunMasuk;

    private Integer angkatan;

    @OneToMany(mappedBy = "santri", cascade = CascadeType.ALL)
    private List<StatusSantri> listStatus;

    private String namaBapak;

    private String namaIbu;

    @Enumerated(EnumType.STRING)
    private JenisSantri jenisSantri;

    public Santri() {
    }
    
    public Santri(Long id) {
        setId(id);
    }

    public Santri(
            Long id,
            Long personId, String personName, String personGender, LocalDate personDob,
            String namaBapak, String nis, String jenisSantri, Integer angkatan, Long tahunMasukId, String tahunMasukName, LocalDate tahunMasukFromDate,
            Long kelompokPengasuhanId, Long kelompokPengasuhanOrgId, String kelompokPengasuhanName, Boolean kelompokPengasuhanKoordinator, LocalDate kelompokPengasuhanPengasuhanFromDate,
            String status, LocalDate statusFromDate) {

        setId(id);

        if (personId != null) {
            Person person = Person.builder()
                    .firstName(personName)
                    .gender(personGender != null ? GenderType.valueOf(personGender) : null)
                    .dateOfBirth(personDob)
                    .get();
            person.setId(personId);
            setPerson(person);
        }

        if (tahunMasukId != null) {
            setFromDate(LocalDateTime.of(tahunMasukFromDate, LocalTime.of(0, 0, 0)));
            this.tahunMasuk = new TahunPembelajaran(tahunMasukId, tahunMasukName);
            this.tahunMasuk.setFromDate(tahunMasukFromDate);
        }

        if (jenisSantri != null) {
            this.jenisSantri = JenisSantri.valueOf(jenisSantri);
        }

        setTargetRelationships(new ArrayList<>());

        if (kelompokPengasuhanId != null) {
            KelompokPengasuhan kelompokPengasuhan = new KelompokPengasuhan(kelompokPengasuhanId, new Organization(kelompokPengasuhanOrgId, kelompokPengasuhanName));
            Pengasuhan pengasuhan = new Pengasuhan();
            pengasuhan.setId(new PartyRelationshipId(kelompokPengasuhanId, id, kelompokPengasuhanPengasuhanFromDate, "Pengasuhan"));
            pengasuhan.setSantri(this);
            pengasuhan.setKelompokPengasuhan(kelompokPengasuhan);
            pengasuhan.setKoordinator(kelompokPengasuhanKoordinator != null ? kelompokPengasuhanKoordinator : false);

            getTargetRelationships().add(pengasuhan);
        }

        this.listStatus = new ArrayList<>();

        if (status != null) {
            StatusSantri statusSantri = new StatusSantri();
            statusSantri.setId(new StatusSantriId(id, statusFromDate));
            statusSantri.setStatus(StatusKesantrian.valueOf(status));

            this.listStatus.add(statusSantri);
        }

        this.namaBapak = namaBapak;

        this.nis = nis;

        this.angkatan = angkatan;

    }

    public Integer getLamaBelajar() {
        long days = DAYS.between(tahunMasuk.getFromDate(), LocalDate.now());
        return ((int) (days / 365)) + (((int) (days % 365)) > 0 ? 1 : 0);
    }

    public int calculateTahunBelajar(TahunPembelajaran tahunPembelajaran) {

        LocalDate fromDate = this.tahunMasuk.getFromDate();
        LocalDate thruDate = tahunPembelajaran.getFromDate();
        Long days = YEARS.between(fromDate, thruDate);
        return days.intValue() + 1;

    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public TahunPembelajaran getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(TahunPembelajaran tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public Integer getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(Integer angkatan) {
        this.angkatan = angkatan;
    }

    public List<StatusSantri> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<StatusSantri> listStatus) {
        this.listStatus = listStatus;
    }

    public JenisSantri getJenisSantri() {
        return jenisSantri;
    }

    public void setJenisSantri(JenisSantri jenisSantri) {
        this.jenisSantri = jenisSantri;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        if (!getTargetRelationships().isEmpty() && getTargetRelationships().size() == 1) {
            return ((Pengasuhan) getTargetRelationships().get(0)).getKelompokPengasuhan();
        } else {
            return null;
        }
    }

    public Boolean getKoordinator() {
        if (!getTargetRelationships().isEmpty() && getTargetRelationships().size() == 1) {
            return ((Pengasuhan) getTargetRelationships().get(0)).isKoordinator();
        } else {
            return false;
        }
    }

    public StatusKesantrian getStatus() {
        if (!listStatus.isEmpty() && listStatus.size() == 1) {
            return listStatus.get(0).getStatus();
        } else {
            return null;
        }
    }

    public String getNamaBapak() {
        return namaBapak;
    }

    public void setNamaBapak(String namaBapak) {
        this.namaBapak = namaBapak;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getName() {
        return getPerson().getName();
    }
}
