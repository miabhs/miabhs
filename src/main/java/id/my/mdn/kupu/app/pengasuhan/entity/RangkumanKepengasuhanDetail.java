/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.entity;

import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class RangkumanKepengasuhanDetail implements Serializable {
    private final Long santriId;
    private final String catatan;
    private final String jenis;

    public RangkumanKepengasuhanDetail(Long santriId, String catatan, String jenis) {
        this.santriId = santriId;
        this.catatan = catatan;
        this.jenis = jenis;
    }

    public Long getSantriId() {
        return santriId;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getJenis() {
        return jenis;
    }

    @Override
    public String toString() {
        return "RangkumanKepengasuhanDetail{" + "santriId=" + santriId + ", catatan=" + catatan + ", jenis=" + jenis + '}';
    }
}
