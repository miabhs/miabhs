/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
    public class RangkumanKepengasuhan implements Serializable {
    private final Long partyId;
    private final String name;
    private final Long santriId;
    private final String nis;
    private final Long kelompokPengasuhanId;
    private final String kelompokPengasuhanPartyName;
    private final Boolean koordinator;
    private final Integer lamaBelajar;
    private final String label;
    private final LocalDate fromDate;
    private final LocalDate thruDate;
    private final Integer bdasMerah;
    private final Integer bdasKuning;
    private final String bdas;
    private final Integer nonBdasMerah;
    private final Integer nonBdasKuning;
    private final String nonBdas;

    public RangkumanKepengasuhan(Long personId, String personName, 
            Long santriId, String nis, LocalDate tahunMasukFromDate, Long kelompokPengasuhanId, String kelompokPengasuhanName, Boolean kelompokPengasuhanKoordinator,
            String label, LocalDate fromDate, LocalDate thruDate, 
            Integer bdasMerah, Integer bdasKuning, String bdas, 
            Integer nonBdasMerah, Integer nonBdasKuning, String nonBdas) {
        this.partyId = personId;
        this.name = personName;
        
        this.santriId = santriId;
        this.nis = nis;
        this.kelompokPengasuhanId = kelompokPengasuhanId;
        this.kelompokPengasuhanPartyName = kelompokPengasuhanName;
        this.koordinator = kelompokPengasuhanKoordinator;       
        
        long days = DAYS.between(tahunMasukFromDate, LocalDate.now());
        this.lamaBelajar =  ((int) (days / 365)) + (((int) (days % 365)) > 0 ? 0 : 0);
        
        this.label = label;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.bdasMerah = bdasMerah;
        this.bdasKuning = bdasKuning;
        this.bdas = bdas;
        this.nonBdasMerah = nonBdasMerah;
        this.nonBdasKuning = nonBdasKuning;
        this.nonBdas = nonBdas;
    }

    public Long getPartyId() {
        return partyId;
    }
    
    public String getName() {
        return name;
    }

    public Long getSantriId() {
        return santriId;
    }

    public String getNis() {
        return nis;
    }

    public Long getKelompokPengasuhanId() {
        return kelompokPengasuhanId;
    }

    public String getKelompokPengasuhanPartyName() {
        return kelompokPengasuhanPartyName;
    }

    public Boolean getKoordinator() {
        return koordinator;
    }

    public Integer getLamaBelajar() {
        return lamaBelajar;
    }

    public String getLabel() {
        return label;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
  
    public LocalDate getThruDate() {
        return thruDate;
    }

    public Integer getBdasMerah() {
        return bdasMerah;
    }

    public Integer getBdasKuning() {
        return bdasKuning;
    }

    public String getBdas() {
        return bdas;
    }

    public Integer getNonBdasMerah() {
        return nonBdasMerah;
    }

    public Integer getNonBdasKuning() {
        return nonBdasKuning;
    }

    public String getNonBdas() {
        return nonBdas;
    }

    @Override
    public String toString() {
        return "RangkumanKepengasuhan{" + "partyId=" + partyId + ", name=" + name + ", santriId=" + santriId + ", nis=" + nis + ", kelompokPengasuhanPartyName=" + kelompokPengasuhanPartyName + ", label=" + label + ", bdasMerah=" + bdasMerah + ", bdasKuning=" + bdasKuning + ", bdas=" + bdas + ", nonBdasMerah=" + nonBdasMerah + ", nonBdasKuning=" + nonBdasKuning + ", nonBdas=" + nonBdas + '}';
    }
    
            
}
