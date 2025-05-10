/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.app.pengajaran.entity.NilaiSetoran;
import id.my.mdn.kupu.app.pengajaran.entity.Presensi;
import id.my.mdn.kupu.core.base.view.annotation.SorterField;
import id.my.mdn.kupu.core.party.entity.PartyRelationshipId;
import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Objects;

/**
 *
 * @author aphasan
 */
@SorterField(value = "fromDate", sort = SorterField.Sort.AUTO, label = "Mulai")
public class RangkumanPengajaran implements Serializable {

    private static final long serialVersionUID = 1L;

    private PartyRelationshipId id;

    private String santriName;

    private PartyRelationshipId pengasuhanId;

    private Long kitabId;

    private String kitabKode;

    private String nis;

    private String tahunMasukName;

    private Integer lamaBelajar;

    private String bdas;
    
    private Boolean bdasWarning;

    private String nbdas;

    private Boolean nasehat;

    private LocalDate d1;

    private Presensi k1;

    private NilaiSetoran s1;

    private LocalDate d2;

    private Presensi k2;

    private NilaiSetoran s2;

    private LocalDate d3;

    private Presensi k3;

    private NilaiSetoran s3;

    private LocalDate d4;

    private Presensi k4;

    private NilaiSetoran s4;

    private LocalDate d5;

    private Presensi k5;

    private NilaiSetoran s5;

    private LocalDate d6;

    private Presensi k6;

    private NilaiSetoran s6;

    public RangkumanPengajaran() {
    }

    public RangkumanPengajaran(Long halaqohId, Long santriId, LocalDate pengajaranFromDate,
            Long kitabId, String kitabKode,
            Long kelompkPengasuhanId, LocalDate pengasuhanFromDate,
            String name,
            String nis, String tahunMasukName, LocalDate tahunMasukFromDate,
            Integer bdasMerah, Integer bdasKuning, Boolean bdasWarning, Integer nbdasMerah, Integer nbdasKuning,
            Boolean nasehat,
            LocalDate d1, String k1, String s1,
            LocalDate d2, String k2, String s2,
            LocalDate d3, String k3, String s3,
            LocalDate d4, String k4, String s4,
            LocalDate d5, String k5, String s5,
            LocalDate d6, String k6, String s6) {
        
        System.err.printf("SELEKTRI NILENE BDAS: %dM, %dK; NBDAS: %dM, %dK", bdasMerah, bdasKuning, nbdasMerah, nbdasKuning);

        this.id = new PartyRelationshipId(halaqohId, santriId, pengajaranFromDate, "Pengajaran");

        this.pengasuhanId = new PartyRelationshipId(kelompkPengasuhanId, santriId, pengasuhanFromDate, "Pengasuhan");

        this.santriName = name;
        this.kitabId = kitabId;
        this.kitabKode = kitabKode;

        this.nis = nis;
        if (tahunMasukFromDate != null) {
            this.tahunMasukName = String.valueOf(tahunMasukFromDate.getYear());

            long days = DAYS.between(tahunMasukFromDate, LocalDate.now());
            this.lamaBelajar = ((int) (days / 365)) + (((int) (days % 365)) > 0 ? 0 : 0);
        }

        if(bdasMerah != null && bdasKuning != null) this.bdas = parse(bdasMerah, bdasKuning);
        this.bdasWarning = bdasWarning;
        if(nbdasMerah != null && nbdasKuning != null) this.nbdas = parse(nbdasMerah, nbdasKuning);

        this.nasehat = nasehat;

        this.d1 = d1;
        if(k1 != null) this.k1 = Presensi.valueOf(k1);
        if(s1 != null) this.s1 = NilaiSetoran.valueOf(s1);
        
        this.d2 = d2;
        if(k2 != null) this.k2 = Presensi.valueOf(k2);
        if(s2 != null) this.s2 = NilaiSetoran.valueOf(s2);
        
        this.d3 = d3;
        if(k3 != null) this.k3 = Presensi.valueOf(k3);
        if(s3 != null) this.s3 = NilaiSetoran.valueOf(s3);
        
        this.d4 = d4;
        if(k4 != null) this.k4 = Presensi.valueOf(k4);
        if(s4 != null) this.s4 = NilaiSetoran.valueOf(s4);
        
        this.d5 = d5;
        if(k5 != null) this.k5 = Presensi.valueOf(k5);
        if(s5 != null) this.s5 = NilaiSetoran.valueOf(s5);
        
        this.d6 = d6;
        if(k6 != null) this.k6 = Presensi.valueOf(k6);
        if(s6 != null) this.s6 = NilaiSetoran.valueOf(s6);
    }

    private String parse(Integer merah, Integer kuning) {

        StringBuilder p3 = new StringBuilder();

        if (merah > 0) {
            p3.append(merah).append("M");
        }

        if (kuning > 0) {
            p3.append(kuning).append("K");
        }

        if (p3.isEmpty()) {
            p3.append("H");
        }

        return p3.toString();

    }

    public PartyRelationshipId getId() {
        System.err.printf("SELEKTRI RANGKUMAN PENGAJARAN: %s", id);
        return id;
    }

    public Long getKitabId() {
        return kitabId;
    }

    public void setKitabId(Long kitabId) {
        this.kitabId = kitabId;
    }
    
    public Long getSantriId() {
        return id.getToRole();
    }

    public String getSantriName() {
        return santriName;
    }

    public String getKitabKode() {
        return kitabKode;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getTahunMasukName() {
        return tahunMasukName;
    }

    public void setTahunMasukName(String tahunMasukName) {
        this.tahunMasukName = tahunMasukName;
    }

    public Integer getLamaBelajar() {
        return lamaBelajar;
    }

    public void setLamaBelajar(Integer lamaBelajar) {
        this.lamaBelajar = lamaBelajar;
    }

    public String getBdas() {
        return bdas;
    }

    public void setBdas(String bdas) {
        this.bdas = bdas;
    }

    public Boolean getBdasWarning() {
        return bdasWarning;
    }

    public void setBdasWarning(Boolean bdasWarning) {
        this.bdasWarning = bdasWarning;
    }

    public String getNbdas() {
        return nbdas;
    }

    public void setNbdas(String nbdas) {
        this.nbdas = nbdas;
    }

    public Boolean getNasehat() {
        return nasehat;
    }

    public void setNasehat(Boolean nasehat) {
        this.nasehat = nasehat;
    }

    public LocalDate getD1() {
        return d1;
    }

    public void setD1(LocalDate d1) {
        this.d1 = d1;
    }

    public Presensi getK1() {
        return k1;
    }

    public void setK1(Presensi k1) {
        this.k1 = k1;
    }

    public NilaiSetoran getS1() {
        return s1;
    }

    public void setS1(NilaiSetoran s1) {
        this.s1 = s1;
    }

    public LocalDate getD2() {
        return d2;
    }

    public void setD2(LocalDate d2) {
        this.d2 = d2;
    }

    public Presensi getK2() {
        return k2;
    }

    public void setK2(Presensi k2) {
        this.k2 = k2;
    }

    public NilaiSetoran getS2() {
        return s2;
    }

    public void setS2(NilaiSetoran s2) {
        this.s2 = s2;
    }

    public LocalDate getD3() {
        return d3;
    }

    public void setD3(LocalDate d3) {
        this.d3 = d3;
    }

    public Presensi getK3() {
        return k3;
    }

    public void setK3(Presensi k3) {
        this.k3 = k3;
    }

    public NilaiSetoran getS3() {
        return s3;
    }

    public void setS3(NilaiSetoran s3) {
        this.s3 = s3;
    }

    public LocalDate getD4() {
        return d4;
    }

    public void setD4(LocalDate d4) {
        this.d4 = d4;
    }

    public Presensi getK4() {
        return k4;
    }

    public void setK4(Presensi k4) {
        this.k4 = k4;
    }

    public NilaiSetoran getS4() {
        return s4;
    }

    public void setS4(NilaiSetoran s4) {
        this.s4 = s4;
    }

    public LocalDate getD5() {
        return d5;
    }

    public void setD5(LocalDate d5) {
        this.d5 = d5;
    }

    public Presensi getK5() {
        return k5;
    }

    public void setK5(Presensi k5) {
        this.k5 = k5;
    }

    public NilaiSetoran getS5() {
        return s5;
    }

    public void setS5(NilaiSetoran s5) {
        this.s5 = s5;
    }

    public LocalDate getD6() {
        return d6;
    }

    public void setD6(LocalDate d6) {
        this.d6 = d6;
    }

    public Presensi getK6() {
        return k6;
    }

    public void setK6(Presensi k6) {
        this.k6 = k6;
    }

    public NilaiSetoran getS6() {
        return s6;
    }

    public void setS6(NilaiSetoran s6) {
        this.s6 = s6;
    }

    public PartyRelationshipId getPengasuhanId() {
        return pengasuhanId;
    }

    public void setPengasuhanId(PartyRelationshipId pengasuhanId) {
        this.pengasuhanId = pengasuhanId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final RangkumanPengajaran other = (RangkumanPengajaran) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return id != null ? id.toString() : null;
    }

}
