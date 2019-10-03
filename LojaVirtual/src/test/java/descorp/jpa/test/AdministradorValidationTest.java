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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author victor
 */
public class AdministradorValidationTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.OFF);
        emf = Persistence.createEntityManagerFactory("lojaVirtual");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
    }

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

    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<Administrador> query = em.createQuery("SELECT c FROM Administrador c WHERE c.id = :id", Administrador.class);
        query.setParameter("id", 2);
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

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 4L;
        Query delete = em.createQuery("DELETE FROM Administrador AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM Administrador c WHERE c.id =?1";
        TypedQuery<Administrador> query = em.createQuery(jpql, Administrador.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }

}
