package br.unicamp.bookstore.uc14;

import br.com.correios.ws.CResultado;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.bookstore.dao.DadosDeEntregaDAO;
import br.unicamp.bookstore.dominio.Produto;

import java.math.BigDecimal;

/**
 * Created by enrique1 on 10/4/16.
 */
public class CalculadorPrecoFrete {

	private final DadosDeEntregaDAO dadosDeEntregaDAO;
	private final CalcPrecoPrazoWSSoap servicoCorreios;

    public CalculadorPrecoFrete(DadosDeEntregaDAO dadosDeEntregaDAO, CalcPrecoPrazoWSSoap servicoCorreios) {
		this.dadosDeEntregaDAO = dadosDeEntregaDAO;
		this.servicoCorreios = servicoCorreios;
    }

    public double calcularPreco(Produto produto, String cep) {
    	if (null == produto) {
			throw new RuntimeException("Produto inválido");
		}
		if (produto.temDimensaoNegativa()) {
			throw new RuntimeException("Peso/largura/altura e comprimento nao podem ter valor negativo");
		}
    	if (cep.isEmpty()) {
			throw new RuntimeException("CEP não informado");
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

		validaResultadoCorreio(resultadoConsultaCorreio);

        return Double.valueOf(resultadoConsultaCorreio.getServicos().getCServico().get(0).getValor());
    }

	private void validaResultadoCorreio(CResultado resultadoConsultaCorreio) {
		if (!resultadoConsultaCorreio.getServicos().getCServico().get(0).getErro().isEmpty()) {
			String codigoErro = resultadoConsultaCorreio.getServicos().getCServico().get(0).getErro();
			String mensagemErro = resultadoConsultaCorreio.getServicos().getCServico().get(0).getMsgErro();

			throw new RuntimeException("Erro " + codigoErro + ": " + mensagemErro);
		}
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

	public String salvarDadosFrete(Double valorFrete, Integer diasEntrega) {
		this.dadosDeEntregaDAO.saveDadosDeEntrega(valorFrete, diasEntrega);
		return "Frete gravado com sucesso!";
	}
}
