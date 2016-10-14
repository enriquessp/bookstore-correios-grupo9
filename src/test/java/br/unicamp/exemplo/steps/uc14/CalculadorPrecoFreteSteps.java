package br.unicamp.exemplo.steps.uc14;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.assertj.core.api.Assertions;

import com.github.tomakehurst.wiremock.http.Fault;

import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.bookstore.dominio.Produto;
import br.unicamp.bookstore.uc14.CalculadorPrecoFrete;
import br.unicamp.exemplo.util.CorreiosUtil;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculadorPrecoFreteSteps {

    // Serviço a ser testado
    private CalculadorPrecoFrete calculadorFretePrazo;

    // Parametros
    private Produto produto;
    private String cep;

    // Possiveis retornos
    private double precoFrete;
    private Throwable throwable;

    @Before
    public void setUp() {

	final CalcPrecoPrazoWSSoap servicoCorreiosWS = CorreiosUtil.generateServicoWsCorreio(CorreiosUtil.URL_CORREIOS);

	calculadorFretePrazo = new CalculadorPrecoFrete(servicoCorreiosWS);
    	throwable = null;
    }

    @Given("^Dado um produto valido com peso (\\d+) largura (\\d+) altura (\\d+) comprimento (\\d+) e cep (\\d+)$")
    public void tenho_um_produto_e_cep(int peso, int largura, int altura, int comprimento, String cep) throws Throwable {
        this.cep = cep;
        produto = new Produto(peso, largura, altura, comprimento);

        assertNotNull(produto.getAltura());
        assertNotNull(produto.getComprimento());
        assertNotNull(produto.getLargura());
        assertNotNull(produto.getPeso());
        assertNotNull(this.cep);
    }

    @Given("^Dado um produto peso negativo$")
    public void tenho_um_produto_e_cep() {
        cep = "5334533";
        produto = new Produto(-5, 10, 10, 10);
    }

    @When("^quando o cliente perguntar qual o valor do frete$")
    public void cliente_colicita_preco_frete_do_produto() throws Throwable {
        configuraWireMockCorreioValido();

        try {
            this.precoFrete = calculadorFretePrazo.calcularPreco(produto, cep);
        } catch (Throwable exc) {
            this.throwable = exc;
        }
    }

    @Then("^o resultado deve ser (\\d+)$")
    public void o_resultado_deveria_ser(double precoFreteEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+precoFreteEsperado, precoFreteEsperado, precoFrete, 0);
    }

    @When("^quando o servico dos Correios estiver fora e o cliente perguntar qual o valor$")
    public void quando_servico_correios_fora() {
        configuraWireMockCorreioFora();
        try {
            calculadorFretePrazo.calcularPreco(produto, cep);
        } catch (Throwable exc) {
            throwable = exc;
        }
    }

    @Then("^deveria apresentar um erro com a mensagem:$")
    public void should_show_an_error(String message){
        Assertions.assertThat(throwable).isNotNull().hasMessage(message);
    }

    private void configuraWireMockCorreioValido() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/soap+xml").withBodyFile(CorreiosUtil.FILEPATH)));
    }

    private void configuraWireMockCorreioFora() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));
    }

}