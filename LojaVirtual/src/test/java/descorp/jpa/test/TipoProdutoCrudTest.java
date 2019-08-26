/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.TipoProduto;
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
public class TipoProdutoCrudTest extends GenericTest {

    @Test
    public void persistirTipoProduto() {
        logger.info("Executando persistirTipoProduto()");
        TipoProduto tp = criarTP();
        em.persist(tp);
        em.flush();
        assertNotNull(tp.getId());
        assertNotNull(tp.getNome());
    }

    @Test
    public void AtualizarTipoProduto() {
        logger.info("Executando atualizarTipoProduto()");
        String nome = "Brinquedo";

        Integer id = 2;
        TipoProduto tp = em.find(TipoProduto.class, id);
        tp.setNome(nome);

        em.flush();
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        tp = em.find(TipoProduto.class, id, properties);
        assertEquals(nome, tp.getNome());
        //logger.info("Atualizado");
    }

    @Test
    public void atualizarTPMerge() {
        logger.info("Executando atualizarTPMerge()");
        String nome = "Beyblade";
        Integer id = 2;
        TipoProduto tp = em.find(TipoProduto.class, id);
        tp.setNome(nome);

        em.clear();
        em.merge(tp);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        /*tp = em.find(TipoProduto.class, id, properties);
        assertEquals(nome, tp.getNome());
        if (tp.getNome().contains(nome)) {
            System.out.println("Possui nome!");
        }*/
    }

    @Test
    public void removerTipoProduto() {
        logger.info("Executando removerTipoProduto()");
        TipoProduto tp = em.find(TipoProduto.class, 2);
        em.remove(tp);
        TipoProduto tp1 = em.find(TipoProduto.class, 2);
        assertNull(tp1);
    }

    private TipoProduto criarTP() {
        TipoProduto tp = new TipoProduto();
        tp.setNome("BOLSA DE COURO");
        return tp;
    }

}