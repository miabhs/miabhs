/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.admin;

import id.my.mdn.kupu.app.pengasuhan.dao.HikmahKauniyahFacade;
import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.app.pengasuhan.view.form.HikmahKauniyahForm;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "hikmahKauniyahEditorPage")
@ConversationScoped
public class HikmahKauniyahEditorPage extends FormPage<HikmahKauniyah> implements Serializable {
    
    @Inject
    private HikmahKauniyahFacade dao;
    
    @Inject
    @Form
    private HikmahKauniyahForm form;
    
    @Override
    public void load() {
        super.load();
        
        form.init(entity);
    }

    @Override
    protected HikmahKauniyah newEntity() {
        return new HikmahKauniyah();
    }
    
    @Override
    public Result<String> save(HikmahKauniyah entity) {        
        return dao.create(entity);        
    }
    
    @Override
    public Result<String> edit(HikmahKauniyah entity) {        
        return dao.edit(entity);        
    }

    public HikmahKauniyahForm getForm() {
        return form;
    }
    
}
