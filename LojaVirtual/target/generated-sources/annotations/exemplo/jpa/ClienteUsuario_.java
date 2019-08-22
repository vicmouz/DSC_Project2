package exemplo.jpa;

import exemplo.jpa.EnderecoCliente;
import exemplo.jpa.Pedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.5.v20170607-rNA", date="2019-08-21T19:59:29")
@StaticMetamodel(ClienteUsuario.class)
public class ClienteUsuario_ extends UsuarioGeral_ {

    public static volatile ListAttribute<ClienteUsuario, Pedido> pedidoUsuario;
    public static volatile SingularAttribute<ClienteUsuario, EnderecoCliente> endereco;
    public static volatile SingularAttribute<ClienteUsuario, String> fixo;
    public static volatile SingularAttribute<ClienteUsuario, String> celular;
    public static volatile SingularAttribute<ClienteUsuario, String> dataNascimento;

}