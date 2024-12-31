/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
    public class RangkumanKepengasuhan implements Serializable {
    private final Long partyId;
    private final String name;
    private final Long santriId;
    private final String nis;
    private final String kelompokPengasuhanPartyName;
    private final String label;
    private final LocalDate fromDate;
    private final LocalDate thruDate;
    private final Integer bdasMerah;
    private final Integer bdasKuning;
    private final String bdas;
    private final Integer nonBdasMerah;
    private final Integer nonBdasKuning;
    private final String nonBdas;

    public RangkumanKepengasuhan(Long partyId, String firstname, String lastname, 
            Long santriId, String nis, String kelompokPengasuhanPartyName, 
            String label, LocalDate fromDate, LocalDate thruDate, 
            Integer bdasMerah, Integer bdasKuning, String bdas, 
            Integer nonBdasMerah, Integer nonBdasKuning, String nonBdas) {
        this.partyId = partyId;
        
        StringBuilder sb = new StringBuilder(firstname);
        if(lastname != null && !lastname.isBlank()) {
            sb.append(" ").append(lastname);
        }
        this.name = sb.toString();
        
        this.santriId = santriId;
        this.nis = nis;
        this.kelompokPengasuhanPartyName = kelompokPengasuhanPartyName;
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

    public String getKelompokPengasuhanPartyName() {
        return kelompokPengasuhanPartyName;
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
