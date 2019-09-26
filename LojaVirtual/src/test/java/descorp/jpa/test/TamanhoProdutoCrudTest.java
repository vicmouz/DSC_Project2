package descorp.jpa.test;

import descorp.jpa.TamanhoProduto;
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
public class TamanhoProdutoCrudTest extends GenericTest{
     
    @Test
    public void persistirTamanhoProduto(){
       logger.info("Executando persistirTamanhoProduto()");
        TamanhoProduto tp = criarTP();
        em.persist(tp);
        em.flush();
        assertNotNull(tp.getId());
        assertNotNull(tp.getNome()); 
    }
    
    @Test
    public void AtualizarTamanhoProduto(){
        logger.info("Executando atualizarTamanhoProduto()");
        String nome = "Enorme";
        
        Long id = 1l;
        TamanhoProduto tp = em.find(TamanhoProduto.class, id);
        tp.setNome(nome);
        em.flush();
        em.clear();
        Map<String, Object> map = new HashMap<>();
        map.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        tp = em.find(TamanhoProduto.class, id, map);
        assertEquals(nome, tp.getNome());
        logger.info("Atualizado");
    }
     @Test
    public void atualizarTPMerge() {
        logger.info("Executando atualizarTPMerge()");
        String nome="Minúsculo";
        Long id = 1l;
       TamanhoProduto tp = em.find(TamanhoProduto.class, id);
        tp.setNome(nome);
        
        em.clear();
        em.merge(tp);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        tp = em.find(TamanhoProduto.class, id, properties);
        assertEquals(nome, tp.getNome());
    }
    
    @Test
    public void removerTamanhoProduto() {
        logger.info("Executando removerTamanhoProduto()");
        TamanhoProduto tp = em.find(TamanhoProduto.class, 2l);
        em.remove(tp);
        em.flush();
        em.clear();        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);        
        TamanhoProduto tp1 = em.find(TamanhoProduto.class, 2l, properties);
        assertNull(tp1);
    }

    private TamanhoProduto criarTP() {
    TamanhoProduto tp = new TamanhoProduto();
    tp.setNome("Médio");
    tp.setAltura(84.3);
    tp.setComprimento(28.9);
    tp.setLargura(12.1);
    tp.setTipo("caneca");
    return tp;
    }

}