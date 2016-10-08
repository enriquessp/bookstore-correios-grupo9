package br.unicamp.exemplo.steps.uc15;

import br.unicamp.bookstore.dominio.Produto;
import br.unicamp.bookstore.uc15.VerificadorStatusProduto;
import br.unicamp.exemplo.util.CorreiosUtil;
import com.github.tomakehurst.wiremock.http.Fault;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VerificadorStatusProdutoSteps {

    // Serviço a ser testado
    private VerificadorStatusProduto calculadorFretePrazo;

    // Parametros
    private Produto produto;

    // Possiveis retornos
    private String status;
    private Throwable throwable;

    @Before
    public void setUp() {
	    calculadorFretePrazo = new VerificadorStatusProduto();
    	throwable = null;
    }

    @Given("^Dado um pedido valido com codigo de rastreio:$")
    public void tenho_um_pedido_com_rastreio(String codigoRastreio) throws Throwable {
        produto = new Produto();
        produto.setCodigoRastreio(codigoRastreio);

        assertNotNull(produto.getCodigoRastreio());
    }

    @When("^quando o cliente perguntar qual o status do pedido$")
    public void cliente_colicita_preco_frete_do_produto() throws Throwable {
        configuraWireMockCorreioValido();

        try {
            this.status = calculadorFretePrazo.verificaStatus(produto);
        } catch (Throwable exc) {
            this.throwable = exc;
        }
    }

    @Then("^o resultado deve ser:$")
    public void o_resultado_deveria_ser(String statusCorreios) throws Throwable {
        assertEquals("Status do produto nos correios deveria ser igual a "+statusCorreios, statusCorreios, this.status);
    }

    private void configuraWireMockCorreioValido() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/x-www-form-urlencoded").withBodyFile(CorreiosUtil.FILEPATH_STATUS)));
    }

    private void configuraWireMockCorreioFora() {
        stubFor(post(urlMatching("/correios")).willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));
    }

}