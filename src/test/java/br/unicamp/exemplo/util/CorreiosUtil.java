package br.unicamp.exemplo.util;

import br.com.correios.ws.CalcPrecoPrazoWS;
import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.util.ServiceLocatorUtil;

/**
 * Created by enrique on 10/6/16.
 */
public class CorreiosUtil {

    public static final String FILEPATH_CALCULO_VALIDO = "correios/calculofrete.xml";
    public static final String URL_CORREIOS = "http://localhost:8888/correios";
    public static final String FILEPATH_STATUS = "correios/statuscorreios.xml";
    public static final String FILEPATH_STATUS_NAO_ENCONTRADO = "correios/statuscorreios_naoencontrado.xml";
    public static final String FILEPATH_CALCULO_CEP_INVALIDO = "correios/calculofrete_erro_cep_invalido.xml";
    public static final String FILEPATH_CALCULO_TIPO_ENTREGA_INVALIDO = "correios/calculofrete_erro_tipo_entrega_invalido.xml";

    public static CalcPrecoPrazoWSSoap generateServicoWsCorreio(String URL) {
        final CalcPrecoPrazoWSSoap calcPrecoPrazoWSSoap = new CalcPrecoPrazoWS().getCalcPrecoPrazoWSSoap12();
        ServiceLocatorUtil.configurarUrlEndpoint(calcPrecoPrazoWSSoap, URL);
        return calcPrecoPrazoWSSoap;
    }
}