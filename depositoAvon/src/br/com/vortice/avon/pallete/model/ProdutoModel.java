package br.com.vortice.avon.pallete.model;

public class ProdutoModel {
	
	private Long codigo;
	private String codigoBarras;
	private String fscCode;
	private String descricao;
	private PalleteModel palleteModel;
	private Integer quantidade;
	
	public ProdutoModel(Long codigo, String codigoBarras, String fscCode, String descricao) {
		this.codigo = codigo;
		this.codigoBarras = codigoBarras;
		this.fscCode = fscCode;
		this.descricao = descricao;
	}
	
	public ProdutoModel(Long codigo) {
		this.codigo = codigo;
	}
	
	public ProdutoModel() {
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getFscCode() {
		return fscCode;
	}
	public void setFscCode(String fscCode) {
		this.fscCode = fscCode;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public PalleteModel getPalleteModel() {
		return palleteModel;
	}
	
	public void setPalleteModel(PalleteModel palleteModel) {
		this.palleteModel = palleteModel;
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
		ProdutoModel other = (ProdutoModel) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
