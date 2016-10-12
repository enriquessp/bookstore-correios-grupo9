package br.unicamp.exemplo.runner.uc15;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo.steps.uc15",
        features = "classpath:features/uc15/VerificadorStatusProduto.feature"
)
public class RunVerificadorsStatusProdutoTest {

    // Wiremock: inicializacao
    @ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);

}