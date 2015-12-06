package br.com.vortice.avon.pallete.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vortice.avon.pallete.model.PalleteModel;
import br.com.vortice.avon.pallete.model.ProdutoModel;

public class PalleteDAO extends DAOAb {
	
	public List<PalleteModel> selectAll() throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        String sql = "SELECT pallete.codigo,pallete.identificacao from pallete order by pallete.identificacao";
        try {
        	stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
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
	
	public List<PalleteModel> selectStatusAtualPalletes() throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT pallete.codigo,pallete.identificacao, ");
        sql.append("produto.CODIGO_BARRAS,produto.descricao,produto.fsc_code,count(codigo_produto) as quantidade ");
        sql.append("from pallete,produto,produto_pallete ");
        sql.append("where pallete.codigo = produto_pallete.codigo_pallete ");
        sql.append("and produto.codigo = produto_pallete.codigo_produto ");
        sql.append("group by pallete.codigo,pallete.identificacao, ");
        sql.append("produto.CODIGO_BARRAS,produto.descricao,produto.fsc_code ");
        sql.append("order by IDENTIFICACAO ");


        
        try {
        	stmt = connection.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
                palleteNovo.setCodigo(rs.getLong("codigo"));
                palleteNovo.setIdentificacao(rs.getString("identificacao"));
                palleteNovo.setQuantidadeProdutos(rs.getInt("quantidade"));
                palleteNovo.setProduto(new ProdutoModel());
                palleteNovo.getProduto().setCodigoBarras(rs.getString("CODIGO_BARRAS"));
                palleteNovo.getProduto().setFscCode(rs.getString("fsc_code"));
                palleteNovo.getProduto().setDescricao(rs.getString("descricao"));
                listaPalletes.add(palleteNovo);
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
	
	public void insert(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "insert into pallete(identificacao) values(?)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, palleteModel.getIdentificacao());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	if(e.getMessage().contains("CODIGO_PALLETE_UNIQUE")){
			e = new Exception("Pallete já existe com essa identificação.");
		}
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void delete(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from pallete where codigo = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, palleteModel.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void deleteProdutosFromPallete(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where codigo_pallete = ?";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setLong(1, palleteModel.getCodigo());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public void liberaPosicao(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String sql = "delete from produto_pallete where exists (select 1 from pallete where identificacao = ? and pallete.codigo = produto_pallete.codigo_pallete)";
        try {
        	stmt = connection.prepareStatement(sql);
        	stmt.setString(1, palleteModel.getIdentificacao());
        	stmt.executeUpdate();
        }catch (Exception e) {
        	throw e;
        }finally {
        	stmt.close();
            connection.close();
        }
	}
	
	public List<PalleteModel> selectAllPalleteByProductFSCCode(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao, ");
        sql.append(" (select count(codigo_produto) from produto_pallete where pallete.codigo = produto_pallete.codigo_pallete) as quantidade ");
        sql.append(" from pallete ");
        sql.append(" WHERE ");
        sql.append(" exists ");
        sql.append(" (select produto_pallete.codigo_pallete from produto,produto_pallete ");
        sql.append(" where produto.fsc_code = ? and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" and pallete.codigo = produto_pallete.codigo_pallete) ");
        sql.append(" order by pallete.identificacao ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, produtoModel.getFscCode());
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
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
	
	public List<PalleteModel> selectAllPalleteByProductBarCode(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao, ");
        sql.append(" (select count(codigo_produto) from produto_pallete where pallete.codigo = produto_pallete.codigo_pallete) as quantidade ");
        sql.append(" from pallete ");
        sql.append(" WHERE ");
        sql.append(" exists ");
        sql.append(" (select produto_pallete.codigo_pallete from produto,produto_pallete ");
        sql.append(" where produto.codigo_barras = ? and produto.codigo = produto_pallete.codigo_produto ");
        sql.append(" and pallete.codigo = produto_pallete.codigo_pallete) ");
        sql.append(" order by pallete.identificacao ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, produtoModel.getCodigoBarras());
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	palleteNovo.setQuantidadeProdutos(rs.getInt("quantidade"));
            	listaPalletes.add(palleteNovo);
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
	
	public List<PalleteModel> selectEmptyPallete(ProdutoModel produtoModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder();


        sql.append(" SELECT pallete.codigo,pallete.identificacao "); 
		sql.append(" from pallete ");
		sql.append(" left join produto_pallete ");
		sql.append(" on produto_pallete.codigo_pallete = pallete.codigo ");
		sql.append(" where produto_pallete.CODIGO_PALLETE is null ");
		sql.append(" order by pallete.identificacao  ");
                 
                 
        try {
        
        	stmt = connection.prepareStatement(sql.toString());
        	
            rs = stmt.executeQuery();
            List<PalleteModel> listaPalletes = new ArrayList<PalleteModel>();
            while (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	listaPalletes.add(palleteNovo);
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
	
	public PalleteModel selectPalleteByIdentificacao(PalleteModel palleteModel) throws Exception{
		Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        StringBuilder sql = new StringBuilder("SELECT pallete.codigo,pallete.identificacao from pallete");
        sql.append(" WHERE ");
        sql.append(" identificacao = ? ");
        try {
        	stmt = connection.prepareStatement(sql.toString());
        	stmt.setString(1, palleteModel.getIdentificacao());
            rs = stmt.executeQuery();
            if (rs.next()) {
            	PalleteModel palleteNovo = new PalleteModel();
            	palleteNovo.setCodigo(rs.getLong("codigo"));
            	palleteNovo.setIdentificacao(rs.getString("identificacao"));
            	return palleteNovo;
            }
            return null;
        }catch (Exception e) {
        	throw e;
        }finally {
        	rs.close();
        	stmt.close();
            connection.close();
        }
	}
}
