package br.unicamp.exemplo.steps.uc14;

import br.com.correios.ws.CalcPrecoPrazoWS;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.util.ServiceLocatorUtil;

/**
 * Created by enrique on 10/6/16.
 */
public class CorreiosUtil {

    public static final String FILEPATH = "correios/calculofrete.xml";
    public static final String URL_CORREIOS = "http://localhost:8888/correios";

    public static CalcPrecoPrazoWSSoap generateServicoWsCorreio(String URL) {
	final CalcPrecoPrazoWSSoap calcPrecoPrazoWSSoap = new CalcPrecoPrazoWS().getCalcPrecoPrazoWSSoap12();
	ServiceLocatorUtil.configurarUrlEndpoint(calcPrecoPrazoWSSoap, URL);
	return calcPrecoPrazoWSSoap;
    }
}