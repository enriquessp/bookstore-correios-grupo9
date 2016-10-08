package br.unicamp.bookstore.uc15;

import br.unicamp.bookstore.dominio.Produto;
import br.unicamp.util.ClientStatusPedidoCorreios;

/**
 * Created by enrique on 10/8/16.
 */
public class VerificadorStatusProduto {

    private final ClientStatusPedidoCorreios clientStatusPedidoCorreios;

    public VerificadorStatusProduto(ClientStatusPedidoCorreios clientStatusPedidoCorreios) {
        this.clientStatusPedidoCorreios = clientStatusPedidoCorreios;
    }

    public ClientStatusPedidoCorreios.ConsultaStatusResponse verificaStatus(Produto produto) {
        return clientStatusPedidoCorreios.consultarRastreio(produto.getCodigoRastreio());
    }

}