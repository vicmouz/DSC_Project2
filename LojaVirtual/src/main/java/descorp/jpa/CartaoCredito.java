/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marcos
 */
@Entity
@Table(name = "TB_CARTAO_CREDITO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "CartaoCredito.PorBandeira",
                    query = "SELECT c FROM CartaoCredito c WHERE c.bandeira LIKE :nome ORDER BY c.id"),
            @NamedQuery(
                    name = "CartaoCredito.PorNumero",
                    query = "SELECT c FROM CartaoCredito c WHERE c.numero LIKE :numero ORDER BY c.id"
            ),
            @NamedQuery(
                    name = "CartaoCredito.PorData",
                    query = "SELECT c FROM CartaoCredito c WHERE c.dataExpiracao LIKE :data ORDER BY c.id"
            )
           
                        }
)
public class CartaoCredito implements Serializable {

    @Id
    @Column(name = "ID_CARTAO_CREDITO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Valid
    @OneToOne(mappedBy = "cartaoCredito", optional = false, cascade = CascadeType.ALL)
    private ClienteUsuario usuario;
    
    @NotNull (message = "Bandeira não pode ser null")
    @Column(name = "TXT_BANDEIRA")
    private String bandeira;

    @NotNull (message = "Número não pode ser null")
    @Column(name = "TXT_NUMERO")
    private String numero;

    @Temporal(TemporalType.DATE)
    
    @Column(name = "DT_EXPIRACAO", nullable = false)
    private Date dataExpiracao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ClienteUsuario usuario) {
        this.usuario = usuario;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
