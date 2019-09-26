/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import descorp.jpa.CartaoCredito;
import descorp.jpa.ClienteUsuario;
import javax.persistence.CacheRetrieveMode;

/**
 *
 * @author ALUNO
 */
public class CartaoCreditoCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        CartaoCredito p = criarCartao();
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
    }

    @Test
    public void Atualizar() {
        String num = "5472941627962395";

        Long id = 1l;
        CartaoCredito c = em.find(CartaoCredito.class, id);
        c.setNumero(num);

        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        c = em.find(CartaoCredito.class, id, properties);

        assertEquals(num, c.getNumero());

        logger.info("Executando atualizar()");

        logger.info("Atualizado");
    }

    @Test
    public void atualizarMerge() {
        logger.info("Executando atualizarMerge()");
        String num = "5472941627962395";

        Long id = 1l;
        CartaoCredito c = em.find(CartaoCredito.class, id);
        c.setNumero(num);

        em.clear();
        em.merge(c);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        c = em.find(CartaoCredito.class, id, properties);

        assertEquals(num, c.getNumero());
    }

    @Test
    public void removerCartao() {
        logger.info("Executando removerCartao()");
        CartaoCredito c = em.find(CartaoCredito.class, 2l);
        em.remove(c);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CartaoCredito c1 = em.find(CartaoCredito.class, 2l, map);
        assertNull(c1);
    }

    private CartaoCredito criarCartao() {
        CartaoCredito c = new CartaoCredito();
        c.setBandeira("Master");
        Calendar data = Calendar.getInstance();
        data.set(Calendar.YEAR, 2222);
        data.set(Calendar.MONTH, Calendar.AUGUST);
        data.set(Calendar.DAY_OF_MONTH, 10);
        c.setDataExpiracao(data.getTime());
        c.setNumero("5179847543153496");
        c.setUsuario(criarClienteUsuario());
        return c;
    }

    private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setId(4l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("797.141.400-56");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1997);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cliente.setDataNascimento(c.getTime());
        return cliente;
    }

}
