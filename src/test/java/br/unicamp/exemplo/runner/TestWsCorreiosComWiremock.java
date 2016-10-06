package br.unicamp.exemplo.runner;

import br.com.correios.ws.CResultado;
import br.com.correios.ws.CalcPrecoPrazoWS;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.util.ServiceLocatorUtil;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by enrique on 10/6/16.
 */
public class TestWsCorreiosComWiremock {


    private final static String FILEPATH = "correios/calculofrete.xml";
    private static final String URL_CORREIOS = "http://localhost:8888/correios";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @Test
    public void testPerformAirShopping() {

	stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/soap+xml").withBodyFile(FILEPATH)));

	final CalcPrecoPrazoWSSoap calcPrecoPrazoWSSoap = new CalcPrecoPrazoWS().getCalcPrecoPrazoWSSoap12();
	ServiceLocatorUtil.configurarUrlEndpoint(calcPrecoPrazoWSSoap, URL_CORREIOS);


	final CResultado cResultado = calcPrecoPrazoWSSoap.calcPrecoPrazo("", "", "", "", "", "", 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
									 BigDecimal.ZERO, "", BigDecimal.ZERO, "");

	Assert.assertNotNull(cResultado);
    }

}
