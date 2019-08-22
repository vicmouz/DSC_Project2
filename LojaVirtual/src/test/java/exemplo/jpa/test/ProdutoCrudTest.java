/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.test;

import exemplo.jpa.CorProduto;
import exemplo.jpa.Produto;
import exemplo.jpa.TamanhoProduto;
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
public class ProdutoCrudTest extends GenericTest {
    
     @Test
    public void persistir(){
       logger.info("Executando persistir()");
        Produto p = criarProduto();
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
        assertNotNull(p.getNome()); 
    }
    
    @Test
    public void Atualizar(){
        logger.info("Executando atualizar()");
        String nome = "Camisa de Formatura";
        int quantidade = 1000;
       
        Integer id = 1;
         Produto p =em.find(Produto.class, id);
        p.setNome(nome);
        p.setQuantidade(quantidade);
        
        em.flush();   
        assertEquals(nome, p.getNome());
        
        logger.info("Atualizado");
    }
     @Test
    public void atualizarMerge() {
        logger.info("Executando atualizarMerge()");
        String nome = "Camisa de ABC";
        int quantidade = 500;
       
        Integer id = 1;
         Produto p =em.find(Produto.class, id);
        p.setNome(nome);
        p.setQuantidade(quantidade);
        
        
        em.clear();
        em.merge(p);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        p = em.find(Produto.class, id, properties);
        assertEquals(nome, p.getNome());
        if(p.getNome().contains(nome)){
            System.out.println("Possui nome!");
        }
    }
   /*
    @Test
    public void remover() {
        logger.info("Executando remover()");
         Produto p = em.find(Produto.class, 2);
        em.remove(p);
        Produto  p1 = em.find(Produto.class, 2);
        assertNull(p1);
    }
*/
    private Produto criarProduto() {
     Produto p = new Produto();
     p.setNome("Short");
     p.setPreco(12.5);
     p.setDescricao("Ótima qualidade");
     TipoProduto tp = new TipoProduto();
     tp = criarTP();
     p.setTipoProduto(tp);
     
   
     return p;
    }
    private TipoProduto criarTP(){
        TipoProduto tp = new TipoProduto();
        tp.setNome("Short");
        return tp;
    }
    

    
}
