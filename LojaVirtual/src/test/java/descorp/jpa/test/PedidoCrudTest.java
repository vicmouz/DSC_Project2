/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.Pedido;
import descorp.jpa.StatusPedido;
import java.util.Calendar;
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
        

        Long id = 1l;
        Pedido p = em.find(Pedido.class, id);
        p.setLog(log);
        p.setStatus(StatusPedido.ENTREGUE);

        em.flush();

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        p = em.find(Pedido.class, id, properties);

        assertEquals(log, p.getLog());
        
        assertEquals(StatusPedido.ENTREGUE,p.getStatus());

        logger.info("Atualizado");
    }

    @Test
    public void atualizarMerge() {
        logger.info("Executando atualizarMerge()");
        String log = "2124b1l2j41bkhj";
        

        Long id = 1l;
        Pedido p = em.find(Pedido.class, id);
        p.setLog(log);
        p.setStatus(StatusPedido.ENTREGUE);

        em.clear();
        em.merge(p);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        p = em.find(Pedido.class, id, properties);

        assertEquals(log, p.getLog());
        assertEquals(StatusPedido.ENTREGUE,p.getStatus());
    }

    @Test
    public void remover() {
        logger.info("Executando remover()");
        Pedido p = em.find(Pedido.class, 1l);
        em.remove(p);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Pedido p1 = em.find(Pedido.class, 1l, map);
        assertNull(p1);
    }

    private Pedido criarPedido() {
        Pedido p = new Pedido();
        p.setLog("32klnsfknfjkasdas");
        p.setQuantidade(333);
        p.setStatus(StatusPedido.CANCELADO);
        p.setClienteusuario(criarClienteUsuario());
        return p;
    }
    
     private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("113.839.514-54");

        cliente.setId(1l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("113.839.514-54");
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
