package br.unicamp.util;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceLocatorUtil {

    public static void configurarTimeout(Object service, int intervaloEntreLogs) {

	Map<String, Object> requestContext = getBindingProvider(service).getRequestContext();
	requestContext.put("com.sun.xml.internal.ws.request.timeout", intervaloEntreLogs * 1000);
	requestContext.put("com.sun.xml.ws.request.timeout", intervaloEntreLogs * 1000);

    }

    public static void configurarUrlEndpoint(Object service, String url) {

	Map<String, Object> requestContext = getBindingProvider(service).getRequestContext();
	requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

    }

    public static void loggingXmlInConsole() {
	System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
	System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
	System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
	System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }

    private static BindingProvider getBindingProvider(Object service) {
	return (BindingProvider)service;
    }
}
