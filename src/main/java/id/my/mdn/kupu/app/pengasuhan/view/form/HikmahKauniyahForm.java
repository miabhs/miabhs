/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengasuhan.view.form;

import id.my.mdn.kupu.app.pengasuhan.entity.HikmahKauniyah;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormComponent;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "hikmahKauniyahForm")
@Dependent
public class HikmahKauniyahForm extends FormComponent<HikmahKauniyah> {

    @Override
    protected Result<String> checkFormComponentValidity() {
        return new Result<>(true, null);
    }

    @Override
    protected void doPack() {
        
    }
    
    
}
