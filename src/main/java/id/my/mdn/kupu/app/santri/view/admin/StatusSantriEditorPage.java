/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.StatusSantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusSantri;
import id.my.mdn.kupu.app.santri.entity.StatusSantriId;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Bookmarked;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "statusSantriEditorPage")
@ConversationScoped
public class StatusSantriEditorPage extends FormPage<StatusSantri> {

    @Inject
    private StatusSantriFacade dao;
    
    @Bookmarked
    private Santri santri;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected StatusSantri newEntity() {
        
        StatusSantri statusSantri = new StatusSantri();
        statusSantri.setId(new StatusSantriId());
        statusSantri.setFromDate(LocalDate.now());
        statusSantri.setSantri(santri);
        
        return statusSantri;
    }

    @Override
    protected Result<String> save(StatusSantri entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(StatusSantri entity) {
        return dao.edit(entity);
    }

    public Santri getSantri() {
        return santri;
    }

    public void setSantri(Santri santri) {
        this.santri = santri;
    }
    
}
