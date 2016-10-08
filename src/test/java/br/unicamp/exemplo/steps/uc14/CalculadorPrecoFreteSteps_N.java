package br.unicamp.exemplo.steps.uc14;

import static org.junit.Assert.assertEquals;

import br.unicamp.bookstore.dominio.Produto;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CalculadorPrecoFreteSteps_N 
	extends CalculadorPrecoFreteSteps{
	
	private String tipoEntrega;
	
	
	@Given("Dado um produto inválido")
	public void produto_invalido(){
		this.produto = null;
	}
	
	
    @Then("^o retorno deve ser (\\d+)$")
    public void o_retorno_deveria_ser(double retornoEsperado) throws Throwable {
        assertEquals("Preco deveria ser igual a "+retornoEsperado, retornoEsperado, retornoCorreios, 0);
    }
    
    @And("tipo de entrega selecionado: $")
    public void tipo_de_entrega(String tipoEntrega){
    	this.tipoEntrega = tipoEntrega;
    }
}
