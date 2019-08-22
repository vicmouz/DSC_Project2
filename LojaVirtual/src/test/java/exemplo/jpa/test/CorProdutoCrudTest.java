/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.CorProduto;
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
public class CorProdutoCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        CorProduto cp = criarCP();
        em.persist(cp);
        em.flush();
        assertNotNull(cp.getId());
        assertNotNull(cp.getNome());
    }

    @Test
    public void Atualizar() {
        logger.info("Executando atualizar()");
        String nome = "VIOLETA";
        Integer id = 1;
        CorProduto cp = em.find(CorProduto.class, id);
        cp.setNome(nome);

        em.flush();
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        cp = em.find(CorProduto.class, id, properties);
        
        assertEquals(nome, cp.getNome());
        //logger.info("Atualizado");
    }

    @Test
    public void atualizarMerge() {
        logger.info("Executando atualizarMerge()");
        String nome = "Azul claro";

        Integer id = 1;
        CorProduto cp = em.find(CorProduto.class, id);
        cp.setNome(nome);

        em.clear();
        em.merge(cp);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        cp = em.find(CorProduto.class, id, properties);
        assertEquals(nome, cp.getNome());
        /*if (cp.getNome().contains(nome)) {
            System.out.println("Possui nome!");
        }*/
    }

    @Test
    public void remover() {
        logger.info("Executando remover()");
        CorProduto cp = em.find(CorProduto.class, 1);
        em.remove(cp);
        CorProduto cp1 = em.find(CorProduto.class, 1);
        assertNull(cp1);
    }

    public CorProduto criarCP() {
        CorProduto cp = new CorProduto();
        cp.setNome("Azul Escuro");
        cp.setTipo("Camisa");
        return cp;
    }

}
