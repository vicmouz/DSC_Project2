/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author aluno
 */
@Entity
@Table (name="TB_CLIENTEUSUARIO")
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "ClienteUsuario.PorData",
                    query = "SELECT cu FROM ClienteUsuario cu WHERE cu.dataNascimento LIKE :dataNascimento ORDER BY cu.id"
            ),
            @NamedQuery(
                    name = "ClienteUsuario.PorNome",
                    query = "SELECT cu FROM UsuarioGeral cu WHERE cu.nome LIKE :nome ORDER BY cu.id"
            ),
            @NamedQuery(
                    name = "ClienteUsuario.PorCpf",
                    query = "SELECT cu FROM UsuarioGeral cu WHERE cu.cpf LIKE :cpf ORDER BY cu.id"
            ),
            @NamedQuery(
                    name = "ClienteUsuario.PorEmail",
                    query = "SELECT cu FROM UsuarioGeral cu WHERE cu.email LIKE :email ORDER BY cu.id"
            )
        }
)
public class ClienteUsuario extends UsuarioGeral implements Serializable {


@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
@JoinColumn(name = "ID_CARTAO_CREDITO", referencedColumnName = "ID_CARTAO_CREDITO")
private CartaoCredito cartaoCredito;

@Column(name="CLIENTE_DATANASCIMENTO", nullable = false)
@Past
@Temporal(TemporalType.DATE)
private Date dataNascimento;


@NotNull (message = "Celular não pode ser null")
@Size(max = 20)
@Column(name="CLIENTE_CELULAR", length=20)
private String celular;

@NotBlank(message = "Telefone fixo não pode ser null/vazio")
@Column(name="CLIENTE_FIXO",length=20)
private String fixo;

@Embedded
private EnderecoCliente endereco;

@Valid
@OneToMany(mappedBy = "clienteusuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
private List<Pedido> pedidoUsuario;




    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

   
   
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFixo() {
        return fixo;
    }

    public void setFixo(String fixo) {
        this.fixo = fixo;
    }

    public EnderecoCliente getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoCliente endereco) {
        this.endereco = endereco;
    }

   
    public List<Pedido> getPedidoUsuario() {
        return pedidoUsuario;
    }

    public void setPedidoUsuario(List<Pedido> pedidoUsuario) {
        this.pedidoUsuario = pedidoUsuario;
    }
    
    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }
    
    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
        this.cartaoCredito.setUsuario(this);
    }

}
