/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.santri.entity;

import id.my.mdn.kupu.core.party.entity.Person;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
//@Entity
//@SqlResultSetMappings({
//    @SqlResultSetMapping(
//            name = "HalaqohSantri",
//            classes = {
//                @ConstructorResult(
//                        targetClass = HalaqohSantri.class,
//                        columns = {
//                            @ColumnResult(name = "halaqohId", type = Long.class),
//                            @ColumnResult(name = "halaqohNama", type = String.class),
//                            @ColumnResult(name = "santriId", type = Long.class),
//                            @ColumnResult(name = "santriNis", type = String.class),
//                            @ColumnResult(name = "santriFirstName", type = String.class),
//                            @ColumnResult(name = "santriLastName", type = String.class),
//                        }
//                )
//            }
//    )
//})
public class HalaqohSantri implements Serializable {

    private static final long serialVersionUID = 1L;

//    @EmbeddedId
    private HalaqohSantriId id;

//    @ManyToOne
//    @MapsId("halaqohPengajaran")
    private HalaqohPengajaran halaqohPengajaran;

//    @ManyToOne
//    @MapsId("santri")
    private Santri santri;

    public HalaqohSantri() {
    }

    public HalaqohSantri(HalaqohPengajaran halaqohPengajaran, Santri santri) {
        this.halaqohPengajaran = halaqohPengajaran;
        this.santri = santri;
    }

    public HalaqohSantri(Long halaqohId, String halaqohNama, Long santriId, String santriNis,
            String santriFirstName, String santriLastName) {
        HalaqohPengajaran halaqoh = new HalaqohPengajaran();
        halaqoh.setId(halaqohId);
        halaqoh.setNama(halaqohNama);
        this.halaqohPengajaran = halaqoh;
        
        Santri s = new Santri();
        s.setId(santriId);
        s.setNis(santriNis);
        s.setPerson(new Person());
        s.getPerson().setFirstName(santriFirstName);
        s.getPerson().setLastName(santriLastName);
        this.santri = s;
    }

    public HalaqohSantriId getId() {
        return id;
    }

    public void setId(HalaqohSantriId id) {
        this.id = id;
    }

    public HalaqohPengajaran getHalaqohPengajaran() {
        return halaqohPengajaran;
    }

    public void setHalaqohPengajaran(HalaqohPengajaran halaqohPengajaran) {
        this.halaqohPengajaran = halaqohPengajaran;
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }

}
