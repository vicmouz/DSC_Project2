/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.TipoProduto;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author root
 */
public class TipoProdutoValidationTest extends GenericTest {
            
   @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<TipoProduto> query = em.createQuery("SELECT c FROM TipoProduto c WHERE c.id = :id", TipoProduto.class);
        query.setParameter("id", 3L);
        TipoProduto tipo = query.getSingleResult();
        tipo.setNome("");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Não pode estar em branco", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}