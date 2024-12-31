/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.reporting;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.dao.RangkumanKepengasuhanFacade;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named("laporanKepengasuhanScriptletHelper")
@RequestScoped
public class LaporanKepengasuhanScriptletHelper {
    
    @Inject
    private RangkumanKepengasuhanFacade rangkumanKepengasuhanFacade;
    
    @Inject
    private HikmahKauniyahFacade hikmahKauniyahFacade;

    public RangkumanKepengasuhanFacade getRangkumanKepengasuhanFacade() {
        return rangkumanKepengasuhanFacade;
    }

    public HikmahKauniyahFacade getHikmahKauniyahFacade() {
        return hikmahKauniyahFacade;
    }
}
