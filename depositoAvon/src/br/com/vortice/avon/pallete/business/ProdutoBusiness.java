package br.com.vortice.avon.pallete.business;

import java.util.List;

import org.springframework.util.StringUtils;

import br.com.vortice.avon.pallete.dao.PalleteDAO;
import br.com.vortice.avon.pallete.dao.ProdutoDAO;
import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;

public class ProdutoBusiness{
	
	private ProdutoDAO produtoDAO;
	private PalleteDAO palleteDAO;
	
	public ProdutoBusiness() {
		this.produtoDAO = new ProdutoDAO();
		this.palleteDAO = new PalleteDAO();
	}

	public ProdutoModel selectProductByBarCode(ProdutoModel produto) throws Exception{
		return produtoDAO.selectProductByBarCode(produto);
	}
	
	public ProdutoModel selectProductByFscCode(ProdutoModel produto) throws Exception{
		return produtoDAO.selectProductByFscCode(produto);
	}
	
	public List<ProdutoModel> selectListProductByPalleteId(PalleteModel palleteModel) throws Exception{	
//		0041002R
		if(!StringUtils.isEmpty(palleteModel.getIdentificacao())){
			palleteModel = palleteDAO.selectPalleteByIdentificacao(palleteModel);
			if(palleteModel != null){
				return produtoDAO.selectListProductByPalleteId(palleteModel);
			}else{
				throw new Exception("Pallete não encontrado.");
			}
		}else{
			throw new Exception("Obrigatório informar o campo código do pallete.");
		}
	}
	public List<ProdutoModel> selectAll() throws Exception{	
		return produtoDAO.selectAll();
	}
	public void delete(ProdutoModel produto) throws Exception{
		if(produto != null && produto.getCodigo() != null){
			this.deleteProdutosFromPallete(produto);
			produtoDAO.delete(produto);	
		}else{
			throw new Exception("Obrigatório informar o campo código.");
		}
		
	}
	public void insert(ProdutoModel produto) throws Exception{
		if(StringUtils.isEmpty(produto.getCodigoBarras()) || StringUtils.isEmpty(produto.getFscCode()) || StringUtils.isEmpty(produto.getDescricao())){
				throw new Exception("Campo obrigatório não preenchido.");
		}
		produtoDAO.insert(produto);
	}
	
	public void limpaFscCodeTodasPosicoes(ProdutoModel produto) throws Exception{
		if(StringUtils.isEmpty(produto.getFscCode())){
			throw new Exception("Campo obrigatório não preenchido.");
	}
		produtoDAO.limpaFscCodeTodasPosicoes(produto);	
	}
	
	public void deleteProdutosFromPallete(ProdutoModel produto) throws Exception{
		if(produto != null && produto.getCodigo() != null){
			produtoDAO.deleteProdutosFromPallete(produto);
		}else{
			throw new Exception("Obrigatório informar o campo código.");
		}
	}
	
	public PalleteModel associarProdutoPeloCodigoBarras(ProdutoModel produtoModel) throws Exception{
		List<PalleteModel> listaPalletesDisponiveis = null;
		if(StringUtils.isEmpty(produtoModel.getCodigoBarras())){
			throw new Exception("Obrigatório informar o código de barras.");
		}else if(!StringUtils.isEmpty(produtoModel.getCodigoBarras())){
			produtoModel = produtoDAO.selectProductByBarCode(produtoModel);
			if(produtoModel != null){
				listaPalletesDisponiveis = palleteDAO.selectAllPalleteByProductBarCode(produtoModel);
			}else{
				throw new Exception("Produto não encontrado pelo código de barras.");
			}
		}
		if(listaPalletesDisponiveis == null || listaPalletesDisponiveis.size() == 0){
			listaPalletesDisponiveis = palleteDAO.selectEmptyPallete(produtoModel);
			PalleteModel palleteModel = listaPalletesDisponiveis.get(0);
			produtoModel.setPalleteModel(palleteModel);
			produtoDAO.associarProdutoPallete(produtoModel);
		}
		if(produtoModel.getPalleteModel() == null){
			throw new Exception("Produto não foi associado a nenhum pallete.");
		}
		return produtoModel.getPalleteModel();
	}
	
	public PalleteModel associarProdutoPeloFscCode(ProdutoModel produtoModel) throws Exception{
		List<PalleteModel> listaPalletesDisponiveis = null;
		if(StringUtils.isEmpty(produtoModel.getFscCode())){
			throw new Exception("Obrigatório informar fsc code.");
		}else if(!StringUtils.isEmpty(produtoModel.getFscCode())){
			produtoModel = produtoDAO.selectProductByFscCode(produtoModel);
			if(produtoModel != null){
				listaPalletesDisponiveis = palleteDAO.selectAllPalleteByProductFSCCode(produtoModel);
			}else{
				throw new Exception("Produto não encontrado pelo fsc code.");
			}		
		}
		if(listaPalletesDisponiveis == null || listaPalletesDisponiveis.size() == 0){
			listaPalletesDisponiveis = palleteDAO.selectEmptyPallete(produtoModel);
			PalleteModel palleteModel = listaPalletesDisponiveis.get(0);
			produtoModel.setPalleteModel(palleteModel);
			produtoDAO.associarProdutoPallete(produtoModel);
		}
		if(produtoModel.getPalleteModel() == null){
			throw new Exception("Produto não foi associado a nenhum pallete.");
		}
		return produtoModel.getPalleteModel();
	}
	
	private boolean associarProdutoPallete(List<PalleteModel> listaPalletesDisponiveis,ProdutoModel produtoModel) throws Exception{
		if(listaPalletesDisponiveis != null && listaPalletesDisponiveis.size() > 0){
			PalleteModel palleteModel = listaPalletesDisponiveis.get(0);
			produtoModel.setPalleteModel(palleteModel);
			produtoDAO.associarProdutoPallete(produtoModel);
			return true;
		}
		return false;
		
	}
	
}
