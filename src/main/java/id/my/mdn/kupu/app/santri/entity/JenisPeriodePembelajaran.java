/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author aphasan
 */
public enum JenisPeriodePembelajaran {
    
    PEKANAN("Pekanan", 7, ChronoUnit.DAYS),
    SEMESTER("Semester", 6, ChronoUnit.MONTHS),
    TRI_WULAN("Tri Wulan", 3, ChronoUnit.MONTHS),
    CATUR_WULAN("Catur Wulan", 3, ChronoUnit.MONTHS),
    BULANAN("Bulanan", 1, ChronoUnit.MONTHS),
    TAHUNAN("Tahunan", 1, ChronoUnit.YEARS);
    
    private final String label;
    private final ChronoUnit unit;
    private final int duration;

    private JenisPeriodePembelajaran(String label, int duration, ChronoUnit unit) {
        this.label = label;
        this.unit = unit;
        this.duration = duration;
    }

    public String getLabel() {
        return label;
    }

    public int getDuration() {
        return this.duration;
    }

    public ChronoUnit getUnit() {
        return unit;
    }
}
