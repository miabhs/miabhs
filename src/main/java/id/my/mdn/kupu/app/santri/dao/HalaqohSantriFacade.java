/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.santri.dao;

import id.my.mdn.kupu.app.santri.entity.HalaqohSantri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class HalaqohSantriFacade extends AbstractSqlFacade<HalaqohSantri> {

    private static final String FIND_ALL
            = """
              SELECT HALAQOHID, HALAQOHNAMA, SANTRIID, SANTRINIS, SANTRIFIRSTNAME, SANTRILASTNAME
              FROM
              (SELECT HP0.ID AS HALAQOHID, HP0.NAMA AS HALAQOHNAMA, HS0.SANTRI_ID
              FROM MIABH_HALAQOHSANTRI AS HS0
              LEFT JOIN MIABH_HALAQOHPENGAJARAN AS HP0
              ON HS0.HALAQOHPENGAJARAN_ID = HP0.ID) AS HS1
              LEFT JOIN
              (SELECT SANTRI2.ID AS SANTRIID, SANTRI2.NIS AS SANTRINIS, SANTRI2.FIRSTNAME AS SANTRIFIRSTNAME,
                      SANTRI2.LASTNAME AS SANTRILASTNAME, STATUSSANTRI0.STATUS AS SANTRISTATUS
              FROM (SELECT SANTRI1.ID, SANTRI1.NIS, PERSON1.FIRSTNAME, PERSON1.LASTNAME
                    FROM (SELECT SANTRI0.ID, PARTYROLE0.PARTY_ID, SANTRI0.NIS
                          FROM MIABH_SANTRI AS SANTRI0
                          JOIN PARTY_PARTYROLE AS PARTYROLE0
                          ON SANTRI0.ID = PARTYROLE0.ID
                          WHERE PARTYROLE0.FROMDATE <=  CURRENT_DATE AND (PARTYROLE0.THRUDATE IS NULL OR PARTYROLE0.THRUDATE >= CURRENT_DATE)
                   ) AS SANTRI1
                   JOIN PARTY_PERSON AS PERSON1
                   ON SANTRI1.PARTY_ID = PERSON1.ID
              ) AS SANTRI2
              JOIN MIABH_STATUSSANTRI AS STATUSSANTRI0
              ON SANTRI2.ID = STATUSSANTRI0.SANTRI_ID
              WHERE STATUSSANTRI0.FROMDATE <=  CURRENT_DATE AND (STATUSSANTRI0.THRUDATE IS NULL OR STATUSSANTRI0.THRUDATE >= CURRENT_DATE)) AS SANTRI3
              ON HS1.SANTRI_ID = SANTRI3.SANTRIID
              """;

    @Inject
    private EntityManager em;

    public HalaqohSantriFacade() {
        super(HalaqohSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return FIND_ALL;
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        switch (filterData.name) {
            case "halaqoh":
                return "HALAQOHID = " + filterData.value;
            default:
                return null;
        }
    }

}
