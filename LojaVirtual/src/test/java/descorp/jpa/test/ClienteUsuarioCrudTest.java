package descorp.jpa.test;

import descorp.jpa.CartaoCredito;
import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
import descorp.jpa.UsuarioGeral;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteUsuarioCrudTest extends GenericTest {

    @Test
    public void persistirClienteUsuario() {
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
        String fixo = "923423523";
        Long id = 1l;
        ClienteUsuario cliente = em.find(ClienteUsuario.class, id); 
        cliente.setEmail(novoEmail);
        cliente.setCelular(telefone);
        cliente.setFixo(fixo);
        em.flush();
        em.clear();
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        cliente = em.find(ClienteUsuario.class, id, properties);        
        assertEquals(novoEmail, cliente.getEmail());
        assertEquals(telefone, cliente.getCelular());
        assertEquals(fixo, cliente.getFixo());

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
        assertEquals(telefone, cliente.getCelular());

    }

    @Test
    public void removerClienteUsuario() {
        logger.info("Executando removerClienteUsuario()");
        ClienteUsuario cliente = em.find(ClienteUsuario.class, 1l);
        em.remove(cliente);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        UsuarioGeral usuario = em.find(UsuarioGeral.class, 1l, map);
        assertNull(usuario);
    }

    private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        //cliente.setId(1l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("100.639.154-13");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1990);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cliente.setDataNascimento(c.getTime());
        cliente.setEndereco(criarEndereco());
        CartaoCredito cartaoCredito = criarCartaoCredito();
        cliente.setCartaoCredito(cartaoCredito);

        return cliente;
    }

    private EnderecoCliente criarEndereco() {
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setBairro("São Paulo");
        ec.setCep("50.144");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("BR");
        ec.setNumero("580");
        return ec;
    }

    public CartaoCredito criarCartaoCredito() {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2021);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("4073000-100");
        return cartaoCredito;
    }
}
