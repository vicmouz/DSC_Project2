/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.TipoProduto;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author root
 */
public class TipoProdutoDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 1L; 
        String nome = "Bola";
        Query update = em.createQuery("UPDATE TipoProduto AS c SET c.nome = ?1 WHERE c.id = ?2");
        update.setParameter(1, nome);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM TipoProduto c WHERE c.id = :id";
        TypedQuery<TipoProduto> query = em.createQuery(jpql, TipoProduto.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        TipoProduto tipo = query.getSingleResult();
        assertEquals(nome, tipo.getNome());        
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 7L;
        Query delete = em.createQuery("DELETE FROM TipoProduto AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM TamanhoProduto c WHERE c.id =?1";
        TypedQuery<TipoProduto> query = em.createQuery(jpql, TipoProduto.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
