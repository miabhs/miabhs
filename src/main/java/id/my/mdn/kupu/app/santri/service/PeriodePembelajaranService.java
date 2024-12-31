/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.service;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class PeriodePembelajaranService {
    
    @Inject
    private PeriodePembelajaranFacade periodePembelajaranFacade; 

    public List<PeriodePembelajaran> findPeriode(PeriodePembelajaran parent) {
        
        List<PeriodePembelajaran> listPeriodePembelajaran = 
                periodePembelajaranFacade.getChildren(parent);
        
        return listPeriodePembelajaran;
    }
}
