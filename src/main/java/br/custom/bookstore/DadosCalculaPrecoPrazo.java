package br.custom.bookstore;

public class DadosCalculaPrecoPrazo {

	// CEP de origem e destino. Esse parametro precisa ser num�rico, sem "-" (h�fen) espa�os ou algo diferente de um n�mero.
	private Long sCepOrigem; //96010140
	private Long sCepDestino; //02460000

	// O peso do produto dever� ser enviado em quilogramas, leve em considera��o que isso dever� incluir o peso da embalagem.
	private Integer nVlPeso; //1

	// O formato tem apenas duas op��es: 1 para caixa / pacote e 2 para rolo/prisma.
	private Integer nCdFormato; //1

	// O comprimento, altura, largura e diametro dever� ser informado em cent�metros e somente n�meros
	private Double nVlComprimento; //16
	private Double nVlAltura; //5
	private Double nVlLargura; //16
	private Double nVlDiametro; //0

	// Aqui voc� informa se quer que a encomenda deva ser entregue somente para uma determinada pessoa ap�s confirma��o por RG. Use "s" e "n".
	private String sCdMaoPropria; //s

	// O valor declarado serve para o caso de sua encomenda extraviar, ent�o voc� poder� recuperar o valor dela. Vale lembrar que o valor da encomenda interfere no valor do frete. Se n�o quiser declarar pode passar 0 (zero).
	private Double nVlValorDeclarado; //200

	// Se voc� quer ser avisado sobre a entrega da encomenda. Para n�o avisar use "n", para avisar use "s".
	private String sCdAvisoRecebimento; //n

	// C�digo do Servi�o, pode ser apenas um ou mais. Para mais de um apenas separe por virgula.
	private String nCdServico; //40010,41106
	
	public DadosCalculaPrecoPrazo() {
	}

	public Long getsCepOrigem() {
		return sCepOrigem;
	}

	public void setsCepOrigem(Long sCepOrigem) {
		this.sCepOrigem = sCepOrigem;
	}

	public Long getsCepDestino() {
		return sCepDestino;
	}

	public void setsCepDestino(Long sCepDestino) {
		this.sCepDestino = sCepDestino;
	}

	public Integer getnVlPeso() {
		return nVlPeso;
	}

	public void setnVlPeso(Integer nVlPeso) {
		this.nVlPeso = nVlPeso;
	}

	public Integer getnCdFormato() {
		return nCdFormato;
	}

	public void setnCdFormato(Integer nCdFormato) {
		this.nCdFormato = nCdFormato;
	}

	public Double getnVlComprimento() {
		return nVlComprimento;
	}

	public void setnVlComprimento(Double nVlComprimento) {
		this.nVlComprimento = nVlComprimento;
	}

	public Double getnVlAltura() {
		return nVlAltura;
	}

	public void setnVlAltura(Double nVlAltura) {
		this.nVlAltura = nVlAltura;
	}

	public Double getnVlLargura() {
		return nVlLargura;
	}

	public void setnVlLargura(Double nVlLargura) {
		this.nVlLargura = nVlLargura;
	}

	public Double getnVlDiametro() {
		return nVlDiametro;
	}

	public void setnVlDiametro(Double nVlDiametro) {
		this.nVlDiametro = nVlDiametro;
	}

	public String getsCdMaoPropria() {
		return sCdMaoPropria;
	}

	public void setsCdMaoPropria(String sCdMaoPropria) {
		this.sCdMaoPropria = sCdMaoPropria;
	}

	public Double getnVlValorDeclarado() {
		return nVlValorDeclarado;
	}

	public void setnVlValorDeclarado(Double nVlValorDeclarado) {
		this.nVlValorDeclarado = nVlValorDeclarado;
	}

	public String getsCdAvisoRecebimento() {
		return sCdAvisoRecebimento;
	}

	public void setsCdAvisoRecebimento(String sCdAvisoRecebimento) {
		this.sCdAvisoRecebimento = sCdAvisoRecebimento;
	}

	public String getnCdServico() {
		return nCdServico;
	}

	public void setnCdServico(String nCdServico) {
		this.nCdServico = nCdServico;
	}
	
}
