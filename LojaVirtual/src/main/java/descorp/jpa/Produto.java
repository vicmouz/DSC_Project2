/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.Valid;

/**
 *
 * @author marcosbrasil98
 */
@Entity
@Table(name = "TB_PRODUTO")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Produto.PorNome",
                    query = "SELECT p FROM Produto p WHERE p.nome LIKE :nome ORDER BY p.quantidade"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "InnerJoinProdutoTamanhoCor.PorIdSQL",
                    query = "select TB_PRODUTO.PRODUTO_ID, TB_PRODUTO.PRODUTO_NOME, TB_PRODUTO_COR.CORPRODUTO_ID,TB_TAMANHOPRODUTO.TAMANHOPRODUTO_NOME ,TB_PRODUTO_TAMANHO.TAMANHOPRODUTO_ID\n"
                    + "from ((( TB_PRODUTO\n"
                    + "inner join TB_PRODUTO_COR on TB_PRODUTO.PRODUTO_ID = TB_PRODUTO_COR.PRODUTO_ID)\n"
                    + "inner join TB_PRODUTO_TAMANHO on TB_PRODUTO.PRODUTO_ID = TB_PRODUTO.PRODUTO_ID)\n"
                    + "inner join TB_TAMANHOPRODUTO on TB_PRODUTO_TAMANHO.TAMANHOPRODUTO_ID = TB_TAMANHOPRODUTO.TAMANHOPRODUTO_ID);",
                    resultClass = Produto.class
            )
        }
)
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUTO_ID")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "PRODUTO_NOME")
    private String nome;

    @Lob
    @Size(max = 300)
    @Column(name = "PRODUTO_DESCRICAO")
    private String descricao;

    @NotNull
    @Column(name = "PRODUTO_QUANTIDADE")
    private Integer quantidade;

    @NotNull
    @Column(name = "PRODUTO_PRECO")
    private double preco;

    @Embedded
    private ImagemProduto imgProduto;

    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "PRODUTO_TIPOPRODUTOFK", referencedColumnName = "TIPOPRODUTO_ID", insertable = true, updatable = true)
    private TipoProduto tipoProduto;

    @Valid
    @ManyToMany
    @JoinTable(name = "TB_PRODUTO_TAMANHO", joinColumns
            = {
                @JoinColumn(name = "PRODUTO_ID")}, inverseJoinColumns
            = {
                @JoinColumn(name = "TAMANHOPRODUTO_ID")})
    private List<TamanhoProduto> tamanho;

    @Valid
    @ManyToMany
    @JoinTable(name = "TB_PRODUTO_COR", joinColumns
            = {
                @JoinColumn(name = "PRODUTO_ID")}, inverseJoinColumns
            = {
                @JoinColumn(name = "CORPRODUTO_ID")})
    private List<CorProduto> cor;

    public ImagemProduto getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(ImagemProduto imgProduto) {
        this.imgProduto = imgProduto;
    }

    public boolean possui(String nome) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public List<TamanhoProduto> getTamanho() {
        return tamanho;
    }

    public void setTamanho(List<TamanhoProduto> tamanho) {
        this.tamanho = tamanho;
    }

    public List<CorProduto> getCor() {
        return cor;
    }

    public void setCor(List<CorProduto> cor) {
        this.cor = cor;
    }

}
