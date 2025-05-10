package id.my.mdn.kupu.app.pengasuhan.dao;

import id.my.mdn.kupu.app.pengasuhan.entity.PengasuhanSantri;
import id.my.mdn.kupu.core.base.dao.AbstractFacade;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 *
 * @author aphasan
 */
@Stateless
public class PengasuhanSantriFacade extends AbstractFacade<PengasuhanSantri> {
    
    @Inject
    private EntityManager em;

    public PengasuhanSantriFacade() {
        super(PengasuhanSantri.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
