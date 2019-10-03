/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CartaoCredito;
import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author root
 */
public class ClienteUsuarioValidationTest extends GenericTest {
    
    
    @Test(expected = ConstraintViolationException.class)
    public void persistirClienteInvalido() {
      ClienteUsuario cliente = null;
      
        try {
            cliente = criarClienteUsuario();            
            cliente.setCpf("595.436"); //CPF inválido
            
            em.persist(cliente);
            em.flush();
            
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.ClienteUsuario.cpf: CPF inválido"),
                                startsWith("class descorp.jpa.ClienteUsuario.senha: A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.")                                
                        )
                );
            }
            assertEquals(2, constraintViolations.size());
            assertNull(cliente.getId());
            throw ex;
        }
    }
    
    
    @Test(expected = ConstraintViolationException.class)
    public void invalidQueryUpdate() {
        TypedQuery<ClienteUsuario> query = em.createQuery("SELECT c FROM ClienteUsuario c WHERE c.id = :id", ClienteUsuario.class);
        query.setParameter("id", 8L);
        ClienteUsuario cliente = query.getSingleResult();
        cliente.setEmail("emailInvalido");
        cliente.setEndereco(this.criarEndereco());

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("Não é um endereço de e-mail"),
                                startsWith("Validação País")
                        )
                );
            }

            assertEquals(1, constraintViolations.size());

            throw ex;
        }
    }
    
     private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();        
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("100.639.154-13");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1990);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cliente.setDataNascimento(c.getTime());
        cliente.setEndereco(criarEndereco());
        CartaoCredito cartaoCredito = criarCartaoCredito();
        cliente.setCartaoCredito(cartaoCredito);
        cliente.setSenha("aaa22AAA");

        return cliente;
    }

    private EnderecoCliente criarEndereco() {
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setBairro("São Paulo");
        ec.setCep("50.144");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        ec.setNumero("580");
        return ec;
    }
    
    public CartaoCredito criarCartaoCredito() {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("5131185499968808");
        return cartaoCredito;
    }
    
}
