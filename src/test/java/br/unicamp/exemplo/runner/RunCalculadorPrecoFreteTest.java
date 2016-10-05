package br.unicamp.exemplo.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo.steps.uc14",
        features = "classpath:features/uc14/CalculadorPrecoFrete.feature"
)
public class RunCalculadorPrecoFreteTest {
}

