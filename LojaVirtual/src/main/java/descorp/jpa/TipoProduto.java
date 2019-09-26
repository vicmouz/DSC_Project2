/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marcosbrasil98
 */
@Entity
@Table(name="TB_TIPOPRODUTO")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "TipoProduto.PorNome",
                    query = "SELECT t FROM TipoProduto t WHERE t.nome LIKE :nome ORDER BY t.id")
           
                        }
)

public class TipoProduto implements Serializable{
  
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "TIPOPRODUTO_ID")
private Long id;

@NotNull
@Column(name = "TIPOPRODUTO_NOME")
private String nome;

public boolean possui(String nome){
       return nome.contains(nome);
   }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
}
