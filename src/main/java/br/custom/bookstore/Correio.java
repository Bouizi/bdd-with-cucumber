package br.custom.bookstore;

public class Correio {

	private String CEP;
	private String Logradouro;
	private String Complemento;
	private String Bairro;
	private String Localidade;
	private String Uf;
	private String Ibge;
	private String Gia;
	private String unidade;

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getLogradouro() {
		return Logradouro;
	}

	public void setLogradouro(String logradouro) {
		Logradouro = logradouro;
	}

	public String getComplemento() {
		return Complemento;
	}

	public void setComplemento(String complemento) {
		Complemento = complemento;
	}

	public String getBairro() {
		return Bairro;
	}

	
	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	public String getLocalidade() {
		return Localidade;
	}

	public void setLocalidade(String localidade) {
		Localidade = localidade;
	}

	public String getUf() {
		return Uf;
	}

	public void setUf(String uf) {
		Uf = uf;
	}

	public String getIbge() {
		return Ibge;
	}

	public void setIbge(String ibge) {
		Ibge = ibge;
	}

	public String getGia() {
		return Gia;
	}

	public void setGia(String gia) {
		Gia = gia;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Override
	public String toString() {
		return "[CEP=" + CEP + ", Logradouro=" + Logradouro + ", Complemento=" + Complemento + ", Bairro="
				+ Bairro + ", Localidade=" + Localidade + ", Uf=" + Uf + ", Ibge=" + Ibge + ", Gia=" + Gia
				+ ", unidade=" + unidade + "]";
	}
	
}
