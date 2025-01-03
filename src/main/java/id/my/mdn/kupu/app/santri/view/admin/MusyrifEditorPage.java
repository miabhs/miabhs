/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.MusyrifFacade;
import id.my.mdn.kupu.app.santri.entity.Musyrif;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.form.PersonEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "musyrifEditorPage")
@ConversationScoped
public class MusyrifEditorPage extends FormPage<Musyrif> {

    @Inject
    private MusyrifFacade dao;
    
    @Inject @Form
    private PersonEditorForm form;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getPerson());
//        form.setValidityChecker(this::checkValid);
//        form.setPacker(this::doPack);
    }

//    private ResultPair<Boolean, String> checkValid() {
//        ResultPair<Boolean, String> validity = form.getPersonForm().getValidityChecker().isValid();
//        return validity;
//    }

//    private void doPack() {
//        form.getPersonForm().getPacker().pack();
//    }

    @Override
    protected Musyrif newEntity() {
        Person person = Person.builder().get();
        
        return Musyrif.builder()
                .withPerson(person)
                .get();
    }

    @Override
    protected Result<String> save(Musyrif entity) {
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Musyrif entity) {
        return dao.edit(entity);
    }

    public PersonEditorForm getForm() {
        return form;
    }
    
}