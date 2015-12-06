package br.com.vortice.avon.pallete.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;

public class ProdutoDAO extends DAOAb {
	
	private static final String SELECT_BASE = "SELECT PRODUTO.codigo,PRODUTO.codigo_barras,PRODUTO.fsc_code,PRODUTO.descricao FROM PRODUTO ";

	public ProdutoModel selectProductByBarCode(ProdutoModel produto) throws Exception{
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = SELECT_BASE+" where codigo_barras = ? order by PRODUTO.descricao";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, produto.getCodigoBarras());
            rs = stmt.executeQuery();
            if (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigo(rs.getLong("codigo"));
            	produtoNovo.setCodigoBarras(rs.getString("codigo_barras"));
            	produtoNovo.setFscCode(rs.getString("fsc_code"));
            	produtoNovo.setDescricao(rs.getString("descricao"));
            	return produtoNovo;
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return null;
	}
	
	public List<ProdutoModel> selectAll() throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = SELECT_BASE+" order by PRODUTO.descricao";
        try {
        	stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            List<ProdutoModel> listaPalletes = new ArrayList<ProdutoModel>();
            while (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigo(rs.getLong("codigo"));
            	produtoNovo.setCodigoBarras(rs.getString("codigo_barras"));
            	produtoNovo.setFscCode(rs.getString("fsc_code"));
            	produtoNovo.setDescricao(rs.getString("descricao"));
            	listaPalletes.add(produtoNovo);
            }
        	return listaPalletes;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
	
	public ProdutoModel selectProductByFscCode(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = SELECT_BASE+" where fsc_code = ? order by PRODUTO.descricao";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, produto.getFscCode());
            rs = stmt.executeQuery();
            if (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigo(rs.getLong("codigo"));
            	produtoNovo.setCodigoBarras(rs.getString("codigo_barras"));
            	produtoNovo.setFscCode(rs.getString("fsc_code"));
            	produtoNovo.setDescricao(rs.getString("descricao"));
            	return produtoNovo;
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return null;
	}
	
	public List<ProdutoModel> selectListProductByPalleteId(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT PRODUTO.codigo_barras,PRODUTO.fsc_code,PRODUTO.descricao,count(*)  as quantidade ");
        sql.append("  FROM PRODUTO produto,produto_pallete where produto_pallete.codigo_pallete = ? and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" group by codigo_barras,fsc_code,descricao");
        sql.append(" order by PRODUTO.descricao");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setLong(1, palleteModel.getCodigo());
            rs = stmt.executeQuery();
            List<ProdutoModel> listaProdutos = new ArrayList<ProdutoModel>();
            while (rs.next()) {
            	ProdutoModel produtoNovo = new ProdutoModel();
            	produtoNovo.setCodigoBarras(rs.getString("codigo_barras"));
            	produtoNovo.setFscCode(rs.getString("fsc_code"));
            	produtoNovo.setDescricao(rs.getString("descricao"));
            	produtoNovo.setQuantidade(rs.getInt("quantidade"));
            	listaProdutos.add(produtoNovo);
            	return listaProdutos;
            }
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
		return null;
	}
	
	public void insert(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into produto values(?,?,?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, produto.getCodigo());
        	stmt.setString(2, produto.getCodigoBarras());
        	stmt.setString(3, produto.getFscCode());
        	stmt.setString(4, produto.getDescricao());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void limpaFscCodeTodasPosicoes(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where exists (select 1 from produto where fsc_code = ? and produto.codigo = produto_pallete.codigo_produto)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, produto.getFscCode());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void deleteProdutosFromPallete(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where codigo_produto = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, produto.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void associarProdutoPallete(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into produto_pallete (codigo,codigo_produto,codigo_pallete) values ((select (nvl(max(codigo),0))+1 from produto_pallete) , ?,?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, produto.getCodigo());
        	stmt.setLong(2, produto.getPalleteModel().getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void delete(ProdutoModel produto) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto where codigo = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, produto.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
}
