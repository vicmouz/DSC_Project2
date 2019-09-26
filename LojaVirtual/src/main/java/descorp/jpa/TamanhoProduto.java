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
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author marcosbrasil98
 */
@Entity
@Table(name="TB_TAMANHOPRODUTO")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "TamanhoProduto.PorNome",
                    query = "SELECT t FROM  TamanhoProduto t WHERE t.nome LIKE :nome ORDER BY t.id"),
            @NamedQuery(
                    name = "TamanhoProduto.PorTipo",
                    query = "SELECT t FROM TamanhoProduto t WHERE t.tipo LIKE :tipo ORDER BY t.id")
           
                        }
)
public class TamanhoProduto implements Serializable{
 
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name="TAMANHOPRODUTO_ID")
 private Long id;
 
 @NotEmpty
 @Column(name="TAMANHOPRODUTO_NOME")
 private String nome;
 
 @NotNull
 @Column(name="TAMANHOPRODUTO_ALTURA")
 private double altura;
 @NotNull
 @Column(name="TAMANHOPRODUTO_COMPRIMENTO")
 private double comprimento;
 @NotNull
 @Column(name="TAMANHOPRODUTO_LARGURA")
 private double largura;
 @NotEmpty
 @Size(max = 50)
 @Column(name="TAMANHOPRODUTO_TIPO")
 private String tipo;
 


   
 
 
 

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

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}