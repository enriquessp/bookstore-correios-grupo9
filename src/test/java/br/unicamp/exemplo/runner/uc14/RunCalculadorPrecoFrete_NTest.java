package br.unicamp.exemplo.runner.uc14;

import br.unicamp.exemplo.util.CorreiosUtil;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo.steps.uc14",
        features = "classpath:features/uc14/CalculadorPrecoFrete_N.feature"
)
public class RunCalculadorPrecoFrete_NTest {

    // Wiremock
    @ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);
    /*@Rule public             WireMockClassRule instanceRule = wireMockRule;*/

    @Before
    public void prepare() {
	stubFor(post(urlMatching("/correios")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/soap+xml").withBodyFile(
			CorreiosUtil.FILEPATH)));
    }
}