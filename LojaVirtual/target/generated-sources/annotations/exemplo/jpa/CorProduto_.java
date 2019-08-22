package exemplo.jpa;

import exemplo.jpa.ImagemProduto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.5.v20170607-rNA", date="2019-08-21T19:59:29")
@StaticMetamodel(CorProduto.class)
public class CorProduto_ { 

    public static volatile SingularAttribute<CorProduto, String> tipo;
    public static volatile ListAttribute<CorProduto, ImagemProduto> imagem;
    public static volatile SingularAttribute<CorProduto, String> nome;
    public static volatile SingularAttribute<CorProduto, Integer> id;

}