package br.unicamp.bookstore.uc14;

import br.com.correios.ws.CResultado;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.bookstore.dominio.Produto;

import java.math.BigDecimal;

/**
 * Created by enrique1 on 10/4/16.
 */
public class CalculadorPrecoFrete {

    private final CalcPrecoPrazoWSSoap servicoCorreios;

    public CalculadorPrecoFrete(CalcPrecoPrazoWSSoap servicoCorreios) {
	this.servicoCorreios = servicoCorreios;
    }

    public double calcularPreco(Produto produto, String cep) {
    	if (produto.temDimensaoNegativa()) {
			throw new RuntimeException("Peso/largura/altura e comprimento nao podem ter valor negativo");
		}
        CResultado resultadoConsultaCorreio = servicoCorreios.calcPrecoPrazo("empresa",
									     "", //senha
									     "", //servico
									     cep,
									     cep,
									     "" + produto.getPeso(),
									     0,//formato
									     BigDecimal.valueOf(produto.getComprimento()),
									     BigDecimal.valueOf(produto.getAltura()),
									     BigDecimal.valueOf(produto.getLargura()),
									     BigDecimal.ZERO, //diametro
									     "", //maoPropria
									     BigDecimal.ZERO, //valorDeclarado
									     "" //avisoRecebimento
        		);

        return Double.valueOf(resultadoConsultaCorreio.getServicos().getCServico().get(0).getValor());
    }

	public int validaTipoEntrega(Produto produto, String cep, String tipoEntrega) {
        CResultado resultadoConsultaCorreio = servicoCorreios.calcPrecoPrazo("empresa",
				     "", //senha
				     "", //servico
				     cep,
				     cep,
				     "" + produto.getPeso(),
				     0,//formato
				     BigDecimal.valueOf(produto.getComprimento()),
				     BigDecimal.valueOf(produto.getAltura()),
				     BigDecimal.valueOf(produto.getLargura()),
				     BigDecimal.ZERO, //diametro
				     "", //maoPropria
				     BigDecimal.ZERO, //valorDeclarado
				     "" //avisoRecebimento
        		);

        return Integer.parseInt(resultadoConsultaCorreio.getServicos().getCServico().get(0).getErro());
	}

}
