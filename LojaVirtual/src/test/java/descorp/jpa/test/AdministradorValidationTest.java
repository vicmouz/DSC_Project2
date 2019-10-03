/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Administrador;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author victor
 */
public class AdministradorValidationTest extends GenericTest {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirAdmInvalido() {
        Administrador adm = null;
        try {
            adm = criarADM();            
            adm.setCpf("595.436"); //CPF inválido
            
            em.persist(adm);
            em.flush();
            
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.Administrador.cpf: CPF inválido")
                        )
                );
            }
            assertEquals(1, constraintViolations.size());
            assertNull(adm.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<Administrador> query = em.createQuery("SELECT c FROM Administrador c WHERE c.id = :id", Administrador.class);
        query.setParameter("id", 2L);
        Administrador adm = query.getSingleResult();
        adm.setCpf("cpfInvalido");
        
        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("CPF inválido", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
    
    private Administrador criarADM() {
        Administrador adm = new Administrador();        
        adm.setNome("Marcos Brasileiro");
        adm.setEmail("m@gmail.com");
        adm.setCpf("595.436.610-14");
        adm.setPermissao("Concedida");
        adm.setSenha("aaa22AAA*");
        return adm;
    }
}
