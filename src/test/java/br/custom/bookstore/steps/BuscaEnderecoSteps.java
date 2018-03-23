package br.custom.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.mockito.MockitoAnnotations;

import br.custom.bookstore.Correio;
import br.custom.bookstore.DadosCalculaPrecoPrazo;
import br.custom.bookstore.DadosDeEntrega;
import br.custom.bookstore.helper.RequestCall;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscaEnderecoSteps {

	private Exception e;
	private WireMockServer wireMockServer;
	
	private String result;
	private Long currentTime;
	
	private String cep;
	private Integer returnCode;
	private String contentType;
	private Correio correio;

	@Before
	public void setUp() {
		e = null;
		wireMockServer = new WireMockServer(wireMockConfig().port(8089));
		wireMockServer.start();
		configureFor("localhost", 8089);
	}
	
	@Given("^Json data initialized (.+)$")
	public void data_initialized(String result) throws Throwable {
		this.result = result;
	}
	
	@Given("^I have a valid CEP$")
	public void i_have_a_valid_CEP() throws Throwable {
	    this.cep = "13076903";
	    this.returnCode = 200;
	    this.contentType = "application/json; charset=utf-8";
	}
	
	@Given("^I have an invalid CEP$")
	public void i_have_an_invalid_CEP() throws Throwable {
		this.cep = "123";
		this.returnCode = 400;
		this.contentType = "text/html";
	}
	
	@Given("^I have a valid CEP not foundable$")
	public void i_have_a_valid_CEP_not_foundable() throws Throwable {
		this.cep = "99999999";
		this.returnCode = 200;
		this.contentType = "application/json; charset=utf-8";
	}

	@When("^There is communication with viacep$")
	public void there_is_communication_with_viacep() throws Throwable {
		stubFor(get(urlEqualTo("/ws/"+this.cep+"/json/"))
				.willReturn(aResponse().withStatus(this.returnCode)
				.withHeader("Content-Type", this.contentType).withBodyFile(this.result)));
	}
	
	@When("^There is a slow communication with viacep$")
	public void there_is_a_slow_communication_with_viacep() throws Throwable {
		stubFor(get(urlEqualTo("/ws/"+this.cep+"/json/"))
				.willReturn(aResponse().withStatus(this.returnCode).withFixedDelay(30*1000)
				.withHeader("Content-Type", this.contentType).withFault(Fault.EMPTY_RESPONSE)));
	}

	@When("^I send the CEP to the Post Office$")
	public void i_send_the_CEP_to_the_Post_Office() throws Throwable {
		try {
			currentTime = System.currentTimeMillis();
			String response = RequestCall.get("http://localhost:8089/ws/"+this.cep+"/json/");
			currentTime = System.currentTimeMillis() - currentTime;
			if (!response.contains("Bad Request (400)")) {
				JSONObject obj = new JSONObject(response);
				if (obj.has("erro" )) {
					throw new Exception("Address not found.");
				} else {
					this.correio = new Correio();
					correio.setBairro(obj.getString("bairro"));
					correio.setCEP(obj.getString("cep"));
					correio.setComplemento(obj.getString("complemento"));
					correio.setGia(obj.getString("gia"));
					correio.setIbge(obj.getString("ibge"));
					correio.setLocalidade(obj.getString("localidade"));
					correio.setLogradouro(obj.getString("logradouro"));
					correio.setUf(obj.getString("uf"));
					correio.setUnidade(obj.getString("unidade"));
				}
			} else {
				throw new Exception("Invalid CEP.");
			}
		} catch (Exception e) {
			this.e = e;
		}
	}
	
	@Then("^It should return the address$")
	public void it_should_return_the_address() throws Throwable {
		String address = this.correio.toString().toLowerCase();
	    assertTrue(address.contains("bairro"));
	    assertTrue(address.contains("cep"));
	    assertTrue(address.contains("complemento"));
	    assertTrue(address.contains("gia"));
	    assertTrue(address.contains("ibge"));
	    assertTrue(address.contains("localidade"));
	    assertTrue(address.contains("logradouro"));
	    assertTrue(address.contains("uf"));
	    assertTrue(address.contains("unidade"));
	}
	
	@Then("^It should show an error message in viacep$")
	public void it_should_show_an_error_message_in_soap_xml(String message) throws Throwable {
		Assertions.assertThat(e).isNotNull().hasMessage(message);
	}

	@After
	public void end() {
		wireMockServer.stop();
	}

}
