/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Administrador;
import descorp.jpa.CartaoCredito;
import descorp.jpa.ClienteUsuario;
import descorp.jpa.CorProduto;
import descorp.jpa.Pedido;
import descorp.jpa.Produto;
import descorp.jpa.TamanhoProduto;
import descorp.jpa.TipoProduto;
import java.util.Calendar;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author marcos
 */
public class JpqlTest extends GenericTest{
    
    
    //Testes JPQL Administrador
    
    @Test
    public void AdministradorPorNome(){
        logger.info("Executando Administrador por nome");
        TypedQuery<Administrador> query = em.createQuery("SELECT a FROM UsuarioGeral a WHERE a.nome LIKE :nome",Administrador.class);
        query.setParameter("nome","Toboco");
        List<Administrador> administradores = query.getResultList();

        for (Administrador adm : administradores) {
            assertTrue(adm.getNome().startsWith("Toboco"));
        }

        assertEquals(1, administradores.size());
    }
    @Test
    public void AdministradorPorNomeQuery(){
        logger.info("Executando Administrador por nome query");
        TypedQuery<Administrador> query = em.createNamedQuery("Administrador.PorNome",Administrador.class);
        query.setParameter("nome","Toboco");
        List<Administrador> administradores = query.getResultList();

        for (Administrador adm : administradores) {
            assertTrue(adm.getNome().startsWith("Toboco"));
        }

        assertEquals(1, administradores.size());  
    }
    
   @Test
   public void QuantidadePermissoesAdm(){
      logger.info("Executando QuantidadePermissoesAdm");
       TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Administrador a WHERE a.permissao IS NOT NULL", Long.class);
        Long resultado = query.getSingleResult();
        assertEquals(new Long(3), resultado);
   }
   
   
   //Testes JPQL Cliente
   
   @Test
   public void ClientePorNome(){
       logger.info("Executando Cliente por nome");
        TypedQuery<ClienteUsuario> query = em.createQuery("SELECT c FROM UsuarioGeral c WHERE c.nome LIKE :nome",ClienteUsuario.class);
        query.setParameter("nome","Flavio Antonio");
        List<ClienteUsuario> clientes = query.getResultList();

        for (ClienteUsuario cliente : clientes) {
            assertTrue(cliente.getNome().startsWith("Flavio Antonio"));
        }

        assertEquals(1, clientes.size());
   }
   
   @Test
    public void ClientePorNomeQuery(){
        logger.info("Executando cliente por nome query");
        TypedQuery<ClienteUsuario> query = em.createNamedQuery("ClienteUsuario.PorNome",ClienteUsuario.class);
        query.setParameter("nome","Flavio Antonio");
        List<ClienteUsuario> clientes = query.getResultList();

        for (ClienteUsuario cliente : clientes) {
            assertTrue(cliente.getNome().startsWith("Flavio Antonio"));
        }

        assertEquals(1, clientes.size());  
    }
    @Test
    public void QuantidadeClientePorCEP(){
        logger.info("Executando QuantidadeClientePorCep()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM ClienteUsuario a WHERE a.endereco.cep IS NOT NULL", Long.class);
        
        Long resultado = query.getSingleResult();
        assertEquals(new Long(5), resultado);  
    }
    
    @Test
    public void QuantidadeClientePorEstado(){
        logger.info("Executando QuantidadeClientePorEstado()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM ClienteUsuario a WHERE a.endereco.estado LIKE :estado", Long.class);
        query.setParameter("estado", "PE");
        
        Long resultado = query.getSingleResult();
        assertEquals(new Long(5), resultado);  
    }
    
   @Test
    public void clienteVisa() {
        logger.info("Executando clienteVisa()");
        TypedQuery<ClienteUsuario> query;
        query = em.createQuery(
                "SELECT c FROM ClienteUsuario c WHERE c.cartaoCredito.bandeira like ?1",
                ClienteUsuario.class);
        query.setParameter(1, "VISA"); 
        query.setMaxResults(20); 
        List<ClienteUsuario> clientes = query.getResultList();

        for (ClienteUsuario cliente : clientes) {
            assertEquals("VISA", cliente.getCartaoCredito().getBandeira());
        }

        assertEquals(2, clientes.size());
    }
    
    @Test
    public void clienteVisaMastercard() {
        logger.info("Executando clienteVisaMastercard()");
        TypedQuery<ClienteUsuario> query;
        query = em.createQuery(
                "SELECT c FROM ClienteUsuario c "
                + "WHERE c.cartaoCredito.bandeira LIKE ?1 "
                + "OR c.cartaoCredito.bandeira LIKE ?2",
                ClienteUsuario.class);
        query.setParameter(1, "VISA"); 
        query.setParameter(2, "MASTERCARD");        
        List<ClienteUsuario> clientes = query.getResultList();

        for (ClienteUsuario cliente : clientes) {
            assertThat(cliente.getCartaoCredito().getBandeira(),
                    CoreMatchers.anyOf(
                            startsWith("VISA"),
                            startsWith("MASTERCARD")));
        }

        assertEquals(3, clientes.size());
    }
    
    @Test
    public void clienteHipercardMaestro() {
        logger.info("Executando clienteHipercardMaestro()");
        TypedQuery<ClienteUsuario> query;
        query = em.createQuery(
                "SELECT c FROM ClienteUsuario c "
                + "WHERE c.cartaoCredito.bandeira IN ('MAESTRO', 'HIPERCARD')",
                ClienteUsuario.class);
        List<ClienteUsuario> clientes = query.getResultList();

        for (ClienteUsuario cliente : clientes) {
            assertThat(cliente.getCartaoCredito().getBandeira(),
                    CoreMatchers.anyOf(
                            startsWith("MAESTRO"),
                            startsWith("HIPERCARD")));
        }

        assertEquals(2, clientes.size());
    }
    
    @Test
    public void ClientePorDataNascimento() {
        logger.info("Executando ClientePorDataNascimento()");
        TypedQuery<ClienteUsuario> query;
        query = em.createQuery(
                "SELECT c FROM ClienteUsuario c WHERE c.dataNascimento BETWEEN ?1 AND ?2",
                ClienteUsuario.class);
        query.setParameter(1, getData(21, Calendar.FEBRUARY, 1990));
        query.setParameter(2, getData(1, Calendar.DECEMBER, 2000));
        List<ClienteUsuario> clientes = query.getResultList();
        assertEquals(5, clientes.size());
    }
    
    // Testes JPQL TipoProduto
    
    @Test
    public void TipoprodutoPorNome(){
        logger.info("Executando TipoProduto por nome");
        TypedQuery<TipoProduto> query = em.createQuery("SELECT t FROM TipoProduto t WHERE t.nome LIKE :nome ",TipoProduto.class);
        query.setParameter("nome","Camisa");
        List<TipoProduto> tipos = query.getResultList();

        for (TipoProduto tipo : tipos) {
            assertTrue(tipo.getNome().startsWith("Camisa"));
        }

        assertEquals(1, tipos.size());
    }
    
    @Test
    public void TipoprodutoPorNomeQuery(){
        logger.info("Executando TipoProduto por nome query");
        TypedQuery<TipoProduto> query = em.createNamedQuery("TipoProduto.PorNome",TipoProduto.class);
        query.setParameter("nome","Camisa");
        List<TipoProduto> tipos = query.getResultList();

        for (TipoProduto tipo : tipos) {
            assertTrue(tipo.getNome().startsWith("Camisa"));
        }

        assertEquals(1, tipos.size());
    }
    
    @Test
    public void TiposDistintos() {
        logger.info("Executando TiposDistintos()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(t.nome) FROM TipoProduto t ORDER BY t.nome", String.class);
        List<String> tipos = query.getResultList();
        assertEquals(7, tipos.size());
    }



// Testes JPQL CartaoCredito

@Test
  public void CartaoCreditoPorNumero(){
        logger.info("Executando CartaoCreditoPorNumero");
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT c FROM CartaoCredito c WHERE c.numero LIKE :numero ",CartaoCredito.class);
        query.setParameter("numero","5467428134749728");
        List<CartaoCredito> cartoes = query.getResultList();

        for (CartaoCredito cartao : cartoes) {
            assertTrue(cartao.getNumero().startsWith("5467428134749728"));
        }

        assertEquals(1, cartoes.size());
    }
    
    @Test
    public void CartaoCreditoPorNumeroQuery(){
        logger.info("Executando CartaoCredito por numero query");
        TypedQuery<CartaoCredito> query = em.createNamedQuery("CartaoCredito.PorNumero",CartaoCredito.class);
        query.setParameter("numero","6759744303415452");
        List<CartaoCredito> cartoes = query.getResultList();

        for (CartaoCredito cartao : cartoes) {
            assertTrue(cartao.getNumero().startsWith("6759744303415452"));
        }

        assertEquals(1, cartoes.size());
    }

    @Test
    public void bandeirasDistintas() {
        logger.info("Executando bandeirasDistintas()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.bandeira) FROM CartaoCredito c ORDER BY c.bandeira", String.class);
        List<String> bandeiras = query.getResultList();
        assertEquals(4, bandeiras.size());
    }
    
    @Test
    public void ordenacaoCartao() {
        logger.info("Executando ordenacaoCartao()");
        TypedQuery<CartaoCredito> query;
        query = em.createQuery(
                "SELECT c FROM CartaoCredito c ORDER BY c.bandeira DESC",
                CartaoCredito.class);
        List<CartaoCredito> cartoes = query.getResultList();
        assertEquals(5, cartoes.size());
        assertEquals("VISA", cartoes.get(0).getBandeira());
        assertEquals("VISA", cartoes.get(1).getBandeira());
        assertEquals("MASTERCARD", cartoes.get(2).getBandeira());
        assertEquals("MAESTRO", cartoes.get(3).getBandeira());
        assertEquals("HIPERCARD", cartoes.get(4).getBandeira());
    }
    
     @Test
    public void cartoesExpirados() {
        logger.info("Executando cartoesExpirados()");
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT c FROM CartaoCredito c WHERE c.dataExpiracao < CURRENT_TIMESTAMP", CartaoCredito.class);
        List<CartaoCredito> cartoesExpirados = query.getResultList();
        assertEquals(1, cartoesExpirados.size());
    }
    
  // Testes JPQL CorProduto
    
    @Test
    public void CorProdutoPorNome(){
        logger.info("Executando CorProduto por nome");
        TypedQuery<CorProduto> query = em.createQuery("SELECT c FROM CorProduto c WHERE c.nome LIKE :nome ",CorProduto.class);
        query.setParameter("nome","Azul");
        List<CorProduto> cores = query.getResultList();

        for (CorProduto cor : cores) {
            assertTrue(cor.getNome().startsWith("Azul"));
        }

        assertEquals(1, cores.size());
    }
    
    @Test
    public void CorProdutoPorNomeQuery(){
        logger.info("Executando CorProdutoPorNomeQuery");
        TypedQuery<CorProduto> query = em.createNamedQuery("CorProduto.PorNome",CorProduto.class);
        query.setParameter("nome","Azul");
        List<CorProduto> cores = query.getResultList();

        for (CorProduto cor : cores) {
            assertTrue(cor.getNome().startsWith("Azul"));
        }

        assertEquals(1, cores.size());
    }
    
    @Test
    public void CoresDistintos() {
        logger.info("Executando CoresDistintos()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.nome) FROM CorProduto c ORDER BY c.nome", String.class);
        List<String> cores = query.getResultList();
        assertEquals(5, cores.size());
    }

    
    // Testes JPQL Pedido
    
    @Test
    public void PedidoPorLog(){
        logger.info("Executando PedidoPorLog");
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p WHERE p.log LIKE :log ",Pedido.class);
        query.setParameter("log","rqqw4qw42");
        List<Pedido> pedidos = query.getResultList();

        for (Pedido pedido : pedidos) {
            assertTrue(pedido.getLog().startsWith("rqqw4qw42"));
        }

        assertEquals(1, pedidos.size());
    }
    
  @Test
    public void PedidoPorNomeQuery(){
        logger.info("Executando PedidoPorNomeQuery");
        TypedQuery<Pedido> query = em.createNamedQuery("Pedido.PorLog",Pedido.class);
        query.setParameter("log","rqqw4qw42");
        List<Pedido> pedidos = query.getResultList();

        for (Pedido pedido : pedidos) {
            assertTrue(pedido.getLog().startsWith("rqqw4qw42"));
        }

        assertEquals(1, pedidos.size());
    }  
    
   
    @Test
    public void pedidoStatusDistintos() {
        logger.info("Executando pedidoStatusDistintos()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.status) FROM Pedido c ORDER BY c.status", String.class);
        List<String> pedidos = query.getResultList();
        assertEquals(3, pedidos.size());
    }
   
  // Testes JPQL Produto
    
    @Test
    public void ProdutoPorNome() {
        logger.info("Executando ProdutoPorNome()");
        TypedQuery<Produto> query = em.createQuery(
                "SELECT p FROM Produto p WHERE p.nome LIKE :nome",
                Produto.class);
        query.setParameter("nome", "Camisa");
        List<Produto> produtos = query.getResultList();

        for (Produto produto : produtos) {
            assertTrue(produto.getNome().startsWith("Camisa"));
        }

        assertEquals(1, produtos.size());
    }

    @Test
    public void ProdutoPorNomeQuery() {
        logger.info("Executando ProdutoPorNomeQuery()");
        TypedQuery<Produto> query = em.createNamedQuery("Produto.PorNome", Produto.class);
        query.setParameter("nome", "Camisa");
        List<Produto> produtos = query.getResultList();

        for (Produto produto : produtos) {
            assertTrue(produto.getNome().startsWith("Camisa"));
        }

        assertEquals(1, produtos.size());
    }

    @Test
    public void QuantidadeProdutoTipo() {
        logger.info("Executando QuantidadeProdutoTipo()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Produto c WHERE c.tipoProduto IS NOT NULL", Long.class);
        Long resultado = query.getSingleResult();
        assertEquals(new Long(5), resultado);
    }
    
    @Test 
    public void produtosDistintos() {
        logger.info("Executando produtosDistintos()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.nome) FROM Produto c ORDER BY c.nome", String.class);
        List<String> produtos = query.getResultList();
        assertEquals(5, produtos.size());
    }
    
    //Testes JPQL TamanhoProduuto
    
    
     @Test
    public void TamanhoPorNome() {
        logger.info("Executando TamanhoPorNome()");
        TypedQuery<TamanhoProduto> query = em.createQuery(
                "SELECT p FROM TamanhoProduto p WHERE p.nome LIKE :nome",
                TamanhoProduto.class);
        query.setParameter("nome", "Pequeno");
        List<TamanhoProduto> tamanhos = query.getResultList();

        for (TamanhoProduto tamanho : tamanhos) {
            assertTrue(tamanho.getNome().startsWith("Pequeno"));
        }

        assertEquals(1, tamanhos.size());
    }

    @Test
    public void TamanhoProdutoPorNomeQuery() {
        logger.info("Executando TamanhoProdutoPorNomeQuery()");
        TypedQuery<TamanhoProduto> query = em.createNamedQuery("TamanhoProduto.PorNome", TamanhoProduto.class);
        query.setParameter("nome", "Pequeno");
        List<TamanhoProduto> tamanhos = query.getResultList();

        for (TamanhoProduto tamanho : tamanhos) {
            assertTrue(tamanho.getNome().startsWith("Pequeno"));
        }

        assertEquals(1, tamanhos.size());
    }

  
    @Test 
    public void tamanhoDistintos() {
        logger.info("Executando tamanhoDistintos()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.nome) FROM TamanhoProduto c ORDER BY c.nome", String.class);
        List<String> produtos = query.getResultList();
        assertEquals(5, produtos.size());
    }
    
}