package br.unicamp.exemplo.runner.uc14;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo.steps.uc14",
        features = "classpath:features/uc14/CalculadorPrecoFrete.feature"
)
public class RunCalculadorPrecoFreteTest {

    // Wiremock: inicializacao
    @ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);
}