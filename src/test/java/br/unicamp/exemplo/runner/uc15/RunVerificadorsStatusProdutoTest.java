package br.unicamp.exemplo.runner.uc15;

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
        glue = "br.unicamp.exemplo.steps.uc15",
        features = "classpath:features/uc15/VerificadorStatusProduto.feature"
)
public class RunVerificadorsStatusProdutoTest {

    // Wiremock
    @ClassRule public static WireMockClassRule wireMockRule = new WireMockClassRule(8888);
    /*@Rule public             WireMockClassRule instanceRule = wireMockRule;*/

    @Before
    public void prepare() {
	stubFor(
	        post(urlMatching("/correios"))
                .willReturn(
                        aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/soap+xml")
                            .withBodyFile(CorreiosUtil.FILEPATH_STATUS)
                )
            );
    }
}