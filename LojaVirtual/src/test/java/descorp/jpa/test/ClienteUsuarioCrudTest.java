package descorp.jpa.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
/* 
package exemplo.jpa.test;

import exemplo.jpa.ClienteUsuario;
import exemplo.jpa.EnderecoCliente;
import exemplo.jpa.UsuarioGeral;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author joaomouzinho
 

public class ClienteUsuarioCrudTest extends GenericTest{
     @Test
  public void persistirClienteUsuario(){
        logger.info("Executando persistirClienteUsuario()");
        ClienteUsuario cliente = criarClienteUsuario();
        em.persist(cliente);
        em.flush();
        assertNotNull(cliente.getId());
    }
    
    @Test
    public void atualizarClienteUsuario() {
      
        logger.info("Executando atualizarClienteUsuario()");
        String novoEmail = "cicrano_de_tal@gmail.com";
        String telefone = "(81) 40028922";
        String fixo ="923423523";
        Integer id = 2;
        ClienteUsuario cliente = em.find(ClienteUsuario.class, id);
        cliente.setEmail(novoEmail);
        cliente.setCelular(telefone);
        cliente.setFixo(fixo);
        em.flush();
        /*String jpql = "SELECT c FROM TB_CLIENTEUSUARIO c WHERE c.id = ?1";
        TypedQuery<ClienteUsuario> query = em.createQuery(jpql, ClienteUsuario.class);
        query.setHint("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        cliente = query.getSingleResult();
        assertEquals(novoEmail, cliente.getEmail());
        if(cliente.getCelular().contains(telefone)){
            System.out.println("Possui telefone!");
        
    }
    
    @Test
    public void atualizarClienteUsuarioMerge() {
        logger.info("Executando atualizarClienteUsuarioMerge()");
        String novoEmail = "cicrano_de_tal2@gmail.com";
        String telefone = "(81) 40028229";
        Integer id = 2;
        ClienteUsuario cliente = em.find(ClienteUsuario.class, id);
        cliente.setEmail(novoEmail);
        cliente.setCelular(telefone);
        em.clear();
        em.merge(cliente);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        cliente = em.find(ClienteUsuario.class, id, properties);
        assertEquals(novoEmail, cliente.getEmail());
        if(cliente.getCelular().contains(telefone)){
            System.out.println("Possui telefone!");
        }
    }
    
    @Test
    public void removerClienteUsuario() {
        logger.info("Executando removerClienteUsuario()");
        ClienteUsuario cliente = em.find(ClienteUsuario.class, 2);
        em.remove(cliente);
        UsuarioGeral usuario = em.find(UsuarioGeral.class, 2);
        assertNull(usuario);
    }

    private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setId(1);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("534.585.765-40");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        cliente.setDataNascimento("24242424");
        cliente.setEndereco(criarEndereco());
        
        
        return cliente;
    }

    private EnderecoCliente criarEndereco() {
        EnderecoCliente endereco = new EnderecoCliente();
        endereco.setNome("Rua VPP");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setCep("50690-220");
        endereco.setNumero("255");
        
        return endereco;
}
}*/
