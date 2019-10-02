/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.TamanhoProduto;
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
public class TamanhoProdutoUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 1L; 
        String nome = "EGG";
        Query update = em.createQuery("UPDATE TamanhoProduto AS c SET c.nome = ?1 WHERE c.id = ?2");
        update.setParameter(1, nome);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM TamanhoProduto c WHERE c.id = :id";
        TypedQuery<TamanhoProduto> query = em.createQuery(jpql, TamanhoProduto.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        TamanhoProduto tamanho = query.getSingleResult();
        assertEquals(nome, tamanho.getNome());        
    }

    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<TamanhoProduto> query = em.createQuery("SELECT c FROM TamanhoProduto c WHERE c.id = :id", TamanhoProduto.class);
        query.setParameter("id", 2L);
        TamanhoProduto tamanho = query.getSingleResult();
        tamanho.setNome(null);

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            //assertEquals("Não é um endereço de e-mail", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 3L;
        Query delete = em.createQuery("DELETE FROM TamanhoProduto AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM TamanhoProduto c WHERE c.id =?1";
        TypedQuery<TamanhoProduto> query = em.createQuery(jpql, TamanhoProduto.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
