/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CorProduto;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author root
 */
public class CorProdutoDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 2L;
        String novoNome = "Prateado";
        Query update = em.createQuery("UPDATE CorProduto AS c SET c.nome = ?1 WHERE c.id = ?2");
        update.setParameter(1, novoNome);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM CorProduto c WHERE c.id = :id";
        TypedQuery<CorProduto> query = em.createQuery(jpql, CorProduto.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CorProduto cor = query.getSingleResult();
        assertEquals(novoNome, cor.getNome());        
    }

    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<CorProduto> query = em.createQuery("SELECT c FROM CorProduto c WHERE c.id = :id", CorProduto.class);
        query.setParameter("id", 2);
        CorProduto cor = query.getSingleResult();
        cor.setNome("");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Não pode estar em branco", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 1L;
        Query delete = em.createQuery("DELETE FROM CorProduto AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM CorProduto c WHERE c.id =?1";
        TypedQuery<CorProduto> query = em.createQuery(jpql, CorProduto.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
