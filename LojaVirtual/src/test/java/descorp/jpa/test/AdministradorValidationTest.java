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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author victor
 */
public class AdministradorValidationTest extends GenericTest{

    @Test(expected = ConstraintViolationException.class)
    public void persistirAdmInvalido() {
        Administrador adm = null;
        try {
            adm = new Administrador();

            adm.setNome("Marcos Brasileiro");
            adm.setEmail("m@gmail.com");
            adm.setCpf("595.436"); //CPF inválido
            adm.setPermissao("Concedida");
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
}
