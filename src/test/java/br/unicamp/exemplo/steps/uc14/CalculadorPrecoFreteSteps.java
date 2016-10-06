package br.unicamp.exemplo.steps.uc14;

import br.unicamp.dominio.Produto;
import br.unicamp.exemplo.uc14.CalculadorPrecoFrete;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CalculadorPrecoFreteSteps {

    // Serviço a ser testado
    private CalculadorPrecoFrete calculadora;

    // Parametros
    private Produto produto;
    private String cep;

    // Possiveis retornos
    private double precoFrete;
    private Throwable throwable;

    @Before
    public void setUp() {
        calculadora = new CalculadorPrecoFrete();
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
