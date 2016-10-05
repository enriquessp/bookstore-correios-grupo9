package br.unicamp.exemplo.steps.uc14;


import br.unicamp.dominio.Produto;
import br.unicamp.exemplo.uc14.CalculadorPrecoFrete;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.raps.code.generate.ws.CalcPrecoPrazoWS;
import com.raps.code.generate.ws.CalcPrecoPrazoWSSoap;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;


import javax.xml.ws.BindingProvider;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CalculadorPrecoFreteSteps {

    // Serviço a ser testado
    private CalculadorPrecoFrete calculadora;

    // Parametros
    private Produto produto;
    private String cep;

    // Possiveis retornos
    private double precoFrete;
    private Throwable throwable;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Before
    public void setUp() {
        wireMockRule.stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/xml")
                        .withBody("<Servicos>" +
                                "<cServico>" +
                                "<Codigo>40010</Codigo>" +
                                "<Valor>17,20</Valor>" +
                                "<PrazoEntrega>1</PrazoEntrega>" +
                                "<ValorSemAdicionais>17,20</ValorSemAdicionais>" +
                                "<ValorMaoPropria>0,00</ValorMaoPropria>" +
                                "<ValorAvisoRecebimento>0,00</ValorAvisoRecebimento>" +
                                "<ValorValorDeclarado>0,00</ValorValorDeclarado>" +
                                "<EntregaDomiciliar>S</EntregaDomiciliar>" +
                                "<EntregaSabado>S</EntregaSabado>" +
                                "<Erro>0</Erro>" +
                                "<MsgErro/>" +
                                "</cServico>" +
                                "</Servicos>")));

        wireMockRule.start();
        if (wireMockRule.isRunning()) {
            System.out.print("No ar");
        }

        CalcPrecoPrazoWSSoap servicoDosCorreiosWS = new CalcPrecoPrazoWS().getCalcPrecoPrazoWSSoap();
        BindingProvider bp = (BindingProvider)servicoDosCorreiosWS;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/some/thing");

        calculadora = new CalculadorPrecoFrete(servicoDosCorreiosWS);
    	throwable = null;
    }

    @After
    public void close() {
        wireMockRule.shutdown();
    }

    @org.junit.Before
    public void prepare() {
        wireMockRule.stubFor(get(urlEqualTo("/CalcPrecoPrazo"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/xml")
//                        .withHeader("Content-Type", "text/html")
                        .withBody("<Servicos>" +
                                "<cServico>" +
                                "<Codigo>40010</Codigo>" +
                                "<Valor>17,20</Valor>" +
                                "<PrazoEntrega>1</PrazoEntrega>" +
                                "<ValorSemAdicionais>17,20</ValorSemAdicionais>" +
                                "<ValorMaoPropria>0,00</ValorMaoPropria>" +
                                "<ValorAvisoRecebimento>0,00</ValorAvisoRecebimento>" +
                                "<ValorValorDeclarado>0,00</ValorValorDeclarado>" +
                                "<EntregaDomiciliar>S</EntregaDomiciliar>" +
                                "<EntregaSabado>S</EntregaSabado>" +
                                "<Erro>0</Erro>" +
                                "<MsgErro/>" +
                                "</cServico>" +
                                "</Servicos>")));

        wireMockRule.start();

        if (wireMockRule.isRunning()) {
            System.out.print("No ar");
        }

        CalcPrecoPrazoWSSoap servicoDosCorreiosWS = new CalcPrecoPrazoWS().getCalcPrecoPrazoWSSoap();
        BindingProvider bp = (BindingProvider)servicoDosCorreiosWS;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080");

        calculadora = new CalculadorPrecoFrete(servicoDosCorreiosWS);
        throwable = null;
    }

    @Test
    public void exactUrlOnly() {
        calculadora.calcularPreco(new Produto(1, 2, 2, 4), "0120231");
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
    	 this.precoFrete = calculadora.calcularPreco(produto, cep);
    }
    
    @Then("^o resultado deve ser (\\d+)$")
    public void the_result_should_be(double precoFreteEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+precoFreteEsperado, precoFreteEsperado, precoFrete, 0);
    }

}