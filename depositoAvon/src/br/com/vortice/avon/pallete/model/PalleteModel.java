package br.com.vortice.avon.pallete.model;

public class PalleteModel {
	
	private Long codigo;
	private String identificacao;
	private Integer quantidadeProdutos;
	private ProdutoModel produto;
	
	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	public PalleteModel(Long codigo, String identificacao) {
		this.codigo = codigo;
		this.identificacao = identificacao;
	}

	public PalleteModel(Long codigo) {
		this.codigo = codigo;
	}
	
	public PalleteModel() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	
	public Integer getQuantidadeProdutos() {
		return quantidadeProdutos;
	}

	public void setQuantidadeProdutos(Integer quantidadeProdutos) {
		this.quantidadeProdutos = quantidadeProdutos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PalleteModel other = (PalleteModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
