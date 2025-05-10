/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.core.base.dao.AbstractSqlFacade;
import id.my.mdn.kupu.core.base.util.FilterTypes.FilterData;
import id.my.mdn.kupu.core.party.entity.GenderType;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Stateless
public class HikmahKauniyahFacade extends AbstractSqlFacade<HikmahKauniyah> {

    private static final String FINDALL
            = """
            SELECT HK6.ID, HK6.CONTENT, HK6.EVENT_DATE, HK6.CREATED,
                   HK6.SANTRI_ID, HK6.PARTY_ID, HK6.SANTRI_NAME, HK6.SANTRI_GENDER, 
                   HK6.KELOMPOKPENGASUHAN_ID, P.FIRSTNAME AS KELOMPOKPENGASUHAN_NAME
            FROM (    
                SELECT HK5.ID, HK5.CONTENT, HK5.EVENT_DATE, HK5.CREATED,
                       HK5.SANTRI_ID, HK5.PARTY_ID, HK5.SANTRI_NAME, HK5.SANTRI_GENDER, 
                       HK5.KELOMPOKPENGASUHAN_ID, ROL.PARTY_ID AS KELOMPOKPENGASUHAN_ORG_ID
                FROM (    
                    SELECT HK4.ID, HK4.CONTENT, HK4.EVENT_DATE, HK4.CREATED,
                           HK4.SANTRI_ID, HK4.PARTY_ID, HK4.SANTRI_NAME, HK4.SANTRI_GENDER, 
                           HK4.KELOMPOKPENGASUHAN_ID
                    FROM (
                        SELECT HK3.ID, HK3.CONTENT, HK3.EVENT_DATE, HK3.CREATED,
                               HK3.SANTRI_ID, HK3.PARTY_ID, HK3.SANTRI_NAME, HK3.SANTRI_GENDER, 
                               PS0.KELOMPOKPENGASUHAN_ID
                        FROM (
                            SELECT HK2.ID, HK2.CONTENT, HK2.EVENT_DATE, HK2.CREATED,
                                   HK2.SANTRI_ID, HK2.PARTY_ID, P.SANTRI_NAME, P.SANTRI_GENDER
                            FROM (    
                                SELECT HK1.ID, HK1.CONTENT, HK1.EVENT_DATE, HK1.CREATED,
                                       HK1.SANTRI_ID, HK1.PARTY_ID
                                FROM (
                                    SELECT HK0.ID, HK0.CONTENT, HK0.EVENT_DATE, HK0.CREATED,
                                           HK0.SANTRI_ID, PR0.PARTY_ID
                                    FROM (
                                        SELECT HK.ID, HK.CONTENT, HK.EVENT_DATE, HK.SANTRI_ID, HK.CREATED
                                        FROM MIABH_HIKMAHKAUNIYAH AS HK
                                        WHERE HK.EVENT_DATE >= ? AND HK.EVENT_DATE <= ?
                                    ) AS HK0
                                    LEFT JOIN PARTY_PARTYROLE AS PR0
                                    ON HK0.SANTRI_ID = PR0.ID
                                ) AS HK1
                            ) AS HK2
                            LEFT JOIN (
                                SELECT PN.ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS SANTRI_NAME, PN.GENDER AS SANTRI_GENDER
                                FROM PARTY_PERSON AS PN
                                JOIN PARTY_PARTY AS PY
                                ON PN.ID = PY.ID
                            ) AS P
                            ON HK2.PARTY_ID = P.ID
                        ) AS HK3
                        LEFT JOIN (
                            SELECT PS.FROMROLE_ID AS KELOMPOKPENGASUHAN_ID, PS.TOROLE_ID
                            FROM MIABH_PENGASUHAN AS PS
                            JOIN (
                                SELECT RELX.FROMROLE_ID, RELX.TOROLE_ID, RELX.FROMDATE
                                FROM PARTY_PARTYRELATIONSHIP AS RELX
                                WHERE RELX.FROMDATE <= ? AND (RELX.THRUDATE IS NULL OR RELX.THRUDATE >= ?)
                                AND RELX.PARTYRELATIONSHIPTYPE_ID = 'Pengasuhan'
                            ) AS REL
                            ON PS.FROMROLE_ID = REL.FROMROLE_ID AND PS.TOROLE_ID = REL.TOROLE_ID AND PS.FROMDATE = REL.FROMDATE
                        ) AS PS0
                        ON HK3.SANTRI_ID = PS0.TOROLE_ID
                    ) AS HK4
                ) AS HK5
                LEFT JOIN PARTY_PARTYROLE AS ROL
                ON HK5.KELOMPOKPENGASUHAN_ID = ROL.ID
            ) AS HK6
            LEFT JOIN PARTY_PARTY AS P
            ON HK6.KELOMPOKPENGASUHAN_ORG_ID = P.ID
            """;

    private static final String FIND
            = """
              SELECT HK6.ID, HK6.CONTENT, HK6.EVENT_DATE, HK6.CREATED,
                     HK6.SANTRI_ID, HK6.PARTY_ID, HK6.SANTRI_NAME, HK6.SANTRI_GENDER, 
                     HK6.KELOMPOKPENGASUHAN_ID, P.FIRSTNAME AS KELOMPOKPENGASUHAN_NAME
              FROM (    
                  SELECT HK5.ID, HK5.CONTENT, HK5.EVENT_DATE, HK5.CREATED,
                         HK5.SANTRI_ID, HK5.PARTY_ID, HK5.SANTRI_NAME, HK5.SANTRI_GENDER,
                         HK5.KELOMPOKPENGASUHAN_ID, ROL.PARTY_ID AS KELOMPOKPENGASUHAN_ORG_ID
                  FROM (    
                      SELECT HK4.ID, HK4.CONTENT, HK4.EVENT_DATE, HK4.CREATED,
                             HK4.SANTRI_ID, HK4.PARTY_ID, HK4.SANTRI_NAME, HK4.SANTRI_GENDER, 
                             HK4.KELOMPOKPENGASUHAN_ID
                      FROM (
                          SELECT HK3.ID, HK3.CONTENT, HK3.EVENT_DATE, HK3.CREATED,
                                 HK3.SANTRI_ID, HK3.PARTY_ID, HK3.SANTRI_NAME, HK3.SANTRI_GENDER,
                                 (
                                  SELECT RELX.FROMROLE_ID
                                  FROM PARTY_PARTYRELATIONSHIP AS RELX
                                  WHERE RELX.FROMDATE <= HK3.EVENT_DATE AND (RELX.THRUDATE IS NULL OR RELX.THRUDATE >= HK3.EVENT_DATE)
                                  AND RELX.PARTYRELATIONSHIPTYPE_ID = 'Pengasuhan' AND RELX.TOROLE_ID = HK3.SANTRI_ID
                                 ) AS KELOMPOKPENGASUHAN_ID
                          FROM (
                              SELECT HK2.ID, HK2.CONTENT, HK2.EVENT_DATE, HK2.CREATED,
                                     HK2.SANTRI_ID, HK2.PARTY_ID,  P.SANTRI_NAME, P.SANTRI_GENDER
                              FROM (    
                                  SELECT HK1.ID, HK1.CONTENT, HK1.EVENT_DATE, HK1.CREATED,
                                         HK1.SANTRI_ID, HK1.PARTY_ID
                                  FROM (
                                      SELECT HK0.ID, HK0.CONTENT, HK0.EVENT_DATE, HK0.CREATED,
                                             HK0.SANTRI_ID, PR0.PARTY_ID
                                      FROM (
                                          SELECT HK.ID, HK.CONTENT, HK.EVENT_DATE, HK.SANTRI_ID, HK.CREATED
                                          FROM MIABH_HIKMAHKAUNIYAH AS HK
                                          WHERE HK.ID = ?
                                      ) AS HK0
                                      LEFT JOIN PARTY_PARTYROLE AS PR0
                                      ON HK0.SANTRI_ID = PR0.ID
                                  ) AS HK1
                              ) AS HK2
                              LEFT JOIN (
                                SELECT PN.ID, CONCAT_WS(' ', PY.FIRSTNAME, PY.LASTNAME) AS SANTRI_NAME, PN.GENDER AS SANTRI_GENDER
                                FROM PARTY_PERSON AS PN
                                JOIN PARTY_PARTY AS PY
                                ON PN.ID = PY.ID
                              ) AS P
                              ON HK2.PARTY_ID = P.ID
                          ) AS HK3
                      ) AS HK4
                  ) AS HK5
                  LEFT JOIN PARTY_PARTYROLE AS ROL
                  ON HK5.KELOMPOKPENGASUHAN_ID = ROL.ID
              ) AS HK6
              LEFT JOIN PARTY_PARTY AS P
              ON HK6.KELOMPOKPENGASUHAN_ORG_ID = P.ID
              """;

    @Inject
    private EntityManager em;

    public HikmahKauniyahFacade() {
        super(HikmahKauniyah.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected String getFindAllQuery() {
        return FINDALL;
    }

    @Override
    protected String getFindQuery() {
        return FIND;
    }

    @Override
    protected void setParameters(Query q, Map<String, Object> parameters) {
        q.setParameter(1, parameters.get("fromDate"));
        q.setParameter(2, parameters.get("thruDate"));
        q.setParameter(3, parameters.get("fromDate"));
        q.setParameter(4, parameters.get("fromDate"));
    }

    @Override
    protected String applyFilter(FilterData filterData) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        switch (filterData.name) {
            case "id":
                return "HK6.ID = '" + filterData.value + "'";
            case "santriName":
                String nameQuery = filterData.value != null ? (String) filterData.value : "";
                if (nameQuery.equals("")) {
                    return null;
                }
                return "(UPPER(SANTRI_NAME) LIKE '%" + nameQuery.toUpperCase() + "%')";
            case "gender":
                GenderType gender = (GenderType) filterData.value;
                return "(SANTRI_GENDER = '" + gender.name() + "' OR SANTRI_GENDER IS NULL)";
            case "santri":
                Santri santri = (Santri) filterData.value;
                return "HK6.SANTRI_ID = " + santri.getId();
            case "santriId":
                Long santriId = (Long) filterData.value;
                return "HK6.SANTRI_ID = " + santriId;
            case "kelompokPengasuhan":
                KelompokPengasuhan kelompokPengasuhan = (KelompokPengasuhan) filterData.value;
                return "HK6.KELOMPOKPENGASUHAN_ID = " + kelompokPengasuhan.getId();
            default:
                return null;
        }
    }

    @Override
    protected String translateOrderField(String fieldName) {
        switch (fieldName) {
            case "date":
                return "EVENT_DATE";
            case "santriName":
                return "SANTRI_NAME";
            case "created":
                return "CREATED";
            default:
                return super.translateOrderField(fieldName);
        }
    }
    
    public void createBlank(LocalDate date) {        
        HikmahKauniyah entity = new HikmahKauniyah();
        entity.setDate(date);
        create(entity);
    }

}
