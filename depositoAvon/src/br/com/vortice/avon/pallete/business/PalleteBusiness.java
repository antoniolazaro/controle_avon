package br.com.vortice.avon.pallete.business;

import java.util.List;

import org.springframework.util.StringUtils;

import br.com.vortice.avon.pallete.dao.PalleteDAO;
import br.com.vortice.avon.pallete.model.PalleteModel;

public class PalleteBusiness {
	
	private PalleteDAO palleteDAO;
	
	public PalleteBusiness() {
		this.palleteDAO = new PalleteDAO();
	}
	
	public List<PalleteModel> selectAll() throws Exception{
		return palleteDAO.selectAll();
	}
	
	public void insert(PalleteModel palleteModel) throws Exception{
		if(palleteModel != null && !StringUtils.isEmpty(palleteModel.getIdentificacao())){
			palleteDAO.insert(palleteModel);
		}else{
			throw new Exception("Campos obrigatórios não preenchidos");
		}
	}
	
	public void delete(PalleteModel palleteModel) throws Exception{
		if(palleteModel != null){
			this.deleteProdutosFromPallete(palleteModel);
			palleteDAO.delete(palleteModel);
		}else{
			throw new Exception("Campos obrigatórios não preenchidos");
		}
	}
	
	public void deleteProdutosFromPallete(PalleteModel palleteModel) throws Exception{
		if(palleteModel != null){
			palleteDAO.deleteProdutosFromPallete(palleteModel);
		}else{
			throw new Exception("Campos obrigatórios não preenchidos");
		}
	}
	
	public void liberaPosicao(PalleteModel palleteModel) throws Exception{
		if(palleteModel != null){
			palleteDAO.liberaPosicao(palleteModel);
		}else{
			throw new Exception("Campos obrigatórios não preenchidos");
		}
	}
}
