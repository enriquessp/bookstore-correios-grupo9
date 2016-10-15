package br.unicamp.exemplo.steps.uc16;

import static org.junit.Assert.*;

import br.unicamp.bookstore.dao.EnderecosDosCorreiosDao;

import br.unicamp.bookstore.uc16.BuscarEndereco;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

public class BuscarEnderecoSteps {

    // Serviço a ser testado
    private BuscarEndereco buscarEndereco;

	// Parametros
    private String cep;

    // Possiveis retornos
    private String endereco;
    private Throwable throwable;

    @Mock
    private EnderecosDosCorreiosDao enderecosDosCorreiosDao;

    @Before
    public void setUp() {
        // Inicializa os mocks sobre as classes annotadas com @Mock
	MockitoAnnotations.initMocks(this);

    	buscarEndereco = new BuscarEndereco(enderecosDosCorreiosDao);
	throwable = null;
    }

    @Given("^Dado o seguinte CEP valido (\\d+)$")
    public void tenho_um_cep_valido(String cep) {
        this.cep = cep;
        assertNotNull(this.cep);
    }
    
    @Given("^Dado o seguinte CEP inválido (\\d+)$")
    public void tenho_um_cep_invalido(String cep) {
        this.cep = cep;
        assertNotNull(this.cep);
    }
    
    @And("^e o sistema solicitar qual o endereço de entrega$")
    public void sistema_solicita_cep() {
	try {
	    this.endereco = buscarEndereco.buscarEndereco(cep);
	} catch (Throwable exc) {
	    throwable = exc;
	}
    }

    @When("^Quando o banco de dados dos correios estiver no ar$")
    public void banco_dos_correios_no_ar() {
	configuraMockCorreioEnderecoValido(cep);
    }

    @When("^Quando o banco de dados dos correios estiver fora do ar$")
    public void banco_dos_correios_fora_do_ar() {
	configuraMockBancoDeDadosCorreioFora();
    }

    @Then("^o resultado deve ser:$")
    public void exibe_resultado(String message) {
	assertEquals("Deveria ter endereco igual", message, endereco);
    }

    @Then("^deveria apresentar mensage de erro ser:$")
    public void exibir_mensagem_erro(String message) {
	Assertions.assertThat(throwable).isNotNull().hasMessage(message);
    }

    private void configuraMockCorreioEnderecoValido(String cep) {
    	if (!cep.equals("10101010")) {
    		Mockito.when(enderecosDosCorreiosDao.recuperaEndereco(cep)).thenReturn("Rua nome da rua, N 200 - Bairro, Cidade/ES");
    	} else {
    		Mockito.when(enderecosDosCorreiosDao.recuperaEndereco(cep)).thenReturn("Endereço não encontrado.");    		
    	}
    }

    private void configuraMockBancoDeDadosCorreioFora() {
	Mockito.when(enderecosDosCorreiosDao.recuperaEndereco(cep)).thenThrow(new RuntimeException("Connection timeout"));
    }
}