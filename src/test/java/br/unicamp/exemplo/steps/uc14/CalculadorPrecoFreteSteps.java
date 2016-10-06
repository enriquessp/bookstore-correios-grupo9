package br.unicamp.exemplo.steps.uc14;

import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.dominio.Produto;
import br.unicamp.exemplo.uc14.CalculadorPrecoFrete;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/soap+xml").withBodyFile(CorreiosUtil.FILEPATH)));

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

    @When("^quando o cliente perguntar qual o valor do frete$")
    public void cliente_colicita_preco_frete_do_produto() throws Throwable {
    	 this.precoFrete = calculadorFretePrazo.calcularPreco(produto, cep);
    }

    @Then("^o resultado deve ser (\\d+)$")
    public void the_result_should_be(double precoFreteEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+precoFreteEsperado, precoFreteEsperado, precoFrete, 0);
    }

}