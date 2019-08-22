/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.Pedido;
import exemplo.jpa.Produto;
import exemplo.jpa.TipoProduto;
import java.util.HashMap;
import java.util.List;
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
public class PedidoCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        Pedido p = criarPedido();
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
        assertNotNull(p.getLog());
    }

    @Test
    public void Atualizar() {
        logger.info("Executando atualizar()");
        String log = "23232323";
        int quantidade = 13;

        Integer id = 1;
        Pedido p = em.find(Pedido.class, id);
        p.setLog(log);
        p.setQuantidade(quantidade);

        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        p = em.find(Pedido.class, id, properties);

        assertEquals(log, p.getLog());
        
        assertEquals("13", p.getQuantidade());

        logger.info("Atualizado");
    }

    @Test
    public void atualizarMerge() {
        logger.info("Executando atualizarMerge()");
        String log = "2124b1l2j41bkhj";
        int quantidade = 500;

        Integer id = 1;
        Pedido p = em.find(Pedido.class, id);
        p.setLog(log);
        p.setQuantidade(quantidade);

        em.clear();
        em.merge(p);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        p = em.find(Pedido.class, id, properties);

        assertEquals(log, p.getLog());
        assertEquals("13", p.getQuantidade());
    }

    @Test
    public void remover() {
        logger.info("Executando remover()");
        Pedido p = em.find(Pedido.class, 1);
        em.remove(p);
        Pedido p1 = em.find(Pedido.class, 1);
        assertNull(p1);
    }

    private Pedido criarPedido() {
        Pedido p = new Pedido();
        p.setId(6);
        p.setLog("32klnsfknfjkasdas");
        p.setQuantidade(333);

        return p;
    }

}
