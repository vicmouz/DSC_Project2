/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        double largura = 93.5;
        
        Integer id = 1;
        TamanhoProduto tp = em.find(TamanhoProduto.class, id);
        tp.setNome(nome);
        tp.setLargura(largura);
        em.flush(); 
        assertEquals(nome, tp.getNome());
        logger.info("Atualizado");
    }
     @Test
    public void atualizarTPMerge() {
        logger.info("Executando atualizarTPMerge()");
        String nome="Minúsculo";
        double comprimento= 1.3;
        Integer id = 1;
       TamanhoProduto tp = em.find(TamanhoProduto.class, id);
        tp.setNome(nome);
        tp.setComprimento(comprimento);
        
        em.clear();
        em.merge(tp);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        tp = em.find(TamanhoProduto.class, id, properties);
        assertEquals(nome, tp.getNome());
        if(tp.getNome().contains(nome)){
            System.out.println("Possui nome!");
        }
    }
    
    @Test
    public void removerTamanhoProduto() {
        logger.info("Executando removerTamanhoProduto()");
        TamanhoProduto tp = em.find(TamanhoProduto.class, 2);
        em.remove(tp);
        TamanhoProduto tp1 = em.find(TamanhoProduto.class, 2);
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