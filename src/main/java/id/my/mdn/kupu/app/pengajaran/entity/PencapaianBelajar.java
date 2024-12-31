/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import id.my.mdn.kupu.app.santri.entity.JenisKitab;
import id.my.mdn.kupu.app.santri.entity.JenisPengajaran;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
public class PencapaianBelajar {
    
    private JenisPengajaran jenisPengajaran;
    
    private JenisKitab jenisKitab;
    
    private String notes;

    public PencapaianBelajar() {
    }

    public PencapaianBelajar(Long jenisPengajaranId, String jenisPengajaranKode, String jenisPengajaranNama,
            Long jenisKitabId, String jenisKitabJudul, String notes) {
        this.jenisPengajaran = new JenisPengajaran();
        this.jenisPengajaran.setId(jenisPengajaranId);
        this.jenisPengajaran.setKode(jenisPengajaranKode);
        this.jenisPengajaran.setNama(jenisPengajaranNama);
        
        this.jenisKitab = new JenisKitab();
        this.jenisKitab.setId(jenisKitabId);
        this.jenisKitab.setJudul(jenisKitabJudul);
        
        this.notes = notes;
    }

    public JenisPengajaran getJenisPengajaran() {
        return jenisPengajaran;
    }

    public JenisKitab getJenisKitab() {
        return jenisKitab;
    }

    public String getNotes() {
        return notes;
    }
    
}
