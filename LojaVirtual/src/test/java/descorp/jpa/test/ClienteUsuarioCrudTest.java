

package descorp.jpa.test;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
import descorp.jpa.UsuarioGeral;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
 

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
        Long id = 1l;
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
            System.out.println("Possui telefone!");*/
        
    }
    
    @Test
    public void atualizarClienteUsuarioMerge() {
        logger.info("Executando atualizarClienteUsuarioMerge()");
        String novoEmail = "cicrano_de_tal2@gmail.com";
        String telefone = "(81) 40028229";
        Long id = 1l;
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
        ClienteUsuario cliente = em.find(ClienteUsuario.class, 1l);
        em.remove(cliente);
        UsuarioGeral usuario = em.find(UsuarioGeral.class, 1l);
        assertNull(usuario);
    }

    private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setId(1l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("100.639.154-13");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        cliente.setDataNascimento("24242424");
        cliente.setEndereco(criarEndereco());
        
        
        return cliente;
    }

    private EnderecoCliente criarEndereco() {
        EnderecoCliente ec = new EnderecoCliente();
         ec.setNome("Avenida Paulista");
        ec.setBairro("São Paulo");
        ec.setCep("424242442");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        ec.setNumero("580");
        return ec;
}
}
