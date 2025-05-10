/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.DetailCatatanAbsensiHalaqoh;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.Map;

/**
 *
 * @author aphasan
 */
@Stateless
public class DetailCatatanAbsensiHalaqohFacade extends AbstractSqlFacade<DetailCatatanAbsensiHalaqoh> {

    private static final String DETAIL_CATATAN_ABSENSI_QUERY
            = """
            SELECT BT0.BENTUK, AK1.AKTIFITAS_COUNT, AK1.CONFIRMED
            FROM (    
                SELECT AK0.BENTUKAKTIFITAS_ID, COUNT(AK0.BENTUKAKTIFITAS_ID) AS AKTIFITAS_COUNT, BOOL_AND(AK0.CONFIRMED) AS CONFIRMED
                FROM MIABH_AKTIFITAS AS AK0
                WHERE AK0.SANTRI_ID = ?
                AND AK0.ACTIVITYDATE BETWEEN ? AND ?
                GROUP BY AK0.BENTUKAKTIFITAS_ID
            ) AS AK1
            JOIN MIABH_BENTUKAKTIFITAS AS BT0
            ON AK1.BENTUKAKTIFITAS_ID = BT0.ID
            """;    

    @Inject
    private EntityManager em;

    public DetailCatatanAbsensiHalaqohFacade() {
        super(DetailCatatanAbsensiHalaqoh.class);
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        
        PeriodePembelajaran pp = (PeriodePembelajaran) parameters.get("periodePembelajaran");
        
        q.setParameter(1, parameters.get("santriId"));
        q.setParameter(2, pp.getFromDate());
        q.setParameter(3, pp.getThruDate());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return DETAIL_CATATAN_ABSENSI_QUERY;
    }

}
