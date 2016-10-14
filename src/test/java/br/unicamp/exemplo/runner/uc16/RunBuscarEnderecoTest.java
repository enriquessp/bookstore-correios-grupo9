package br.unicamp.exemplo.runner.uc16;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo.steps.uc16",
        features = "classpath:features/uc16/BuscarEndereco.feature"
)
public class RunBuscarEnderecoTest {}