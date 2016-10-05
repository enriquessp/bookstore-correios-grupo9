package br.unicamp.exemplo.uc14;

import br.unicamp.dominio.Produto;
import com.raps.code.generate.ws.CResultado;
import com.raps.code.generate.ws.CalcPrecoPrazoWS;
import com.raps.code.generate.ws.CalcPrecoPrazoWSSoap;

import java.math.BigDecimal;

/**
 * Created by enrique1 on 10/4/16.
 */
public class CalculadorPrecoFrete {

    private final CalcPrecoPrazoWSSoap servicoCorreriosWS;

    public CalculadorPrecoFrete(CalcPrecoPrazoWSSoap servicoCorreriosWS) {
        this.servicoCorreriosWS = servicoCorreriosWS;
    }

    public double calcularPreco(Produto produto, String cep) {
        CResultado resultadoConsultaCorreio = servicoCorreriosWS.calcPrecoPrazo("empresa",
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

}