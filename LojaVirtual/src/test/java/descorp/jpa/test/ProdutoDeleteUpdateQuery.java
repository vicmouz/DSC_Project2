/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Produto;
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
public class ProdutoDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 1L; 
        Integer quantidade = 50;
        Query update = em.createQuery("UPDATE Produto AS c SET c.quantidade = ?1 WHERE c.id = ?2");
        update.setParameter(1, quantidade);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM Produto c WHERE c.id = :id";
        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Produto produto = query.getSingleResult();
        assertEquals(quantidade, produto.getQuantidade());        
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 2L;
        Query delete = em.createQuery("DELETE FROM Produto AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM Produto c WHERE c.id =?1";
        TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
