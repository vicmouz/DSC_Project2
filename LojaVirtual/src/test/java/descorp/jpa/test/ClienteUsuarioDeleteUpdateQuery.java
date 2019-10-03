/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author root
 */
public class ClienteUsuarioDeleteUpdateQuery extends GenericTest {

    @Test
    public void queryUpdate() {
        Long id = 1L;
        String novoNome = "Update test";
        Query update = em.createQuery("UPDATE ClienteUsuario AS c SET c.nome = ?1 WHERE c.id = ?2");
        update.setParameter(1, novoNome);
        update.setParameter(2, id);
        update.executeUpdate();
        String jpql = "SELECT c FROM ClienteUsuario c WHERE c.id = :id";
        TypedQuery<ClienteUsuario> query = em.createQuery(jpql, ClienteUsuario.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        ClienteUsuario cliente = query.getSingleResult();
        assertEquals(novoNome, cliente.getNome());       
    }

    @Test(expected = NoResultException.class)
    public void queryDelete() {
        Long id = 3L;
        Query delete = em.createQuery("DELETE FROM ClienteUsuario AS c WHERE c.id = ?1");
        delete.setParameter(1, id);
        delete.executeUpdate();
        String jpql = "SELECT c FROM ClienteUsuario c WHERE c.id =?1";
        TypedQuery<ClienteUsuario> query = em.createQuery(jpql, ClienteUsuario.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.getSingleResult();
    }
    
     private EnderecoCliente criarEndereco() {
        EnderecoCliente ec = new EnderecoCliente();
        ec.setNome("Avenida Paulista");
        ec.setBairro("São Paulo");
        ec.setCep("50.144");
        ec.setCidade("São Paulo");
        ec.setComplemento("Casa");
        ec.setEstado("São Paulo");
        ec.setPais("XX");
        ec.setNumero("580");
        return ec;
    }

}
