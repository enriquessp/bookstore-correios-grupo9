package br.unicamp.exemplo.runner.uc14;

import br.com.correios.ws.CResultado;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.exemplo.util.CorreiosUtil;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by enrique on 10/6/16.
 */
public class TestWsCorreiosComWiremock {


    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);

/*
    @Rule
    public WireMockClassRule instanceRule = wireMockRule;
*/

    @Test
    public void testServicoCorreios() {

	stubFor(
	        post(urlMatching("/correios"))
            .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/soap+xml")
                            .withBodyFile(CorreiosUtil.FILEPATH_CALCULO_VALIDO)
                        )
            );

	final CalcPrecoPrazoWSSoap calcPrecoPrazoWSSoap = CorreiosUtil.generateServicoWsCorreio(CorreiosUtil.URL_CORREIOS);

	final CResultado cResultado = calcPrecoPrazoWSSoap.calcPrecoPrazo("", "", "", "", "", "", 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
									 BigDecimal.ZERO, "", BigDecimal.ZERO, "");

	Assert.assertNotNull(cResultado);
    Assert.assertEquals(50, Double.valueOf(cResultado.getServicos().getCServico().get(0).getValor()).doubleValue(), 0);
    }

}
