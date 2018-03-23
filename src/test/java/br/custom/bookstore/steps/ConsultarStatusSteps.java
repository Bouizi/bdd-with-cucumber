package br.custom.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.w3c.dom.NodeList;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import br.custom.bookstore.helper.ParseXml;
import br.custom.bookstore.helper.RequestCall;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ConsultarStatusSteps {

	private Exception e;
	private WireMockServer wireMockServer;

	private String result;
	private Long currentTime;

	private String soap;
	private String code;
	private String descricao;

	@Before
	public void setUp() {
		e = null;
	}

	@Given("^Soap XML data initialized (.+)$")
	public void data_initialized(String result) throws Throwable {
		this.result = result;
	}

	@Given("^I have a valid code$")
	public void i_have_a_valid_code() {
		this.code = "DU553211637BR";
	}

	@Given("^I have an invalid code$")
	public void i_have_an_invalid_code() throws Throwable {
		this.code = "JF598971235BR";
	}

	@When("^There is communication with buscaEventos$")
	public void there_is_communication() throws Throwable {
		this.soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ "xmlns:res=\"http://resource.webservice.correios.com.br/\">" + "<soapenv:Header/>" + "<soapenv:Body>"
				+ "<res:buscaEventos>" + "<usuario>ECT</usuario>" + "<senha>SRO</senha>" + "<tipo>L</tipo>"
				+ "<resultado>T</resultado>" + "<lingua>101</lingua>" + "<objetos>" + this.code + "</objetos>"
				+ "</res:buscaEventos>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		stubFor(post(urlEqualTo("/service/rastro")).withRequestBody(equalTo(this.soap))
				.willReturn(aResponse().withStatus(200).withHeader("SOAPAction", "buscaEventos")
						.withHeader("Content-Type", "text/xml; charset=utf-8").withBodyFile(this.result)));
	}

	@When("^There is slow communication with buscaEventos$")
	public void there_is_slow_communication() throws Throwable {
		this.soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ "xmlns:res=\"http://resource.webservice.correios.com.br/\">" + "<soapenv:Header/>" + "<soapenv:Body>"
				+ "<res:buscaEventos>" + "<usuario>ECT</usuario>" + "<senha>SRO</senha>" + "<tipo>L</tipo>"
				+ "<resultado>T</resultado>" + "<lingua>101</lingua>" + "<objetos>" + this.code + "</objetos>"
				+ "</res:buscaEventos>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		stubFor(post(urlEqualTo("/service/rastro")).withRequestBody(equalTo(this.soap))
				.willReturn(aResponse().withStatus(200).withFixedDelay(30 * 1000)
						.withHeader("SOAPAction", "buscaEventos").withHeader("Content-Type", "text/xml; charset=utf-8")
						.withFault(Fault.EMPTY_RESPONSE)));
	}

	@When("^I send the code to the Post Office$")
	public void i_send_a_valid_code_to_the_Post_Office() throws Throwable {
		try {
			currentTime = System.currentTimeMillis();
			String response = RequestCall.postSoap("http://localhost:8089/service/rastro", "buscaEventos", this.soap);
			currentTime = System.currentTimeMillis() - currentTime;

			assertFalse(response.contains("Error 404 NOT_FOUND"));
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(response.getBytes(Charset.forName("UTF-8"))));
			SOAPBody body = message.getSOAPBody();
			NodeList returnList = body.getElementsByTagName("ns2:buscaEventosResponse");
			String errorMsg = ParseXml.findString(returnList, "erro");
			if (errorMsg != "") {
				// Resposta do correio com acento n�o permite assert pelo
				// cucumber
				throw new Exception("Invalid object.");
			}
			// pegara o status do primeiro evento que encontrar, no caso o
			// ultimo atualizado
			this.descricao = ParseXml.findString(returnList, "descricao");
		} catch (Exception e) {
			this.e = e;
		}
	}

	@Then("^Time cycle is quick response from buscaEventos$")
	public void time_cycle_is_quick_response_from_buscaEventos() throws Throwable {
		assertTrue(this.currentTime <= 3000);
	}

	@Then("^Time cycle is slow response from buscaEventos$")
	public void time_cycle_is_slow_response_from_buscaEventos() throws Throwable {
		assertTrue(this.currentTime > 3000);
	}

	@When("^The Post Office finds the delivery$")
	public void the_Post_Office_finds_the_delivery() throws Throwable {
		assertTrue(StringUtils.isNotEmpty(this.descricao));
	}

	@Then("^It should show the code and status$")
	public void it_should_show_the_code_and_status() throws Throwable {
		assertEquals("DU553211637BR", this.code);
		Assertions.assertThat(this.descricao).isNotEmpty().contains("Objeto entregue");
	}

	@Then("^It should show an error message in soap xml$")
	public void it_should_show_an_error_message_in_soap_xml(String message) throws Throwable {
		Assertions.assertThat(e).isNotNull().hasMessage(message);
	}

}
