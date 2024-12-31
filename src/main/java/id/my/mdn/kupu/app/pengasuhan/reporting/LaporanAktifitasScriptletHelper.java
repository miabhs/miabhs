/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.AktifitasFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.santri.dao.KakakKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.KelompokPengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.PembantuPelaksanaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.PembinaKepengasuhanFacade;
import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.view.converter.IslamicDateConverter;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "laporanAktifitasScriptletHelper")
@RequestScoped
public class LaporanAktifitasScriptletHelper {

    @Inject
    private KelompokPengasuhanFacade kelompokPengasuhanFacade;
    
    @Inject
    private PembinaKepengasuhanFacade pembinaFacade;
    
    @Inject
    private PembantuPelaksanaKepengasuhanFacade pembantuFacade;
    
    @Inject
    private KakakKepengasuhanFacade kakakFacade;
    
    @Inject
    private SantriFacade santriFacade;
    
    @Inject
    private AktifitasFacade aktifitasFacade;
    
    @Inject
    private HikmahKauniyahFacade hikmahKauniyahFacade;
    
    @Inject @FacesConverter(value = "IslamicDateConverter", managed = true)
    private IslamicDateConverter dateConverter;

    public KelompokPengasuhanFacade getKelompokPengasuhanFacade() {
        return kelompokPengasuhanFacade; 
    }

    public PembinaKepengasuhanFacade getPembinaFacade() {
        return pembinaFacade;
    }

    public PembantuPelaksanaKepengasuhanFacade getPembantuFacade() {
        return pembantuFacade;
    }

    public KakakKepengasuhanFacade getKakakFacade() {
        return kakakFacade;
    }

    public IslamicDateConverter getDateConverter() {
        return dateConverter;
    }

    public SantriFacade getSantriFacade() {
        return santriFacade;
    }

    public AktifitasFacade getAktifitasFacade() {
        return aktifitasFacade;
    }

    public HikmahKauniyahFacade getHikmahKauniyahFacade() {
        return hikmahKauniyahFacade;
    }
    
}
