/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.PeriodePembelajaranFacade;
import id.my.mdn.kupu.app.santri.entity.PeriodePembelajaran;
import id.my.mdn.kupu.app.santri.entity.TahunPembelajaran;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan at mdnx.dev>
 */
@Named(value = "periodePembelajaranEditorPage")
@ConversationScoped
public class PeriodePembelajaranEditorPage extends FormPage<PeriodePembelajaran> {

    @Inject
    private PeriodePembelajaranFacade dao;
    
    private TahunPembelajaran tahunPembelajaran;

    private PeriodePembelajaran parent;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected PeriodePembelajaran newEntity() {

        PeriodePembelajaran periodePembelajaran = new PeriodePembelajaran();
        
        periodePembelajaran.setTahunPembelajaran(tahunPembelajaran);

        if (parent != null) {
            periodePembelajaran.setParent(parent);
            parent.getChildren().add(periodePembelajaran);
        }

        return periodePembelajaran;
    }

    @Override
    protected Result<String> save(PeriodePembelajaran entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(PeriodePembelajaran entity) {
        return dao.edit(entity);
    }

    public TahunPembelajaran getTahunPembelajaran() {
        return tahunPembelajaran;
    }

    public void setTahunPembelajaran(TahunPembelajaran tahunPembelajaran) {
        this.tahunPembelajaran = tahunPembelajaran;
    }

    public PeriodePembelajaran getParent() {
        return parent;
    }

    public void setParent(PeriodePembelajaran parent) {
        this.parent = parent;
    }

}
