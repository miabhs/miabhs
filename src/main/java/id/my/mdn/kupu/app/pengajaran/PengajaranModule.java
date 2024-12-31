/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.pengajaran;

import id.my.mdn.kupu.core.base.AbstractModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "pengajaran")
@ApplicationScoped
public class PengajaranModule extends AbstractModule {    

    @Override
    protected String getLabel() {
        return "pengajaran.module.title";
    }

    @Override
    protected int getOrder() {
        return 3;
    }
    
}
