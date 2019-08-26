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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author aluno
 */
@Entity
@Table (name="TB_ADMUSUARIO")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Administrador.PorPermissao",
                    query = "SELECT a FROM Administrador a WHERE a.permissao LIKE :permissao ORDER BY a.id"
            )
        }
)

public class Administrador extends UsuarioGeral implements Serializable {
    

    @NotBlank(message = "Administrador precisa da permissão")
    @Column(name = "ADM_PERMISSAO")
    private String permissao;

    

    
    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }


   


  
}
