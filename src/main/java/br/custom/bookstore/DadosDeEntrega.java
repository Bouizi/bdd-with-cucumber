package br.custom.bookstore;

import br.custom.bookstore.dao.DadosDeEntregaDAO;

public class DadosDeEntrega {

	private DadosDeEntregaDAO dao;
	private Double valorFrete;
	private Integer diasEntrega;
	
	public DadosDeEntrega(DadosDeEntregaDAO dao) {
		this.dao = dao;
	}
	
	public void save(Double valorFrete, Integer diasEntrega) {
		this.dao.saveDadosDeEntrega(valorFrete, diasEntrega);
		this.valorFrete = valorFrete;
		this.diasEntrega = diasEntrega;
	}

	public Double getValorFrete() {
		return valorFrete;
	}

	public Integer getDiasEntrega() {
		return diasEntrega;
	}
}
