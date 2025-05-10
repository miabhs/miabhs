/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import java.io.Serializable;

/**
 *
 * @author aphasan
 */
public class CatatanAbsensiHalaqoh implements Serializable {
    
    private final Integer santriNum;
    
    private final Integer kelompokPengasuhanNum;

    private final String bdas;
    
    private final Boolean bdasWarning;

    private final String nbdas;
    
    private final Long santriId;

    public CatatanAbsensiHalaqoh(Integer santriNum, Integer kelompokKepengasuhanNum,
            Integer bdasMerah, Integer bdasKuning, Boolean bdasWarning, Integer nbdasMerah, Integer nbdasKuning,
            Long santriId ) {
        this.santriNum = santriNum;
        this.kelompokPengasuhanNum = kelompokKepengasuhanNum;

        this.bdas = parse(bdasMerah,bdasKuning);
        this.bdasWarning = bdasWarning;
        this.nbdas = parse(nbdasMerah, nbdasKuning);
        
        this.santriId = santriId;
        System.out.println("SELEK KONSTRAK santriId: " + santriId);
    }

    public Integer getSantriNum() {
        return santriNum;
    }

    public Integer getKelompokPengasuhanNum() {
        return kelompokPengasuhanNum;
    }

    public String getBdas() {
        return bdas;
    }

    public Boolean getBdasWarning() {
        return bdasWarning;
    }

    public String getNbdas() {
        return nbdas;
    }

    public Long getSantriId() {
        return santriId;
    }

    private String parse(Integer merah, Integer kuning) {

        StringBuilder p3 = new StringBuilder();

        if (merah != null && merah > 0) {
            p3.append(merah).append("M");
        }

        if (kuning != null && kuning > 0) {
            p3.append(kuning).append("K");
        }

        if (p3.isEmpty()) {
            p3.append("H");
        }

        return p3.toString();
        
    }
    
}
