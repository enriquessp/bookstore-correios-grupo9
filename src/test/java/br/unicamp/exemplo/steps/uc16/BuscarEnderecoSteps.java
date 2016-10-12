package br.unicamp.exemplo.steps.uc16;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertNotNull;

import com.github.tomakehurst.wiremock.http.Fault;

import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.bookstore.uc16.BuscarEndereco;
import br.unicamp.exemplo.util.CorreiosUtil;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscarEnderecoSteps {

    // Serviço a ser testado
    private BuscarEndereco buscarEndereco;

	// Parametros
    private String cep;

    // Possiveis retornos
    private String endereco;
    private Throwable throwable;
    
    @Before
    public void setUp() {
    	final CalcPrecoPrazoWSSoap servicoCorreiosWS = CorreiosUtil.generateServicoWsCorreio(CorreiosUtil.URL_CORREIOS);

    	buscarEndereco = new BuscarEndereco();
        	throwable = null;
    }

    @Given("^Dado o seguinte CEP valido (\\d+)$")
    public void tenho_um_cep_valido(String cep) {
        this.cep = cep;
        assertNotNull(this.cep);
    }
    
    @When("^quando o sistema solicitar qual o endereço de entrega$")
    public void sistema_solicita_cep() throws Throwable {
    	configuraWireMockCorreioValido();
    	
    	this.endereco = buscarEndereco.buscarEndereco(cep);
    }
    
    @Then("^o resultado deve ser:$")
    public void exibe_resultado(String message){
    	//assertEquals("Rua nome da rua, N 200 - Bairro, Cidade/ES", endereco);
    }

    private void configuraWireMockCorreioValido() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/soap+xml").withBodyFile(CorreiosUtil.FILEPATH_CALCULO_VALIDO)));
    }

    private void configuraWireMockCorreioFora() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));
    }
}