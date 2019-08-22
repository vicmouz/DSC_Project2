/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.EnderecoCliente;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author marcosbrasil98
 */
public class EnderecoClienteCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistirEndereco()");
        EnderecoCliente ec = criarEndereco();
        em.persist(ec);
        em.flush();
        assertNotNull(ec.getId());
        assertNotNull(ec.getNome());
    }

    @Test
    public void AtualizarEndereco() {
        logger.info("Executando atualizarEndereco()");
        String nome = "Avenida Boa viagem";
        String numero = "1000";
        String bairro = "Boa Viagem";

        Integer id = 1;
        EnderecoCliente ec = em.find(EnderecoCliente.class, id);
        ec.setNome(nome);
        ec.setNumero(numero);
        ec.setBairro(bairro);
        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        ec = em.find(EnderecoCliente.class, id, properties);
        //logger.info("Atualizado");

        assertEquals(nome, ec.getNome());
        assertEquals(numero, ec.getNumero());
        assertEquals(bairro, ec.getBairro());
    }

    @Test
    public void atualizarEnderecoMerge() {
        logger.info("Executando atualizarEnderecoMerge()");
        String nome = "Avenida Recife";
        String numero = "500";
        String bairro = "ipsep";

        Integer id = 1;
        EnderecoCliente ec = em.find(EnderecoCliente.class, id);
        ec.setNome(nome);
        ec.setNumero(numero);
        ec.setBairro(bairro);

        em.clear();
        em.merge(ec);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        ec = em.find(EnderecoCliente.class, id, properties);

        assertEquals(nome, ec.getNome());
        assertEquals(numero, ec.getNumero());
        assertEquals(bairro, ec.getBairro());
        /*if(ec.getNome().contains(nome)){
            System.out.println("Possui nome!");
        }*/
    }

    @Test
    public void remover() {
        logger.info("Executando remover()");
        EnderecoCliente ec = em.find(EnderecoCliente.class, 2);
        em.remove(ec);
        EnderecoCliente ec1 = em.find(EnderecoCliente.class, 2);
        assertNull(ec1);
    }

    private EnderecoCliente criarEndereco() {
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        return ec;
    }
}
