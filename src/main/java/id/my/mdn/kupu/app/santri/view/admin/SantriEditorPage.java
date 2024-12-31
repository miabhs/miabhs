/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package id.my.mdn.kupu.app.santri.view.admin;

import id.my.mdn.kupu.app.santri.dao.SantriFacade;
import id.my.mdn.kupu.app.santri.entity.Santri;
import id.my.mdn.kupu.app.santri.entity.StatusKesantrian;
import id.my.mdn.kupu.app.santri.entity.StatusSantri;
import id.my.mdn.kupu.app.santri.entity.StatusSantriId;
import id.my.mdn.kupu.core.base.util.Result;
import id.my.mdn.kupu.core.base.view.FormPage;
import id.my.mdn.kupu.core.base.view.annotation.Form;
import id.my.mdn.kupu.core.party.entity.Person;
import id.my.mdn.kupu.core.party.view.form.PersonEditorForm;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

/**
 *
 * @author Arief Prihasanto <aphasan57 at gmail.com>
 */
@Named(value = "santriEditorPage")
@ConversationScoped
public class SantriEditorPage extends FormPage<Santri> {

    @Inject
    private SantriFacade dao;

    @Inject @Form
    private PersonEditorForm form;

    @Override
    public void load() {
        super.load();
        form.init(getEntity().getPerson());
    }

    @Override
    protected Santri newEntity() {
        Person person = Person.builder()
//                .identity(new PersonIdentity())
//                .postalAddress(new PostalAddress())
//                .telecommunicationNumber(new TelecommunicationNumber())
//                .electronicAddress(new ElectronicAddress())
                .get();
        
        return Santri.builder()
                .withPerson(person)
                .get();
    }

    @Override
    protected Result<String> save(Santri entity) {
        StatusSantri statusSantri = new StatusSantri();
        statusSantri.setId(new StatusSantriId());
        statusSantri.setFromDate(entity.getTahunMasuk().getFromDate());
        statusSantri.setSantri(entity);
        statusSantri.setStatus(StatusKesantrian.ACTIVE);
        entity.setListStatus(List.of(statusSantri));        
        
        return dao.create(entity);
    }

    @Override
    protected Result<String> edit(Santri entity) {
        return dao.edit(entity);
    }

    public PersonEditorForm getForm() {
        return form;
    }

}
