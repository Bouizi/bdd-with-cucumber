package br.custom.bookstore.steps;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;

import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.api.Assertions;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import br.custom.bookstore.DadosCalculaPrecoPrazo;
import br.custom.bookstore.DadosDeEntrega;
import br.custom.bookstore.dao.DadosDeEntregaDAO;
import br.custom.bookstore.helper.ParseXml;
import br.custom.bookstore.helper.RequestCall;
import br.custom.bookstore.helper.Utils;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculaPrecoPrazoSteps {

	private Exception e;
	private WireMockServer wireMockServer;

	private String result;
	private Long currentTime;

	private DadosCalculaPrecoPrazo dados;
	@Mock
	private DadosDeEntregaDAO dao;
	@InjectMocks
	private DadosDeEntrega dadosDeEntrega;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		dados = new DadosCalculaPrecoPrazo();
		dadosDeEntrega = new DadosDeEntrega(dao);
		e = null;
	}

	@Given("^Price and Period data initialized (.+)$")
	public void data_initialized(String result) throws Throwable {
		this.result = result;
		assertNotNull(dados);
		assertNotNull(dadosDeEntrega);
	}

	@Given("^I have the products data$")
	public void i_have_the_products_data() throws Throwable {
		dados.setnVlPeso(1);
		dados.setnCdFormato(1);
		dados.setnVlComprimento(16.0);
		dados.setnVlAltura(5.0);
		dados.setnVlLargura(16.0);
		dados.setnVlDiametro(0.0);
		dados.setsCdMaoPropria("s");
		dados.setnVlValorDeclarado(200.0);
		dados.setsCdAvisoRecebimento("s");
	}

	@Given("^I do not have the products data$")
	public void i_do_not_have_the_products_data() throws Throwable {
	}

	@Given("^I have the origin address returned by the Post Office$")
	public void i_have_the_origin_address_returned_by_the_Post_Office() throws Throwable {
		dados.setsCepOrigem(96010140L);
	}

	@Given("^I do not have the origin address returned by the Post Office$")
	public void i_do_not_have_the_origin_address_returned_by_the_post_office() throws Throwable {
	}

	@Given("^I have the delivery address returned by the Post Office$")
	public void i_have_the_delivery_address_returned_by_the_Post_Office() throws Throwable {
		dados.setsCepDestino(02460000L);
	}

	@Given("^I do not have the delivery address returned by the Post Office$")
	public void i_do_not_have_the_delivery_addres_returned_by_post_office() throws Throwable {
	}

	@Given("^I have the type of the delivery$")
	public void i_have_the_type_of_the_delivery() throws Throwable {
		dados.setnCdServico("40010");
	}

	@Given("^I do not have the type of the delivery$")
	public void i_do_not_have_the_type_of_the_delivery() throws Throwable {
	}

	@When("^There is communication with CalcPrecoPrazo$")
	public void there_is_communication_with_calprezoprazo() throws Throwable {
		stubFor(get(urlMatching("/calculador/CalcPrecoPrazo.*"))
				// .withQueryParam("sCepOrigem", matching("(\\d+)"))
				// .withQueryParam("sCepDestino", matching("(\\d+)"))
				// .withQueryParam("nVlPeso", matching("(\\d+)"))
				// .withQueryParam("nCdFormato", matching("(\\d+)"))
				// .withQueryParam("nVlComprimento", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlAltura", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlLargura", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlDiametro", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("sCdMaoPropria", matching("(.*)"))
				// .withQueryParam("nVlValorDeclarado",
				// matching("(\\d+\\.\\d+)"))
				// .withQueryParam("sCdAvisoRecebimento", matching("(.*)"))
				// .withQueryParam("nCdServico", matching("(.*)"))
				.willReturn(
						aResponse().withStatus(200).withHeader("Content-Type", "text/xml").withBodyFile(this.result)));
	}

	@When("^There is slow communication with CalcPrecoPrazo$")
	public void there_is_slow_communication_with_calcprecoprazo() throws Throwable {
		// reset();
		stubFor(get(urlMatching("/calculador/CalcPrecoPrazo.*"))
				// .withQueryParam("sCepOrigem", matching("(\\d+)"))
				// .withQueryParam("sCepDestino", matching("(\\d+)"))
				// .withQueryParam("nVlPeso", matching("(\\d+)"))
				// .withQueryParam("nCdFormato", matching("(\\d+)"))
				// .withQueryParam("nVlComprimento", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlAltura", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlLargura", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("nVlDiametro", matching("(\\d+\\.\\d+)"))
				// .withQueryParam("sCdMaoPropria", matching("(.*)"))
				// .withQueryParam("nVlValorDeclarado",
				// matching("(\\d+\\.\\d+)"))
				// .withQueryParam("sCdAvisoRecebimento", matching("(.*)"))
				// .withQueryParam("nCdServico", matching("(.*)"))
				.willReturn(aResponse().withStatus(200).withFixedDelay(30 * 1000).withHeader("Content-Type", "text/xml")
						.withFault(Fault.EMPTY_RESPONSE)));
	}

	@When("^I send the products data$")
	public void i_send_the_products_data() throws Throwable {
		try {
			Double valorFrete = null;
			Integer diasEntrega = null;

			List<NameValuePair> params = new LinkedList<NameValuePair>();
			if (dados.getsCepOrigem() != null)
				params.add(new BasicNameValuePair("sCepOrigem", dados.getsCepOrigem().toString()));
			if (dados.getsCepDestino() != null)
				params.add(new BasicNameValuePair("sCepDestino", dados.getsCepDestino().toString()));
			if (dados.getnVlPeso() != null)
				params.add(new BasicNameValuePair("nVlPeso", dados.getnVlPeso().toString()));
			if (dados.getnCdFormato() != null)
				params.add(new BasicNameValuePair("nCdFormato", dados.getnCdFormato().toString()));
			if (dados.getnVlComprimento() != null)
				params.add(new BasicNameValuePair("nVlComprimento", dados.getnVlComprimento().toString()));
			if (dados.getnVlAltura() != null)
				params.add(new BasicNameValuePair("nVlAltura", dados.getnVlAltura().toString()));
			if (dados.getnVlLargura() != null)
				params.add(new BasicNameValuePair("nVlLargura", dados.getnVlLargura().toString()));
			if (dados.getnVlDiametro() != null)
				params.add(new BasicNameValuePair("nVlDiametro", dados.getnVlDiametro().toString()));
			if (dados.getsCdMaoPropria() != null)
				params.add(new BasicNameValuePair("sCdMaoPropria", dados.getsCdMaoPropria()));
			if (dados.getnVlValorDeclarado() != null)
				params.add(new BasicNameValuePair("nVlValorDeclarado", dados.getnVlValorDeclarado().toString()));
			if (dados.getsCdAvisoRecebimento() != null)
				params.add(new BasicNameValuePair("sCdAvisoRecebimento", dados.getsCdAvisoRecebimento()));
			if (dados.getnCdServico() != null)
				params.add(new BasicNameValuePair("nCdServico", dados.getnCdServico()));
			params.add(new BasicNameValuePair("strRetorno", "xml"));
			String paramString = URLEncodedUtils.format(params, "utf-8");

			currentTime = System.currentTimeMillis();
			String response = RequestCall.get("http://localhost:8089/calculador/CalcPrecoPrazo" + "?" + paramString);
			currentTime = System.currentTimeMillis() - currentTime;

			assertFalse(response.contains("Error 404 NOT_FOUND"));
			ParseXml parse = new ParseXml(response);
			if (!parse.getString("Erro").trim().equals("0")) {
				String errorMsg = Utils.removeAcentos(parse.getString("MsgErro"));
				throw new Exception(errorMsg);
			}
			valorFrete = new Double(parse.getString("Valor").replace(",", "."));
			diasEntrega = new Integer(parse.getString("PrazoEntrega"));
			if (valorFrete != null && diasEntrega != null)
				dadosDeEntrega.save(valorFrete, diasEntrega);
		} catch (Exception e) {
			this.e = e;
		}
		// while (1 == 1);
	}

	@Then("^It should store the freight and delivery time in the database$")
	public void it_should_store_the_freight_and_delivery_time_in_the_database() throws Throwable {
		verify(dao, times(1)).saveDadosDeEntrega(anyDouble(), anyInt());
	}

	@Then("^Time cycle is quick response from CalcPrecoPrazo$")
	public void time_cycle_is_quick() throws Throwable {
		assertTrue(this.currentTime <= 3000);
	}

	@Then("^Time cycle has a slow response from CalcPrecoPrazo$")
	public void time_cycle_is_slow() throws Throwable {
		assertTrue(this.currentTime > 3000);
	}

	@Then("^It should return the freight and delivery time$")
	public void it_should_return_the_freight_and_delivery_time() throws Throwable {
		assertEquals(63.65d, dadosDeEntrega.getValorFrete().doubleValue(), 0.1);
		assertEquals(2, dadosDeEntrega.getDiasEntrega().intValue());
	}

	@Then("^It should not store the freight and delivery time in the database$")
	public void it_should_not_store_the_freight_and_delivery_time_in_the_database() throws Throwable {
		verify(dao, never()).saveDadosDeEntrega(anyDouble(), anyInt());
	}

	@Then("^It should show an error message in xml$")
	public void it_should_show_an_error_message(String message) throws Throwable {
		Assertions.assertThat(e).isNotNull().hasMessage(message);
	}

}