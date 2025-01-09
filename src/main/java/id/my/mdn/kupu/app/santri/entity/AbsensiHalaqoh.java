/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author aphasan
 */
public class AbsensiHalaqoh implements Serializable {

    private final Long santriId;

    private final String name;

    private final String nis;

    private final String tahunMasukName;

    private final Integer lamaBelajar;

    private final String bdas;

    private final String nbdas;

    public AbsensiHalaqoh(Long santriId, String firstName, String lastName,
            String nis, String tahunMasukName, LocalDate tahunMasukFromDate,
            Integer bdasMerah, Integer bdasKuning, Integer nbdasMerah, Integer nbdasKuning) {

        this.santriId = santriId;

        this.name = ((firstName != null && !firstName.isBlank()) ? " " + firstName : "")
                + ((lastName != null && !lastName.isBlank()) ? " " + lastName : "");

        this.nis = nis;
        this.tahunMasukName = tahunMasukName;
        
        long days = DAYS.between(tahunMasukFromDate, LocalDate.now());
        this.lamaBelajar =  ((int) (days / 365)) + (((int) (days % 365)) > 0 ? 1 : 0);

        this.bdas = parse(bdasMerah,bdasKuning);
        this.nbdas = parse(nbdasMerah, nbdasKuning);
        
    }

    private String parse(Integer merah, Integer kuning) {

        StringBuilder p3 = new StringBuilder();

        if (merah != null && merah > 0) {
            p3.append(merah).append("M");
        }

        if (kuning != null && kuning > 0) {
            p3.append(kuning).append("K");
        }

        if (!p3.isEmpty()) {
            p3.append("H");
        }

        return p3.toString();
        
    }

    public Long getSantriId() {
        return santriId;
    }

    public String getNis() {
        return nis;
    }

    public String getTahunMasukName() {
        return tahunMasukName;
    }

    public Integer getLamaBelajar() {
        return lamaBelajar;
    }

    public String getName() {
        return name;
    }

    public String getBdas() {
        return bdas;
    }

    public String getNbdas() {
        return nbdas;
    }

}
