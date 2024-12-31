/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Objects;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RangkumanAktifitas implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long partyId;

    private Long santriId;

    private Long fromroleId;

    private String nama;

    private Integer jumlahData;

    private Integer jumlahKuning;

    private Integer jumlahMerah;

    private String namaKelompok;

    private String kesimpulanPokok; // Status BDAS
    
    private String kesimpulanTambahan;  // Status Non BDASKesimpulan
    
    private String kesimpulan;
    
    private int lamaBelajar;
    
    private boolean koordinator;

    public RangkumanAktifitas(
            Long partyId, 
            Long santriId, 
            Long fromroleId, 
            String nama, 
            Integer jumlahData, 
            Integer jumlahKuning, 
            Integer jumlahMerah, 
            String namaKelompok,
            LocalDate tahunMasuk,
            boolean koordinator) {
        this.partyId = partyId;
        this.santriId = santriId;
        this.fromroleId = fromroleId;
        this.nama = nama;
        this.jumlahData = jumlahData;
        this.jumlahKuning = jumlahKuning;
        this.jumlahMerah = jumlahMerah;
        this.namaKelompok = namaKelompok;
        long days = DAYS.between(tahunMasuk, LocalDate.now());
        this.lamaBelajar = ((int) (days / 365)) + (((int)(days % 365)) > 0 ? 1 : 0);
        this.koordinator = koordinator;
    }

    public RangkumanAktifitas(long santriId) {
        this.santriId = santriId;
    }

    public RangkumanAktifitas() {
    }

    public String getKesimpulanPokok() {
        return kesimpulanPokok;
    }

    public void setKesimpulanPokok(String kesimpulanPokok) {
        this.kesimpulanPokok = kesimpulanPokok;
    }

    public String getKesimpulanTambahan() {
        return kesimpulanTambahan;
    }

    public void setKesimpulanTambahan(String kesimpulanTambahan) {
        this.kesimpulanTambahan = kesimpulanTambahan;
    }

    public String getKesimpulan() {
        return kesimpulan;
    }

    public void setKesimpulan(String kesimpulan) {
        this.kesimpulan = kesimpulan;
    }

    public Integer getLamaBelajar() {
        return lamaBelajar;
    }

    public void setLamaBelajar(Integer lamaBelajar) {
        this.lamaBelajar = lamaBelajar;
    }
    
    public Long getId() {
        return santriId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Long getFromroleId() {
        return fromroleId;
    }

    public void setFromroleId(Long fromroleId) {
        this.fromroleId = fromroleId;
    }

    public Long getSantriId() {
        return santriId;
    }

    public void setSantriId(Long santriId) {
        this.santriId = santriId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getJumlahData() {
        return jumlahData;
    }

    public void setJumlahData(Integer jumlahData) {
        this.jumlahData = jumlahData;
    }

    public Integer getJumlahKuning() {
        return jumlahKuning;
    }

    public void setJumlahKuning(Integer jumlahKuning) {
        this.jumlahKuning = jumlahKuning;
    }

    public Integer getJumlahMerah() {
        return jumlahMerah;
    }

    public void setJumlahMerah(Integer jumlahMerah) {
        this.jumlahMerah = jumlahMerah;
    }

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public void setNamaKelompok(String namaKelompok) {
        this.namaKelompok = namaKelompok;
    }

    public boolean isKoordinator() {
        return koordinator;
    }

    public void setKoordinator(boolean koordinator) {
        this.koordinator = koordinator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final RangkumanAktifitas other = (RangkumanAktifitas) obj;
        return Objects.equals(this.santriId, other.santriId);
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
