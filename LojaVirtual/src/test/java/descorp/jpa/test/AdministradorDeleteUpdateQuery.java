/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Administrador;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author root
 */
public class AdministradorDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 2L;
        String novoNome = "Update test";
        Query update = em.createQuery("UPDATE Administrador AS c SET c.nome = ?1 WHERE c.id = ?2");
        update.setParameter(1, novoNome);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM Administrador c WHERE c.id = :id";
        TypedQuery<Administrador> query = em.createQuery(jpql, Administrador.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Administrador adm = query.getSingleResult();
        assertEquals(novoNome, adm.getNome());        
    }
  
    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 4L;
        Query delete = em.createQuery("DELETE FROM Administrador AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM Administrador c WHERE c.id =?1";
        TypedQuery<Administrador> query = em.createQuery(jpql, Administrador.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
