/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CartaoCredito;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author root
 */
public class CartaoCreditoValidationTest extends GenericTest {
    
    
    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {      
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT c FROM CartaoCredito c WHERE c.id = :id", CartaoCredito.class);
        query.setParameter("id", 2);
        CartaoCredito cartao = query.getSingleResult();        
        cartao.setNumero("123");
        
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
           assertEquals("Número de Cartão de Crédito inválido", violation.getMessage());
           assertEquals(1, ex.getConstraintViolations().size());          
            throw ex;
        }
    }
    
}
