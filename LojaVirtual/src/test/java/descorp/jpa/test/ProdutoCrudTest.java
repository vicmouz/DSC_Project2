/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CorProduto;
import descorp.jpa.ImagemProduto;
import descorp.jpa.Produto;
import descorp.jpa.TamanhoProduto;
import descorp.jpa.TipoProduto;
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
       
        Long id = 1l;
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
       
        Long id = 1l;
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
   
    @Test
    public void remover() {
        logger.info("Executando remover()");
         Produto p = em.find(Produto.class, 2l);
        em.remove(p);
        Produto  p1 = em.find(Produto.class, 2l);
        assertNull(p1);
    }

    private Produto criarProduto() {
     Produto p = new Produto();
     p.setNome("Short");
     p.setPreco(12.5);
     p.setDescricao("Ótima qualidade");
     p.setQuantidade(200);
     TipoProduto tp = new TipoProduto();
     tp = criarTP();
     p.setTipoProduto(tp);
     ImagemProduto i = new ImagemProduto();
     i = criarImagem();
     p.setImgProduto(i);
     
   
     return p;
    }
    private TipoProduto criarTP(){
        TipoProduto tp = new TipoProduto();
        tp.setNome("Short");
        return tp;
    }
        private ImagemProduto criarImagem(){
          ImagemProduto img = new ImagemProduto();
          img.setImageProduto("AAAAAAAAAAAAAA");
          return img;
        }
        
    

    
}
