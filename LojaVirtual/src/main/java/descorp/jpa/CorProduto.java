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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author marcosbrasil98
 */
@Entity
@Table(name="TB_CORPRODUTO")
@Access(AccessType.FIELD)
public class CorProduto implements Serializable{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="CORPRODUTO_ID")
private Long id;

@NotBlank
@Column(name = "CORPRODUTO_NOME")
private String nome;
@NotNull(message = "Tipo não pode ser null")
@Column(name = "CORPRODUTO_TIPO")
private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
