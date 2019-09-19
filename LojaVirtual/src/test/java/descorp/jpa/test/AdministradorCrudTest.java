/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Administrador;
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
public class AdministradorCrudTest extends GenericTest {
    
    @Test
    public void persistirAdm(){
       logger.info("Executando persistirADM()");
        Administrador adm = criarADM();
        em.persist(adm);
        em.flush();
        assertNotNull(adm.getId());
        assertNotNull(adm.getNome()); 
    }

    private Administrador criarADM() {
      Administrador adm = new Administrador();
     // adm.setId(3l);
      adm.setNome("Marcos Brasileiro");
      adm.setEmail("m@gmail.com");
      adm.setCpf("595.436.610-14");
      adm.setPermissao("Concedida");
      return adm;
    }
    
    @Test
    public void AtualizarAdm(){
        logger.info("Executando atualizarADM()");
        String novoEmail = "fulano_de_tal@gmail.com";
        String cpf = "807.392.930-95";
        
        Long id = 2l;
        Administrador adm = em.find(Administrador.class, id);
        adm.setEmail(novoEmail);
        adm.setCpf(cpf);
        
        em.flush();       
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        adm = em.find(Administrador.class, id, properties);
        
        assertEquals(novoEmail, adm.getEmail());
        assertEquals(cpf, adm.getCpf());
    }
     @Test
    public void atualizarAdmMerge() {
        logger.info("Executando atualizarAdmMerge()");
        String novoEmail = "cicrano_de_tal2@gmail.com";
        String cpf = "848.345.490-46";
        Long id = 2l;
        Administrador adm = em.find(Administrador.class, id);
        adm.setEmail(novoEmail);
        adm.setCpf(cpf);
        em.clear();
        em.merge(adm);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        adm = em.find(Administrador.class, id, properties);
        assertEquals(novoEmail, adm.getEmail());
         assertEquals(cpf, adm.getCpf());
    }
    
    @Test
    public void removerADM() {
        logger.info("Executando removerADM()");
        Administrador adm = em.find(Administrador.class, 2l);
        em.remove(adm);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Administrador adm1 = em.find(Administrador.class, 2l,map);
        assertNull(adm1);
    }

}
