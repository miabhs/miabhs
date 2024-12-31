/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.base.model.EntityBuilder;
import id.my.mdn.kupu.core.party.entity.GenderType;
import id.my.mdn.kupu.core.party.entity.Organization;
import id.my.mdn.kupu.core.party.entity.PartyRoleType;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.entity.PersonRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
                            @ColumnResult(name = "PARTYID", type = Long.class),
                            @ColumnResult(name = "PARTYROLETYPEID", type = String.class),
                            @ColumnResult(name = "FIRSTNAME", type = String.class),
                            @ColumnResult(name = "LASTNAME", type = String.class),
                            @ColumnResult(name = "NAMABAPAK", type = String.class),
                            @ColumnResult(name = "GENDER", type = String.class),
                            @ColumnResult(name = "DATEOFBIRTH", type = LocalDate.class),
                            @ColumnResult(name = "NIS", type = String.class),
                            @ColumnResult(name = "STATUS", type = String.class),
                            @ColumnResult(name = "TAHUNMASUKID", type = Long.class),
                            @ColumnResult(name = "TAHUNMASUKNAME", type = String.class),
                            @ColumnResult(name = "TAHUNMASUKFROMDATE", type = LocalDate.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANPARTYID", type = Long.class),
                            @ColumnResult(name = "KELOMPOKPENGASUHANPARTYNAME", type = String.class),
                            @ColumnResult(name = "KOORDINATOR", type = Boolean.class)
                        }
                )
            }
    )
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

    @OneToMany(mappedBy = "santri", cascade = CascadeType.ALL)
    private List<StatusSantri> listStatus;

    @Transient
    private StatusKesantrian status;

    @Transient
    private KelompokPengasuhan kelompokPengasuhan;

    @ManyToMany(mappedBy = "listSantri")
    private List<HalaqohPengajaran> listHalaqohPengajaran;

    @Transient
    private Boolean koordinator;
    
    private String namaBapak;
    
    private String namaIbu;

    public Santri() {
    }

    public Santri(Long id, Long partyId, String partyRoleTypeId, String firstName, String lastName, String namaBapak, String gender, LocalDate dateOfBirth, String nis, String status,
            Long tahunMasukId, String tahunMasukName, LocalDate tahunMasukFromDate, Long kelompokPengasuhanId, Long kelompokPengasuhanPartyId, String kelompokPengasuhanPartyName, Boolean koordinator) {
        setId(id);
        setPartyRoleType(new PartyRoleType(partyRoleTypeId, "Santri"));
        Person person = new Person(partyId, firstName, lastName, GenderType.valueOf(gender), dateOfBirth);
        person.setType("Person");
        this.setPerson(person);
        this.namaBapak = namaBapak;
        this.nis = nis;
        this.tahunMasuk = new TahunPembelajaran(tahunMasukId, tahunMasukName);
        this.tahunMasuk.setFromDate(tahunMasukFromDate);
        
        this.setFromDate(LocalDateTime.of(tahunMasukFromDate, LocalTime.of(0, 0, 0)));
        this.status = StatusKesantrian.valueOf(status);
        if (kelompokPengasuhanId != null) {
            this.kelompokPengasuhan = new KelompokPengasuhan(kelompokPengasuhanId, new Organization(kelompokPengasuhanPartyId, kelompokPengasuhanPartyName));
            this.koordinator = koordinator;
        } else {
            this.kelompokPengasuhan = null;
            this.koordinator = null;
        }
    }

    public Integer getLamaBelajar() {
        long days = DAYS.between(tahunMasuk.getFromDate(), LocalDate.now());
        return ((int) (days / 365)) + (((int) (days % 365)) > 0 ? 1 : 0);
    }

    public int calculateTahunBelajar(TahunPembelajaran tahunPembelajaran) {

        LocalDateTime fromDate = this.getFromDate();
        LocalDateTime thruDate = LocalDateTime.of(tahunPembelajaran.getFromDate(), LocalTime.of(0, 0, 0));
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

    public List<StatusSantri> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<StatusSantri> listStatus) {
        this.listStatus = listStatus;
    }

    public StatusKesantrian getStatus() {
        return status;
    }

    public void setStatus(StatusKesantrian status) {
        this.status = status;
    }

    public KelompokPengasuhan getKelompokPengasuhan() {
        return kelompokPengasuhan;
    }

    public void setKelompokPengasuhan(KelompokPengasuhan kelompokPengasuhan) {
        this.kelompokPengasuhan = kelompokPengasuhan;
    }

    public Boolean getKoordinator() {
        return koordinator;
    }

    public void setKoordinator(Boolean koordinator) {
        this.koordinator = koordinator;
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

    public List<HalaqohPengajaran> getListHalaqohPengajaran() {
        return listHalaqohPengajaran;
    }

    public void setListHalaqohPengajaran(List<HalaqohPengajaran> listHalaqohPengajaran) {
        this.listHalaqohPengajaran = listHalaqohPengajaran;
    }
}
