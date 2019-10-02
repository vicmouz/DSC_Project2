/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CartaoCredito;
import java.text.SimpleDateFormat;
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
public class CartaoCreditoDeleteUpdateQuery extends GenericTest{
    
    @Test
    public void queryUpdate() {
        Long id = 1L;
        Query update = em.createQuery("UPDATE CartaoCredito AS c SET c.dataExpiracao = ?1 WHERE c.id = ?2");
        update.setParameter(1, getData(10, 01, 2222));
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM CartaoCredito c WHERE c.id = :id";
        TypedQuery<CartaoCredito> query = em.createQuery(jpql, CartaoCredito.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CartaoCredito cartao = query.getSingleResult();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(simpleDateFormat.format(getData(10, 01, 2222)), simpleDateFormat.format(cartao.getDataExpiracao()));
        logger.info(cartao.getDataExpiracao().toString());
    }

    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {      
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT c FROM CartaoCredito c WHERE c.id = :id", CartaoCredito.class);
        query.setParameter("id", 2);
        CartaoCredito cartao = query.getSingleResult();        
        cartao.setDataExpiracao(getData(10, 01, 1900));

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
           assertEquals("deve estar no futuro", violation.getMessage());
           assertEquals(1, ex.getConstraintViolations().size());          
            throw ex;
        }
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 3L;
        Query delete = em.createQuery("DELETE FROM CartaoCredito AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM CartaoCredito c WHERE c.id =?1";
        TypedQuery<CartaoCredito> query = em.createQuery(jpql, CartaoCredito.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}

