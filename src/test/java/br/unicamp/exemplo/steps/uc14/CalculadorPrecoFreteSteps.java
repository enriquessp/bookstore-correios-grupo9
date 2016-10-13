package br.unicamp.exemplo.steps.uc14;

import br.com.correios.ws.CalcPrecoPrazoWSSoap;
import br.unicamp.bookstore.dao.DadosDeEntregaDAO;
import br.unicamp.bookstore.dominio.Produto;
import br.unicamp.bookstore.uc14.CalculadorPrecoFrete;
import br.unicamp.exemplo.util.CorreiosUtil;
import com.github.tomakehurst.wiremock.http.Fault;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.sql.SQLException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CalculadorPrecoFreteSteps {

    // Serviço a ser testado
    private CalculadorPrecoFrete calculadorFretePrazo;

    // Parametros
    protected Produto produto;
    private String cep;
    protected int retornoCorreios;
    private String tipoEntrega;
    private DadosDeEntregaDAO dadosEntregaDaoMock;

    // Possiveis retornos
    private double precoFrete;
    private Throwable throwable;

    @Before
    public void setUp() {
        dadosEntregaDaoMock = Mockito.mock(DadosDeEntregaDAO.class);

        final CalcPrecoPrazoWSSoap servicoCorreiosWS = CorreiosUtil.generateServicoWsCorreio(CorreiosUtil.URL_CORREIOS);
	    calculadorFretePrazo = new CalculadorPrecoFrete(dadosEntregaDaoMock, servicoCorreiosWS);
    	throwable = null;
    }

    @Given("^Dado um produto valido com peso (\\d+) largura (\\d+) altura (\\d+) comprimento (\\d+) e cep:$")
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

    @Given("^Dado um produto inválido$")
    public void produto_invalido(){
        this.produto = null;
    }

    @And("^um cep válido$")
    public void produto_invalido_e_cep_invalido(String cep){
        this.cep = cep;
    }

    @And("^tipo de entrega:$")
    public void cliente_seleciona_tipo_de_entrega(String tipoEntrega){
    	retornoCorreios = calculadorFretePrazo.validaTipoEntrega(this.produto, this.cep, tipoEntrega);
    }

    @And("tipo de entrega selecionado:$")
    public void tipo_de_entrega(String tipoEntrega){
        this.tipoEntrega = tipoEntrega;
    }

    @When("^quando o cliente perguntar qual o valor do frete")
    public void cliente_solicita_preco_frete_do_produto() throws Throwable {
        if (cep.equals("000")) {
            configuraWireMockCorreioCepInvalido();
        } else if(null != this.tipoEntrega && this.tipoEntrega.equals("XPTO")) {
            configuraWireMockCorreioTipoEntregaInvalido();
        } else {
            configuraWireMockCorreioValido();
        }

        invocarServicoCorreios();
    }

    @When("^quando banco de dados fora e o cliente perguntar qual o valor do frete e quiser salvar")
    public void cliente_solicita_preco_frete_do_e_armazenamento_em_banco_fora_do_ar() throws Throwable {
        configuraWireMockCorreioValido();
        configura_mock_DadosEntregaDao_Com_Banco_Fora();

        invocarServicoCorreios();

        try {
            calculadorFretePrazo.salvarDadosFrete(Double.valueOf(this.precoFrete), 10);
        } catch (Throwable exc) {
            this.throwable = exc;
        }
    }

    private void invocarServicoCorreios() {
        try {
            this.precoFrete = calculadorFretePrazo.calcularPreco(produto, cep);
        } catch (Throwable exc) {
            this.throwable = exc;
        }
    }

    @When ("^quando o cliente perguntar qual o tempo do frete")
    public void cliente_solicita_tempo_do_frete() throws Throwable{
        configuraWireMockCorreioValido();

        invocarServicoCorreios();
    }
    
    @Then("^o resultado deve ser (\\d+)")
    public void o_resultado_deveria_ser(double precoFreteEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+precoFreteEsperado, precoFreteEsperado, precoFrete, 0);
    }

    @Then("^o retorno de status dos correios deve ser (\\d)")
    public void o_retorno_deveria_ser(double retornoEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+retornoEsperado, retornoEsperado, retornoCorreios, 0);
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
        stubFor(
            post(urlMatching("/correios"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/soap+xml")
                        .withBodyFile(CorreiosUtil.FILEPATH_CALCULO_VALIDO)
                )
        );
    }

    private void configuraWireMockCorreioCepInvalido() {
        stubFor(
            post(urlMatching("/correios"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/soap+xml")
                        .withBodyFile(CorreiosUtil.FILEPATH_CALCULO_CEP_INVALIDO)
                )
        );
    }

    private void configuraWireMockCorreioTipoEntregaInvalido() {
        stubFor(
            post(urlMatching("/correios"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/soap+xml")
                        .withBodyFile(CorreiosUtil.FILEPATH_CALCULO_TIPO_ENTREGA_INVALIDO)
                )
        );
    }

    private void configuraWireMockCorreioFora() {
        stubFor(
            post(urlMatching("/correios"))
                .willReturn(
                    aResponse()
                        .withFault(Fault.EMPTY_RESPONSE)
                )
        );
    }

    private void configura_mock_DadosEntregaDao_Com_Banco_Fora() {
        Mockito
            .doThrow(new RuntimeException("java.sql.SQLException: Falha ao conectar com o banco de dados"))
            .when(dadosEntregaDaoMock).saveDadosDeEntrega(Mockito.anyDouble(), Mockito.anyInt());
    }
}