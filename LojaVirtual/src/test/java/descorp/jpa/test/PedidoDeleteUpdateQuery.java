/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Pedido;
import descorp.jpa.StatusPedido;
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
public class PedidoDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 4L;        
        Query update = em.createQuery("UPDATE Pedido AS c SET c.status = ?1 WHERE c.id = ?2");
        update.setParameter(1, StatusPedido.ENTREGUE);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM Pedido c WHERE c.id = :id";
        TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Pedido pedido = query.getSingleResult();
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());        
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 5L;
        Query delete = em.createQuery("DELETE FROM Pedido AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM Pedido c WHERE c.id =?1";
        TypedQuery<Pedido> query = em.createQuery(jpql, Pedido.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}

