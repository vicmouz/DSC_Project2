package exemplo.jpa;

import exemplo.jpa.ClienteUsuario;
import exemplo.jpa.Produto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.5.v20170607-rNA", date="2019-08-21T19:59:29")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, ClienteUsuario> Clienteusuario;
    public static volatile ListAttribute<Pedido, Produto> produto;
    public static volatile SingularAttribute<Pedido, String> log;
    public static volatile SingularAttribute<Pedido, Integer> id;
    public static volatile SingularAttribute<Pedido, Integer> quantidade;

}