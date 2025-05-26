/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran.entity;

import java.io.Serializable;

/**
 *
 * @author aphasan
 */
public class PencapaianPembelajaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final Long jenisKitabId;
    private final Long jenisKitabParentId;
    private final String jenisKitabJudul;
    private final Long jenisPengajaranId;
    private final String jenisPengajaranNama;
    private final String pencapaian;
    
    public PencapaianPembelajaran(
            Long jenisKitabId,Long jenisKitabParentId, String jenisKitabJudul,
            Long jenisPengajaranId, String jenisPengajaranNama, 
            String pencapaian
    ) {
        this.jenisPengajaranId = jenisPengajaranId;
        this.jenisPengajaranNama = jenisPengajaranNama;
        this.jenisKitabParentId = jenisKitabParentId;
        this.jenisKitabId = jenisKitabId;
        this.jenisKitabJudul = jenisKitabJudul;
        this.pencapaian = pencapaian;
    }

    public Long getJenisPengajaranId() {
        return jenisPengajaranId;
    }

    public String getJenisPengajaranNama() {
        return jenisPengajaranNama;
    }

    public Long getJenisKitabParentId() {
        return jenisKitabParentId;
    }

    public Long getJenisKitabId() {
        return jenisKitabId;
    }

    public String getJenisKitabJudul() {
        return jenisKitabJudul;
    }

    public String getPencapaian() {
        return pencapaian;
    }
    
}
