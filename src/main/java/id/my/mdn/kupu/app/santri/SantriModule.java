/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri;

import id.my.mdn.kupu.app.santri.entity.HalaqohPengajaran;
import id.my.mdn.kupu.app.santri.entity.KelompokPengasuhan;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.app.santri.entity.PelaksanaKepengasuhan;
import id.my.mdn.kupu.app.santri.entity.Pengajaran;
import id.my.mdn.kupu.app.santri.entity.PengampuHalaqoh;
import id.my.mdn.kupu.app.santri.entity.Pengasuhan;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.Ustadz;
import id.my.mdn.kupu.core.base.AbstractModule;
import id.my.mdn.kupu.core.party.dao.ElectronicAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipTypeFacade;
import id.my.mdn.kupu.core.party.dao.PartyRoleTypeFacade;
import id.my.mdn.kupu.core.party.dao.PersonIdentityTypeFacade;
import id.my.mdn.kupu.core.party.dao.PostalAddressPurposeTypeFacade;
import id.my.mdn.kupu.core.party.dao.TelecommunicationNumberPurposeTypeFacade;
import id.my.mdn.kupu.core.party.entity.ElectronicAddressPurposeType;
import id.my.mdn.kupu.core.party.entity.PersonIdentityType;
import id.my.mdn.kupu.core.party.entity.PostalAddressPurposeType;
import id.my.mdn.kupu.core.party.entity.TelecommunicationNumberPurposeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santri")
@ApplicationScoped
public class SantriModule extends AbstractModule {

    @Inject
    private PartyRoleTypeFacade partyRoleTypeFacade;
    
    @Inject
    private PartyRelationshipTypeFacade partyRelationshipTypeFacade;
    
    @Inject
    private PersonIdentityTypeFacade personIdentityTypeFacade;
    
    @Inject
    private PostalAddressPurposeTypeFacade postalAddressPurposeTypeFacade;
    
    @Inject
    private TelecommunicationNumberPurposeTypeFacade telecommunicationNumberPurposeTypeFacade;
    
    @Inject
    private ElectronicAddressPurposeTypeFacade electronicAddressPurposeTypeFacade;

    @Override
    protected String getLabel() {
        return "santri.module.title";
    }

    @Override
    protected int getOrder() {
        return 1;
    }

    @Override
    protected void postInit() {
        loadRoleTypes();
        loadIdentityTypes();
        loadContactMechanismPurposeType();
    }

    private void loadRoleTypes() {
        partyRoleTypeFacade.createTypeIfNotExist(Santri.class, "Santri");
        partyRoleTypeFacade.createTypeIfNotExist(Musyrif.class, "Musyrif");
        partyRoleTypeFacade.createTypeIfNotExist(Ustadz.class, "Ustadz");
        partyRoleTypeFacade.createTypeIfNotExist(KelompokPengasuhan.class, "Kelompok Pengasuhan");
        partyRoleTypeFacade.createTypeIfNotExist(HalaqohPengajaran.class, "Halaqoh Pengajaran");
        partyRelationshipTypeFacade.createTypeIfNotExist(Pengasuhan.class, "Pengasuhan");
        partyRelationshipTypeFacade.createTypeIfNotExist(Pengajaran.class, "Pengajaran");
        partyRelationshipTypeFacade.createTypeIfNotExist(PelaksanaKepengasuhan.class, "Pelaksana Kepengasuhan");
        partyRelationshipTypeFacade.createTypeIfNotExist(PengampuHalaqoh.class, "Pengampu Halaqoh");
    }
    
    private void loadIdentityTypes() {
        PersonIdentityType idType = new PersonIdentityType();
        
        idType.setId("ktp");
        idType.setRemarks("KTP");
        personIdentityTypeFacade.createIfNotExist(idType, null);
        
        idType.setId("sim");
        idType.setRemarks("SIM");
        personIdentityTypeFacade.createIfNotExist(idType, null);
    }
    
    private void loadContactMechanismPurposeType() {
        PostalAddressPurposeType postalAddressPurposeType = new PostalAddressPurposeType();
        
        postalAddressPurposeType.setRemarks("Alamat Rumah");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
        
        postalAddressPurposeType.setRemarks("Alamat Kantor");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
        
        postalAddressPurposeType.setRemarks("Alamat Lainnya");
        postalAddressPurposeTypeFacade.createIfNotExist(postalAddressPurposeType, "remarks");
        
        TelecommunicationNumberPurposeType telecommunicationNumberPurposeType = new TelecommunicationNumberPurposeType();
        
        telecommunicationNumberPurposeType.setRemarks("Telepon Rumah");
        telecommunicationNumberPurposeTypeFacade.createIfNotExist(telecommunicationNumberPurposeType, "remarks");
        
        telecommunicationNumberPurposeType.setRemarks("Telepon Kantor");
        telecommunicationNumberPurposeTypeFacade.createIfNotExist(telecommunicationNumberPurposeType, "remarks");
        
        telecommunicationNumberPurposeType.setRemarks("Telepon Seluler");
        telecommunicationNumberPurposeTypeFacade.createIfNotExist(telecommunicationNumberPurposeType, "remarks");
        
        telecommunicationNumberPurposeType.setRemarks("Telepon Lainnya");
        telecommunicationNumberPurposeTypeFacade.createIfNotExist(telecommunicationNumberPurposeType, "remarks");
        
        ElectronicAddressPurposeType electronicAddressPurposeType = new ElectronicAddressPurposeType();
        
        electronicAddressPurposeType.setRemarks("Alamat Email");
        electronicAddressPurposeTypeFacade.createIfNotExist(electronicAddressPurposeType, "remarks");
    }
    
}
