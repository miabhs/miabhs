/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan;

import id.my.mdn.kupu.core.base.AbstractModule;
import id.my.mdn.kupu.core.party.dao.PartyRelationshipTypeFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pengasuhan")
@ApplicationScoped
public class PengasuhanModule extends AbstractModule {
    
    @Inject
    private PartyRelationshipTypeFacade partyRelationshipTypeFacade;

    @Override
    protected String getLabel() {
        return "pengasuhan.module.title";
    }

    @Override
    protected int getOrder() {
        return 2;
    }

    @Override
    protected void postInit() {
        loadRoleTypes();
    }

    private void loadRoleTypes() {
//        partyRelationshipTypeFacade.createRoleTypeIfNotExist(TaskAssignment.class, "Task Assignment");
    }

}
